package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
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

        Fault sensor1_ED = new Fault("sensor1_ED", faultModes.get("Sensor1_ED"));
        Fault sensor2_MD = new Fault("sensor2_MD", faultModes.get("Sensor2_MD"));
        Fault valve1_MD = new Fault("valve1_MD", faultModes.get("Valve1_MD"));

        Scenario scenario = new Scenario(system);

        Map<String, Component> current_system = scenario.getCurrentSystemMap();
        scenario.addFault(sensor1_ED, BigDecimal.valueOf(12), current_system.get("Sensor1_Base"));
        scenario.addFault(sensor2_MD, BigDecimal.valueOf(13), current_system.get("Sensor2_Base"));
        scenario.addFault(valve1_MD, BigDecimal.valueOf(16), current_system.get("Valve1_Base"));
        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();

        java.lang.System.out.println(scenario.getFailuresOccurredWithTimes().toString());

        XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(system, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
    }
}
