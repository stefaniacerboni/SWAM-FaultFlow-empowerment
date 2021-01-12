package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class Scenario gives a test scenario in which we want to simulate how
 * a set of incoming faults will propagate into failures in a certain system.
 */
public class Scenario {

	private final List<Fault> incomingFaults;
	private final List<Fault> failuresOccurred;
	private final HashMap<String, BigDecimal> failuresOccurredTimes;
	private final HashMap<String, ConcreteComponent> failureComponents;
	private final HashMap<String, List<BigDecimal>> multiFailuresList;

	private List<ConcreteComponent> system;

	public Scenario() {
		incomingFaults = new ArrayList<>(); //List of failures in the scenario
		failuresOccurred = new ArrayList<>(); //List of failures occurred after the propagation
		failuresOccurredTimes = new HashMap<>(); //Map with occurred Failures linked to their occurrence time
		failureComponents = new HashMap<>();
		multiFailuresList = new HashMap<>();
	}

	public Scenario(System system) {
		this();
		this.system = system.getComponents().stream()
		                    .map(c -> new ConcreteComponent(c.getName() + "_Base", c))
		                    .collect(Collectors.toList());
	}

	public void addFault(Fault fail, BigDecimal timestamp, ConcreteComponent concreteComponent) {
		//Add the fault to the incomingFault list
		fail.occurred(timestamp);
		if (!incomingFaults.contains(fail))
			incomingFaults.add(fail);
		concreteComponent.addFailure(fail);
		failureComponents.put(fail.getDescription(), concreteComponent);
	}

	public void removeFailure(Fault fail) {
		incomingFaults.remove(fail);
	}

	public List<Fault> getIncomingFaults() {
		return this.incomingFaults;
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
	 * method propagate(Failure, Component) in a chronological way.
	 */
	public void propagate() {
		List<Fault> orderedList =
				incomingFaults.stream().sorted(Comparator.comparing(Fault::getTimestamp)).collect(Collectors.toList());
		for (Fault fault : orderedList) {
			ConcreteComponent affectedConcreteComponent = failureComponents.get(fault.getDescription());
			propagate(fault, affectedConcreteComponent);
		}
	}

	private void propagate(Fault fault, ConcreteComponent affectedConcreteComponent) {
		Fault next;
		fault.getFaultMode().setState(true);
		double occurredTime;
		for (ErrorMode em : getErrorModesFromFault(fault, affectedConcreteComponent.getComponentType())) {
			if (em.checkActivationFunction()) {
				occurredTime = fault.getTimestamp().doubleValue() + em.getTimetofailurePDF().sample();
				if (failuresOccurredTimes.get(em.getOutgoingFailure().getDescription()) != null) {
					double currentTime = failuresOccurredTimes.get(em.getOutgoingFailure().getDescription()).doubleValue();
					if (currentTime > occurredTime) //se è già avvenuto con tempo > rispetto ad adesso, aggiorna i
					    // minimal cutset
						failuresOccurredTimes.replace(em.getOutgoingFailure().getDescription(),
                                                      BigDecimal.valueOf(occurredTime));
					multiFailuresList.computeIfAbsent(em.getOutgoingFailure().getDescription(),
                                                      f -> new ArrayList<>()).add(BigDecimal.valueOf(currentTime));
					multiFailuresList.get(em.getOutgoingFailure().getDescription()).add(BigDecimal.valueOf(occurredTime));
				} else
					failuresOccurredTimes.put(em.getOutgoingFailure().getDescription(),
                                              BigDecimal.valueOf(occurredTime));
                List<PropagationPort> propagationPorts =
                        affectedConcreteComponent.getComponentType().getPropagationPorts().stream().filter(x -> x.getPropagatedFailureMode().equals(em.getOutgoingFailure())).collect(Collectors.toList());
                for (PropagationPort propagationPort : propagationPorts) {
					FaultMode exoFault = propagationPort.getExogenousFaultMode();
					next = new Fault(exoFault.getName(), exoFault);
					ConcreteComponent failedConcreteComponent = system.stream()
					                                  .filter(component -> propagationPort.getAffectedComponent().equals(component.getComponentType()))
					                                  .findAny()
					                                  .orElse(null);
					if (!failedConcreteComponent.isFailureAlreadyOccurred(next.getDescription())) {
						failedConcreteComponent.addFailure(next);
						next.occurred(BigDecimal.valueOf(occurredTime));
						failuresOccurredTimes.put(next.getDescription(), next.getTimestamp());
						failuresOccurred.add(next);
						propagate(next, failedConcreteComponent);
					}
				}
			}
		}
	}

	private List<ErrorMode> getErrorModesFromFault(Fault fault, Component component) {
		return component.getErrorModes().stream().filter(x -> x.checkFaultIsPresent(fault.getFaultMode().getName())).collect(Collectors.toList());
	}

	/**
	 * In a visitor design pattern way, the Scenario Class accepts a Visit from the PetriNetTranslator class
	 * to translate all the information concerning failure occurrences and their timestamps into petriNet places
	 * and markings
	 *
	 * @param pnt a PetriNetTranslator instance
	 */
	public void accept(PetriNetTranslator pnt) {
		for (Fault f : this.incomingFaults) {
			pnt.decorateOccurrence(f, f.getTimestamp());
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
