package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
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
	private final HashMap<String, BigDecimal> failuresOccurredTimes;
	private final List<Fault> incomingFaults;
	private final List<Fault> failuresOccurred;
	private final HashMap<String, Component> failureComponents;

	private List<Component> system;

	public Scenario() {
		incomingFaults = new ArrayList<>(); //List of failures in the scenario
		failuresOccurred = new ArrayList<>(); //List of failures occurred after the propagation
		failuresOccurredTimes = new HashMap<>(); //Map with occurred Failures linked to their occurrence time
		failureComponents = new HashMap<>();
	}

	public Scenario(System system) {
		this();
		this.system = system.getComponents().stream()
		                    .map(c -> new Component(c.getName() + "_Base", c))
		                    .collect(Collectors.toList());
	}

	public void addFault(Fault fail, BigDecimal timestamp, Component component) {
		//Add the fault to the incomingFault list
		fail.occurred(timestamp);
		if (!incomingFaults.contains(fail))
			incomingFaults.add(fail);
		component.addFailure(fail);
		failureComponents.put(fail.getDescription(), component);
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

	public LinkedHashMap<String, BigDecimal> getFailuresOccurredWithTimes() {
		LinkedHashMap<String, BigDecimal> sortedMap = new LinkedHashMap<>();
		failuresOccurredTimes.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x-> sortedMap.put(x.getKey(), x.getValue()));
		return sortedMap;
	}

	public Map<String, Component> getCurrentSystemMap() {
		return this.system.stream().collect(Collectors.toMap(Component::getSerial, Function.identity()));
	}

	public void setSystem(List<Component> system) {
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
			Component affectedComponent = failureComponents.get(fault.getDescription());
			propagate(fault, affectedComponent);
		}
	}

	private void propagate(Fault fault, Component affectedComponent) {
		Fault next;
		fault.getFaultMode().setState(true);
		int occurredTime;
		for (ErrorMode em : getErrorModesFromFault(fault, affectedComponent.getComponentType())) {
			if (em.checkActivationFunction()) {
				occurredTime = fault.getTimestamp().intValue() + 1;
				if(failuresOccurredTimes.get(em.getOutgoingFailure().getDescription()) != null){
					if (failuresOccurredTimes.get(em.getOutgoingFailure().getDescription()).intValue() > occurredTime) //se è già avvenuto con tempo> rispetto ad adesso
						failuresOccurredTimes.replace(em.getOutgoingFailure().getDescription(), BigDecimal.valueOf(occurredTime));
				}
				else
					failuresOccurredTimes.put(em.getOutgoingFailure().getDescription(), BigDecimal.valueOf(occurredTime));
				Optional<PropagationPort> propagationPort =
                        affectedComponent.getComponentType().getPropagationPort().stream().filter(x -> x.getPropagatedFailureMode().equals(em.getOutgoingFailure())).findAny();
				if (propagationPort.isPresent()) {
					FaultMode exoFault = propagationPort.get().getExogenousFaultMode();
					next = new Fault(exoFault.getName(), exoFault);
					Component failedComponent = system.stream()
					                                  .filter(component -> propagationPort.get().getAffectedComponent().equals(component.getComponentType()))
					                                  .findAny()
					                                  .orElse(null);
					if (!failedComponent.isFailureAlreadyOccurred(next.getDescription())) {
						failedComponent.addFailure(next);
						next.occurred(BigDecimal.valueOf(fault.getTimestamp().intValue() + 1));
						failuresOccurredTimes.put(next.getDescription(),next.getTimestamp());
						failuresOccurred.add(next);
						propagate(next, failedComponent);
					}
				}
			}
		}
	}

	private List<ErrorMode> getErrorModesFromFault(Fault fault, MetaComponent metaComponent) {
		return metaComponent.getErrorModes().stream().filter(x -> x.checkFaultIsPresent(fault.getFaultMode().getName())).collect(Collectors.toList());
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
		for (Component component : system) {
			java.lang.System.out.println(
					"Component: " + component.getSerial() +
							" of Type: " + component.getComponentType().getName() +
							" has Faults:");
			for (Fault fault : component.getFaultList()) {
				java.lang.System.out.println(fault.getDescription() + " Occurred at time: " + fault.getTimestamp());
			}
			java.lang.System.out.println();
		}
	}
}
