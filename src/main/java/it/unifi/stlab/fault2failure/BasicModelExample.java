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

public class BasicModelExample {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        HashMap<String, MetaComponent> components = BasicModelBuilder.getInstance().getMetaComponents();
        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getInstance().getFaultModes();
        HashMap<String, List<PropagationPort>> failConnections = BasicModelBuilder.getInstance().getFailConnections();
        System sys = BasicModelBuilder.getInstance().getSystem();

        PetriNetTranslator pnt = new PetriNetTranslator();

        pnt.translate(BasicModelBuilder.getInstance().getSystem());
        //System.out.println( pnt.getPetriNet().toString() +"\n");
        //System.out.println( pnt.getMarking().toString() +"\n");

        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"));

        List<Component> current_system = new ArrayList<>();
        Component a = new Component("A_Serial", components.get("Leaf_A"));
        Component b = new Component("B_Serial", components.get("Leaf_B"));
        Component c = new Component("C_Serial", components.get("Root_C"));
        current_system.add(a);
        current_system.add(b);
        current_system.add(c);

        Scenario scenario = new Scenario(current_system);

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
