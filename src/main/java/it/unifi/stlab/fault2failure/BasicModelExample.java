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

        Scenario scenario = new Scenario();
        BasicModelBuilder.createBaseDigitalTwin(scenario, sys, "_Serial");
        BasicModelBuilder.injectFaultsIntoScenario(scenario, "_Serial");
        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();
        //java.lang.System.out.println(pnt.getMarking().toString());
        java.lang.System.out.println("Fallimenti avvenuti, con relativo timestamp: "+scenario.getFailuresOccurredWithTimes().toString());
        XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(sys, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
    }
}
