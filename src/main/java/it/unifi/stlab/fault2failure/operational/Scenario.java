package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;

import javax.ejb.DuplicateKeyException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class Scenario gives a test scenario in which we want to simulate how
 * a set of incoming faults will propagate into failures in a certain system.
 */
public class Scenario {

	private final List<Event> incomingEvents;
	private final HashMap<String, ConcreteComponent> eventComponents;
	private final HashMap<String, BigDecimal> errorDelays;


	private final List<Fault> failuresOccurred;
	private final HashMap<String, BigDecimal> failuresOccurredTimes;
	private final HashMap<String, List<BigDecimal>> multiFailuresList;

	private List<ConcreteComponent> system;

	public Scenario() {
		incomingEvents = new ArrayList<>();
		eventComponents = new HashMap<>();
		errorDelays = new HashMap<>();

		failuresOccurred = new ArrayList<>(); //List of failures occurred after the propagation
		failuresOccurredTimes = new HashMap<>(); //Map with occurred Failures linked to their occurrence time
		multiFailuresList = new HashMap<>();
	}

	public Scenario(System system) {
		this();
		this.system = system.getComponents().stream()
		                    .map(c -> new ConcreteComponent(c.getName() + "_Base", c))
		                    .collect(Collectors.toList());
	}

	public void addCustomErrorDelay(Error error) {
		errorDelays.computeIfAbsent(error.getErrorMode().getName(), k -> error.getTimestamp());
	}

	public void addEvent(Event event, ConcreteComponent concreteComponent){
		if(event instanceof Error){
			addCustomErrorDelay((Error)event);
		}
		else {
			incomingEvents.add(event);
			eventComponents.put(event.getDescription(), concreteComponent);
			if(event instanceof Fault){
				concreteComponent.addFailure((Fault)event);
			}
		}
	}

	public void removeEvent(Event event){
		incomingEvents.remove(event);
	}

	public List<Event> getIncomingEvents() {
		return incomingEvents;
	}

	public List<Fault> getFailuresOccurred() {
		return this.failuresOccurred;
	}

	public HashMap<String, List<BigDecimal>> getMultiFailuresList() {
		return multiFailuresList;
	}


	public LinkedHashMap<String, BigDecimal> getFailuresOccurredWithTimes() {
		LinkedHashMap<String, BigDecimal> sortedMap = new LinkedHashMap<>();
		failuresOccurredTimes.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		return sortedMap;
	}

	public Map<String, ConcreteComponent> getCurrentSystemMap() {
		return this.system.stream().collect(Collectors.toMap(ConcreteComponent::getSerial, Function.identity()));
	}

	public void setSystem(List<ConcreteComponent> system) {
		this.system = system;
	}

	/**
	 * Method propagate orders the Faults inside the incomingFaultTimes map by Timestamp, then iteratively calls the
	 * method propagate(Fault, Component) in a chronological way.
	 */

	public void propagate(){
		List<Event> orderedList =
				incomingEvents.stream().sorted(Comparator.comparing(Event::getTimestamp)).collect(Collectors.toList());
		for(Event event: orderedList){
			ConcreteComponent affectedConcreteComponent = eventComponents.get(event.getDescription());
			if(event instanceof Fault){
				propagate((Fault) event, affectedConcreteComponent);
			}
			if(event instanceof Failure){
				propagateFailure((Failure) event, affectedConcreteComponent);
			}
		}

	}

	private void propagate(Fault fault, ConcreteComponent affectedConcreteComponent) {
		fault.occurred();
		double occurredTime;
		for (ErrorMode em : getErrorModesFromFault(fault, affectedConcreteComponent.getComponentType())) {
			if (em.checkActivationFunction()) {
				if(errorDelays.get(em.getName())==null)
					occurredTime = fault.getTimestamp().doubleValue() + em.getTimetofailurePDF().sample();
				else
					occurredTime = fault.getTimestamp().doubleValue() + errorDelays.get(em.getName()).doubleValue();
				//modifica considerando anche se il delay dell'errorMode è stato specificato deterministico
				Failure failure = new Failure(em.getOutgoingFailure().getDescription()+"Occurrence", em.getOutgoingFailure(), BigDecimal.valueOf(occurredTime));
				propagateFailure(failure, affectedConcreteComponent);
			}
		}
	}


	private void propagateFailure(Failure failure, ConcreteComponent affectedConcreteComponent) {
		if (failuresOccurredTimes.get(failure.getFailureMode().getDescription()) != null) {
			double currentTime = failuresOccurredTimes.get(failure.getFailureMode().getDescription()).doubleValue();
			if (failure.getTimestamp().doubleValue() < currentTime ) //se è già avvenuto con tempo > rispetto ad adesso, aggiorna i
				// minimal cutset
				failuresOccurredTimes.replace(failure.getFailureMode().getDescription(),
						failure.getTimestamp());
			multiFailuresList.computeIfAbsent(failure.getFailureMode().getDescription(),
					f -> new ArrayList<>()).add(BigDecimal.valueOf(currentTime));
			multiFailuresList.get(failure.getFailureMode().getDescription()).add(failure.getTimestamp());
		} else
			failuresOccurredTimes.put(failure.getFailureMode().getDescription(),
					failure.getTimestamp());
		Fault next;
		List<PropagationPort> propagationPorts =
				affectedConcreteComponent.getComponentType().getPropagationPorts().stream().filter(x -> x.getPropagatedFailureMode().equals(failure.getFailureMode())).collect(Collectors.toList());
		for (PropagationPort propagationPort : propagationPorts) {
			FaultMode exoFault = propagationPort.getExogenousFaultMode();
			ConcreteComponent failedConcreteComponent = system.stream()
					.filter(component -> propagationPort.getAffectedComponent().equals(component.getComponentType()))
					.findAny()
					.orElse(null);
			if (!failedConcreteComponent.isFailureAlreadyOccurred(exoFault.getName())) {
				next = new Fault(exoFault.getName(), exoFault, failure.getTimestamp());
				failedConcreteComponent.addFailure(next);
				next.occurred();
				failuresOccurredTimes.put(next.getDescription(), next.getTimestamp());
				failuresOccurred.add(next);
				propagate(next, failedConcreteComponent);
			}
		}
	}


	private List<ErrorMode> getErrorModesFromFault(Fault fault, Component component) {
		return component.getErrorModes().stream().filter(x -> x.checkFaultIsPresent(fault.getFaultMode().getName())).collect(Collectors.toList());
	}

	/**
	 * In a visitor design pattern way, the Scenario Class accepts a Visit from the PetriNetTranslator class
	 * to translate all the information concerning Fault and Failure occurrences and their timestamps into petriNet places
	 * and markings
	 *
	 * @param pnt a PetriNetTranslator instance
	 */

	public void accept(PetriNetTranslator pnt){
		for(Event e: this.incomingEvents){
			pnt.decorate(e, e.getTimestamp());
		}
	}

	public void printReport() {
		for (ConcreteComponent concreteComponent : system) {
			java.lang.System.out.println(
					"Component: " + concreteComponent.getSerial() +
							" of Type: " + concreteComponent.getComponentType().getName() +
							" has Faults:");
			for (Fault fault : concreteComponent.getFaultList()) {
				java.lang.System.out.println(fault.getDescription() + " Occurred at time: " + fault.getTimestamp());
			}
			java.lang.System.out.println();
		}
	}
}
