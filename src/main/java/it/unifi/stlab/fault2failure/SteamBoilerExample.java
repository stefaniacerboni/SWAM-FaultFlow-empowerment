package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import it.unifi.stlab.fault2failure.knowledge.utils.SteamBoilerModelBuilder;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SteamBoilerExample {
    public static void main( String[] args ) throws JAXBException, FileNotFoundException {
        SteamBoilerModelBuilder.build();
        HashMap<String, MetaComponent> components = SteamBoilerModelBuilder.getMetaComponents();
        HashMap<String, FaultMode> faultModes = SteamBoilerModelBuilder.getFaultModes();
        HashMap<String, List<PropagationPort>> failConnections = SteamBoilerModelBuilder.getFailConnections();
        HashMap<String, ErrorMode> errorModes = SteamBoilerModelBuilder.getErrorModes();
        PetriNetTranslator pnt = new PetriNetTranslator();
        List<PropagationPort> pplist = new ArrayList<>();
        for (Map.Entry<String, List<PropagationPort>> mapElement : failConnections.entrySet()){
            pplist.addAll(mapElement.getValue());
        }
        pnt.translate(SteamBoilerModelBuilder.getSystem());

        /**
         * Manca un meccanismo di generazione di un Component a partire dal livello Meta generato in
         * SteamBoilerModelBuilder.
         * In questa fase sembra necessario conoscere (due volte) la composizione del sistema.
         */
        List<Component> system = new ArrayList<>();
        Component controller = new Component("controller-0", components.get("Controller"));
        Component sensor1 = new Component("sensor-1", components.get("Sensor1"));
        Component sensor2 = new Component("sensor-2", components.get("Sensor2"));
        Component sensor3 = new Component("sensor-3", components.get("Sensor3"));
        Component valve1 = new Component("valve-1", components.get("Valve1"));
        Component valve2 = new Component("valve-2", components.get("Valve2"));
        Component steamBoiler = new Component("stmBlr-1", components.get("SteamBoiler"));
        system.add(controller);
        system.add(sensor1);
        system.add(sensor2);
        system.add(sensor3);
        system.add(valve1);
        system.add(valve2);
        system.add(steamBoiler);

        Failure sensor1_ED = new Failure("sensor1_ED", faultModes.get("Sensor1_ED"));
        Failure sensor2_MD = new Failure("sensor2_MD", faultModes.get("Sensor2_MD"));
        Failure valve1_MD = new Failure("valve1_MD", faultModes.get("Valve1_MD"));

        Scenario scenario = new Scenario(failConnections,system);
        scenario.addFault(sensor1_ED, BigDecimal.valueOf(12), sensor1);
        scenario.addFault(sensor2_MD, BigDecimal.valueOf(13), sensor2);
        scenario.addFault(valve1_MD,BigDecimal.valueOf(16), valve1);
        scenario.accept(pnt);
        scenario.propagate();
        scenario.printReport();

        XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(SteamBoilerModelBuilder.getSystem(), pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
    }
}
