package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Failure;
import it.unifi.stlab.fault2failure.operational.Scenario;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicModelExample {
    public static void main( String[] args ) throws JAXBException, FileNotFoundException {
		BasicModelBuilder.build();
		HashMap<String, MetaComponent> components = BasicModelBuilder.getMetaComponents();
		HashMap<String, FaultMode> failModes = BasicModelBuilder.getFaultModes();
		HashMap<String, List<PropagationPort>> failConnections = BasicModelBuilder.getFailConnections();
		HashMap<String, ErrorMode> errorModes = BasicModelBuilder.getErrorModes();
		System sys = BasicModelBuilder.getSystem();

		PetriNetTranslator pnt = new PetriNetTranslator();
		List<PropagationPort> pplist = new ArrayList<>();
		for (Map.Entry<String, List<PropagationPort>> mapElement : BasicModelBuilder.getFailConnections().entrySet()){
			pplist.addAll(mapElement.getValue());
		}
		pnt.translate(BasicModelBuilder.getSystem());
		//System.out.println( pnt.getPetriNet().toString() +"\n");
		//System.out.println( pnt.getMarking().toString() +"\n");

		Failure A_fault1Occurred = new Failure("A_fault1Occurred", failModes.get("A_Fault1"));
		Failure A_fault2Occurred = new Failure("A_fault2Occurred", failModes.get("A_Fault2"));
		Failure A_fault3Occurred = new Failure("A_fault3Occurred", failModes.get("A_Fault3"));

		Failure B_fault1Occurred = new Failure("B_fault1Occurred", failModes.get("B_Fault1"));
		Failure B_fault2Occurred = new Failure("B_fault2Occurred", failModes.get("B_Fault2"));
		Failure C_fault6Occurred = new Failure("C_fault6Occurred", failModes.get("C_Fault6"));

		List<Component> current_system = new ArrayList<>();
		Component a = new Component("A_Serial", components.get("Leaf_A"));
		Component b = new Component("B_Serial", components.get("Leaf_B"));
		Component c = new Component("C_Serial", components.get("Root_C"));
		current_system.add(a);
		current_system.add(b);
		current_system.add(c);

		Scenario scenario = new Scenario(failConnections, current_system);

		scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10), a);
		scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13), a);
		scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16), a);
		scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12), b);
		scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18), b);
		scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17), c);

		scenario.accept(pnt);
		scenario.propagate();
		scenario.printReport();
		java.lang.System.out.println(pnt.getMarking().toString());
		XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(sys, pnt.getPetriNet(), pnt.getMarking()));
		XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
		}
}
