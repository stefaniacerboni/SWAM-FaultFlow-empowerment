package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.PetriNetExportMethod;
import it.unifi.stlab.exporter.PetriNetExporter;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.DecemberModelBuilder;
import it.unifi.stlab.fault2failure.operational.Fault;
import it.unifi.stlab.fault2failure.operational.Scenario;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;

public class DecemberModelExample {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        HashMap<String, FaultMode> faultModes = DecemberModelBuilder.getInstance().getFaultModes();
        System s = DecemberModelBuilder.getInstance().getSystem();
        //Exporting petri net as fault injection -faults with deterministic occurrence
        PetriNetExporter.exportPetriNetFromSystem(s, PetriNetExportMethod.FAULT_INJECTION);
        //Exporting petri net as fault analysis -faults with their original pdf
        PetriNetTranslator pnt = PetriNetExporter.exportPetriNetFromSystem(s, PetriNetExportMethod.FAULT_ANALYSIS);

        Scenario scenario = new Scenario(s, "_Serial");
        scenario.InitializeScenarioFromSystem();
        //DecemberModelBuilder.createBaseDigitalTwin(scenario, s, "_Serial");
        //DecemberModelBuilder.injectFaultsIntoScenario(scenario, "_Serial");
        Fault B_fault2Occurred = new Fault("B_Fault2Occurred", faultModes.get("B_Fault2"), BigDecimal.valueOf(20.0));
        scenario.addEvent(B_fault2Occurred, scenario.getCurrentSystemMap().get("B_Serial"));
        scenario.accept(pnt);
        PetriNetExporter.exportPetriNet(pnt);
        scenario.propagate();
        scenario.printReport();

        java.lang.System.out.println("Failures Occurred With Times");
        java.lang.System.out.println(scenario.getFailuresOccurredWithTimes().toString());
        java.lang.System.out.println("Multiple Failure List");
        java.lang.System.out.println(scenario.getMultiFailuresList().toString());


    }
}
