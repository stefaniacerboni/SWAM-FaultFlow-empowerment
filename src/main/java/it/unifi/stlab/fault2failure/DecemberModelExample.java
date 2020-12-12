package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportToXPN;
import it.unifi.stlab.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.DecemberModelBuilder;
import it.unifi.stlab.fault2failure.operational.Scenario;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class DecemberModelExample {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        HashMap<String, FaultMode> faultModes = DecemberModelBuilder.getInstance().getFaultModes();
        System s = DecemberModelBuilder.getInstance().getSystem();
        PetriNetTranslator pnt = new PetriNetTranslator();
        pnt.translate(s);

        Scenario scenario = new Scenario();
        DecemberModelBuilder.createBaseDigitalTwin(scenario, s, "_Serial");
        DecemberModelBuilder.injectFaultsIntoScenario(scenario, "_Serial");
        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();

        java.lang.System.out.println("Failures Occurred With Times");
        java.lang.System.out.println(scenario.getFailuresOccurredWithTimes().toString());
        java.lang.System.out.println("Multiple Failure List");
        java.lang.System.out.println(scenario.getMultiFailuresList().toString());

        XPNExporter.export(new File("DecemberModel_Fault2Failure.xpn"),
                           new OrderByComponentToXPN(s, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("DecemberModel_Fault2Failure_Basic.xpn"),
                            new BasicExportToXPN(pnt.getPetriNet(), pnt.getMarking()));
    }
}
