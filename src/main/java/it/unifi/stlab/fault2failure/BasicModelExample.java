package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Fault;
import it.unifi.stlab.fault2failure.operational.Scenario;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicModelExample {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getInstance().getFaultModes();
        System sys = BasicModelBuilder.getInstance().getSystem();

        PetriNetTranslator pnt = new PetriNetTranslator();

        pnt.translate(BasicModelBuilder.getInstance().getSystem());

        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"));

        Scenario scenario = new Scenario(sys);
        Map<String, Component> current_system = scenario.getCurrentSystemMap();

        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10), current_system.get("Leaf_A_Base"));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13), current_system.get("Leaf_A_Base"));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16), current_system.get("Leaf_A_Base"));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12), current_system.get("Leaf_B_Base"));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18), current_system.get("Leaf_B_Base"));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17), current_system.get("Root_C_Base"));

        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();
        java.lang.System.out.println(pnt.getMarking().toString());
        XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(sys, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
    }
}
