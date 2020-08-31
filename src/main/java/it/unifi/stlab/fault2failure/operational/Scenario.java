package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class Scenario gives a test scenario in which we want to simulate how
 * a set of incoming faults will propagate into failures in a certain system.
 * Needs a map of <String,List<PropagationPort>>
 */
public class Scenario {
    private List<Fault> incomingFaults;
    private List<Fault> failuresOccurred;

    private final HashMap<String, List<BigDecimal>> failuresOccurredTimes;
    private HashMap<String, Component> failureComponents;


    private List<Component> system;

    public Scenario(){
        incomingFaults = new ArrayList<>(); //List of failures in the scenario
        failuresOccurred = new ArrayList<>(); //List of failures occurred after the propagation
        failuresOccurredTimes = new HashMap<>(); //Map with occurred Failures linked to their occurrence time

        failureComponents= new HashMap<>();
    }
    public Scenario(List<Component> currentSystem){
        this();
        this.system =currentSystem;
    }

    public void addFault(Fault fail, BigDecimal timestamp){
        //Add the fault to the incomingFault list,
        //Add it also to the incomingFaultTimes with its timestamp
        if(!incomingFaults.contains(fail))
            incomingFaults.add(fail);
    }

    public void addFault(Fault fail, BigDecimal timestamp, Component component){
        //Add the fault to the incomingFault list,
        //Add it also to the incomingFaultTimes with its timestamp
        fail.occurred(timestamp);
        if(!incomingFaults.contains(fail))
            incomingFaults.add(fail);
        component.addFailure(fail);
        failureComponents.put(fail.getDescription(), component);


    }
    public void removeFailure(Fault fail){
        if(incomingFaults.contains(fail))
            incomingFaults.remove(fail);

    }
    public List<Fault> getIncomingFaults(){
        return this.incomingFaults;
    }
    public List<Fault> getFailuresOccurred(){
        return this.failuresOccurred;
    }
    public HashMap<String, List<BigDecimal>> getFailuresOccurredWithTimes(){ return this.failuresOccurredTimes;}
    public void setSystem(List<Component> system) {
        this.system = system;
    }

    /**
     * Method propagate orders the Faults inside the incomingFaultTimes map by Timestamp, then iteratively calls the
     * method propagate(Failure, Component) in a chronological way.
     */
    public void propagate(){
        List<Fault> orderedList = incomingFaults.stream().sorted(Comparator.comparing(Fault::getTimestamp)).collect(Collectors.toList());
        for(Fault fault :orderedList){
            Component affectedComponent = failureComponents.get(fault.getDescription());
            propagate(fault, affectedComponent);
        }
    }

    private void propagate(Fault fault, Component affectedComponent){
        Fault next;
        fault.getFaultMode().setState(true);
        for(ErrorMode em: getErrorModesFromFault(fault, affectedComponent.getComponentType())){
            if(em.checkActivationFunction()){
                failuresOccurredTimes.computeIfAbsent(em.getOutgoingFailure().getDescription(),k->new ArrayList<>()).add(BigDecimal.valueOf(fault.getTimestamp().intValue()+1));
                Optional<PropagationPort> propagationPort = affectedComponent.getComponentType().getPropagationPort().stream().filter(x-> x.getPropagatedFailureMode().equals(em.getOutgoingFailure())).findAny();
                if(propagationPort.isPresent()){
                    FaultMode exoFault = propagationPort.get().getExogenousFaultMode();
                    next = new Fault(exoFault.getName(),exoFault);
                    Component failedComponent = system.stream()
                            .filter(component -> propagationPort.get().getAffectedComponent().equals(component.getComponentType()))
                            .findAny()
                            .orElse(null);
                    if(!failedComponent.isFailureAlreadyOccurred(next.getDescription())) {
                        failedComponent.addFailure(next);
                        next.occurred(BigDecimal.valueOf(fault.getTimestamp().intValue() + 1));
                        failuresOccurredTimes.computeIfAbsent(next.getDescription(), k -> new ArrayList<>()).add(next.getTimestamp());
                        failuresOccurred.add(next);
                        propagate(next, failedComponent);
                    }
                }
            }
        }
    }

    private List<ErrorMode> getErrorModesFromFault(Fault fault, MetaComponent metaComponent){
        return metaComponent.getErrorModes().stream().filter(x-> x.checkFaultIsPresent(fault.getFaultMode().getName())).collect(Collectors.toList());
    }

    /**
     * Method propagate(Failure, component) is called recursively following the PropagationPorts inside failConnections,
     * declared before at Knowledge level. The propagation stops if there's no more PropagationPorts for a certain Failure.
     * @param fault the failure that's being propagated.
     */
    private void propagate(Fault fault){
        Fault next;

        /*
            //System.out.println(failure.getDescription()+" is occurred at time "+failure.getTimestamp().toString());
            Failure next;
            List<PropagationPort> thisconnection = failConnections.get(failure.getFailureMode().getDescription());
            if(thisconnection!=null) {
                //Propagation in the component, fault->failure inside the same component
                for (PropagationPort pp : thisconnection) {
                    ErrorMode em = pp.getErrorMode();
                    if (em != null) {
                        if (em.checkActivationFunction()) {
                            next = new Failure(pp.getFailOut().getDescription(), pp.getFailOut());
                            Component failedComponent = system.stream()
                                    .filter(component -> pp.getMetaComponent().equals(component.getComponentType()))
                                    .findAny()
                                    .orElse(null);
                            if(!failedComponent.isFailureAlreadyOccurred(next.getDescription())){
                                failedComponent.addFailure(next);
                                next.occurred(BigDecimal.valueOf(failure.getTimestamp().intValue()+1));
                                failuresOccurredTimes.computeIfAbsent(next.getDescription(),k->new ArrayList<>()).add(next.getTimestamp());
                                failuresOccurred.add(next);
                                propagate(next);
                            }
                        }
                        //else
                            //System.out.println(em.getName() + "'s Activation Function is not Satisfied");
                    }
                    else{
                        //Propagation in the hierarchy, failure->fault from a component to another
                        next = new Failure(pp.getFailOut().getDescription(), pp.getFailOut());
                        next.occurred(failure.getTimestamp());
                        //extract the component affected by the failure by checking the metacomponent attribute
                        Component affectedComponent = system.stream()
                                .filter(component -> pp.getMetaComponent().equals(component.getComponentType()))
                                .findAny()
                                .orElse(null);
                        affectedComponent.addFailure(next);
                        propagate(next);

                        //Propagate the fault into the components
                        /*
                        for(Component component: affectedComponents) {
                            component.addFailure(next);
                            //failuresOccurred.putIfAbsent(next.getDescription(), next);
                            propagate(next, component);
                        }

                    }
                }
            }
            //else
                //System.out.println("PropEnded");
         */
    }
    /**
     * In a visitor design pattern way, the Scenario Class accepts a Visit from the PetriNetTranslator class
     * to translate all the information concerning failure occurrences and their timestamps into petriNet places
     * and markings
     * @param pnt a PetriNetTranslator instance
     */
    public void accept(PetriNetTranslator pnt){
        for(Fault f : this.incomingFaults) {
            pnt.decorateOccurrence(f, f.getTimestamp());
        }

    }

    public void printReport(){
        for(Component component: system){
            System.out.println("\n" +
                    "Component: "+component.getSerial()+
                    " of Type: "+ component.getComponentType().getName()+
                    " has Faults: \n");
            for(Fault fault : component.getFaultList()){
                System.out.println(fault.getDescription()+ " Occurred at time: "+ fault.getTimestamp());
            }
        }
    }
}
