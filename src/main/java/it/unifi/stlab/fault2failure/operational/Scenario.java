package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;

import java.math.BigDecimal;
import java.util.*;

/**
 * Class Scenario gives a test scenario in which we want to simulate how
 * a set of incoming faults will propagate into failures in a certain system.
 * Needs a map of <String,List<PropagationPort>>
 */
public class Scenario {
    private List<Failure> incomingFaults;
    private List<Failure> failuresOccurred;

    private final HashMap<String, BigDecimal> incomingFaultTimes;
    private final HashMap<String, List<BigDecimal>> failuresOccurredTimes;

    private HashMap<String, List<PropagationPort>> failConnections;


    private List<Component> system;

    public Scenario(){
        incomingFaults = new ArrayList<>(); //List of failures in the scenario
        failuresOccurred = new ArrayList<>(); //List of failures occurred after the propagation
        incomingFaultTimes = new HashMap<>(); //Map with incoming Fault linked to their occurrence time
        failuresOccurredTimes = new HashMap<>(); //Map with occurred Failures linked to their occurrence time
    }
    public Scenario(HashMap<String, List<PropagationPort>> failConnections, List<Component> currentSystem){
        this();
        this.failConnections=failConnections;
        this.system =currentSystem;
    }

    public void addFault(Failure fail, BigDecimal timestamp){
        //Add the fault to the incomingFault list,
        //Add it also to the incomingFaultTimes with its timestamp
        if(!incomingFaults.contains(fail))
            incomingFaults.add(fail);
        if(!incomingFaultTimes.containsKey(fail.getDescription()))
            incomingFaultTimes.put(fail.getDescription(),timestamp);

    }

    public void addFault(Failure fail, BigDecimal timestamp, Component component){
        //Add the fault to the incomingFault list,
        //Add it also to the incomingFaultTimes with its timestamp
        if(!incomingFaults.contains(fail))
            incomingFaults.add(fail);
        if(!incomingFaultTimes.containsKey(fail.getDescription()))
            incomingFaultTimes.put(fail.getDescription(),timestamp);
        component.addFailure(fail);


    }
    public void removeFailure(Failure fail){
        if(incomingFaults.contains(fail))
            incomingFaults.remove(fail);
        if(incomingFaultTimes.containsKey(fail.getDescription()))
            incomingFaultTimes.remove(fail.getDescription());

    }
    public List<Failure> getIncomingFaults(){
        return this.incomingFaults;
    }
    public List<Failure> getFailuresOccurred(){
        return this.failuresOccurred;
    }
    public HashMap<String, List<BigDecimal>> getFailuresOccurredWithTimes(){ return this.failuresOccurredTimes;}
    public void setFailConnections(HashMap<String, List<PropagationPort>> failConnections){
        this.failConnections=failConnections;
    }
    public void setSystem(List<Component> system) {
        this.system = system;
    }

    /**
     * Method propagate orders the Faults inside the incomingFaultTimes map by Timestamp, then iteratively calls the
     * method propagate(Failure, Component) in a chronological way.
     */
    public void propagate(){
        Failure fault;
        LinkedHashMap<String, BigDecimal> Ordered_failOccurrences = sortFaultOccurrencesByTimestamp(incomingFaultTimes);
        for (Map.Entry<String, BigDecimal> mapElement : Ordered_failOccurrences.entrySet()){
            fault = incomingFaults.stream()
                    .filter(failure -> mapElement.getKey().equals(failure.getDescription()))
                    .findAny()
                    .orElse(null);
            fault.occurred(mapElement.getValue());
            propagate(fault);
        }
    }

    /**
     * Method propagate(Failure, component) is called recursively following the PropagationPorts inside failConnections,
     * declared before at Knowledge level. The propagation stops if there's no more PropagationPorts for a certain Failure.
     * @param failure the failure that's being propagated.
     */
    private void propagate(Failure failure){
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
                        */
                    }
                }
            }
            //else
                //System.out.println("PropEnded");
    }

    /**
     * Reorder the incoming fault map by timestamp.
     * @param passedMap the map that needs to be reordered
     * @return an instance of LinkedHashMap that has all the occurrences of passedMap in input but ordered by timestamp
     */
    private LinkedHashMap<String, BigDecimal> sortFaultOccurrencesByTimestamp(
            HashMap<String, BigDecimal> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<BigDecimal> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);

        LinkedHashMap<String, BigDecimal> sortedMap =
                new LinkedHashMap<>();

        Iterator<BigDecimal> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            BigDecimal val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                BigDecimal comp1 = passedMap.get(key);
                BigDecimal comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    /**
     * In a visitor design pattern way, the Scenario Class accepts a Visit from the PetriNetTranslator class
     * to translate all the information concerning failure occurrences and their timestamps into petriNet places
     * and markings
     * @param pnt a PetriNetTranslator instance
     */
    public void accept(PetriNetTranslator pnt){
        for(Failure f : this.incomingFaults) {
            pnt.decorateOccurrence(f, incomingFaultTimes.get(f.getDescription()));
        }

    }

    public void printReport(){
        for(Component component: system){
            System.out.println("\n" +
                    "Component: "+component.getSerial()+
                    " of Type: "+ component.getComponentType().getName()+
                    " has Failures: \n");
            for(Failure failure: component.getFailureList()){
                System.out.println(failure.getDescription()+ " Occurred at time: "+failure.getTimestamp());
            }
        }
    }
}
