package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.NewModelBuilder;
import it.unifi.stlab.fault2failure.knowledge.utils.SteamBoilerModelBuilder;
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

public class SteamBoilerExample {
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        System system = SteamBoilerModelBuilder.getInstance().getSystem();
        HashMap<String, FaultMode> faultModes = SteamBoilerModelBuilder.getInstance().getFaultModes();
        PetriNetTranslator pnt = new PetriNetTranslator();
        pnt.translate(system);

        Scenario scenario = new Scenario();
        SteamBoilerModelBuilder.createBaseDigitalTwin(scenario, system, "_Serial");
        SteamBoilerModelBuilder.injectFaultsIntoScenario(scenario, "_Serial");
        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();

        java.lang.System.out.println(scenario.getFailuresOccurredWithTimes().toString());
        java.lang.System.out.println(scenario.getMultiFailuresList().toString());

        XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(system, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
    }
}
