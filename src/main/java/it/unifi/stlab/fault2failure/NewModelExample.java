package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.NewModelBuilder;
import it.unifi.stlab.fault2failure.operational.Scenario;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class NewModelExample {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        HashMap<String, FaultMode> faultModes = NewModelBuilder.getInstance().getFaultModes();
        System s = NewModelBuilder.getInstance().getSystem();
        PetriNetTranslator pnt = new PetriNetTranslator();
        pnt.translate(s);

        Scenario scenario = new Scenario();
        NewModelBuilder.createBaseDigitalTwin(scenario, s, "_Serial");
        NewModelBuilder.injectFaultsIntoScenario(scenario, "_Serial");
        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();

        java.lang.System.out.println(scenario.getFailuresOccurredWithTimes().toString());
        XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(s, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
    }
}
