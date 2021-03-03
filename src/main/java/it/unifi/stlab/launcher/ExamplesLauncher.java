package it.unifi.stlab.launcher;

import it.unifi.stlab.faultflow.exporter.PetriNetExportMethod;
import it.unifi.stlab.faultflow.exporter.PetriNetExporter;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FailureMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.model.operational.Failure;
import it.unifi.stlab.faultflow.model.operational.Fault;
import it.unifi.stlab.faultflow.model.operational.Scenario;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;
import it.unifi.stlab.faultflow.translator.PetriNetTranslatorMethod;
import it.unifi.stlab.launcher.systembuilder.PollutionMonitorIdealDesignBuilder;
import it.unifi.stlab.launcher.systembuilder.PollutionMonitorPreliminaryDesignBuiler;
import it.unifi.stlab.launcher.systembuilder.SimpleSystem02Builder;
import it.unifi.stlab.launcher.systembuilder.SteamBoilerModelBuilder;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;

public class ExamplesLauncher {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        //Per cambiare sistema da buildare è sufficiente modificare l'istruzione seguente
        //con il builder del sistema desiderato.
        System s = PollutionMonitorIdealDesignBuilder.getInstance().getSystem();
        //Exporting petri net as fault injection -faults with deterministic occurrence sampled from the pdfs
        PetriNetExporter.exportPetriNetFromSystem(s, PetriNetExportMethod.FAULT_INJECTION);
        //Exporting petri net as fault analysis -faults with their original pdf
        PetriNetTranslator pnt = PetriNetExporter.exportPetriNetFromSystem(s, PetriNetExportMethod.FAULT_ANALYSIS);

        Scenario scenario = new Scenario(s, "_Serial");
        scenario.InitializeScenarioFromSystem();
        scenario.accept(pnt, PetriNetTranslatorMethod.CONCURRENT);
        PetriNetExporter.exportPetriNetOrderedByComponent(pnt, s);
        scenario.propagate();
        scenario.printReport();

        java.lang.System.out.println("Failures Occurred With Times");
        java.lang.System.out.println(scenario.getFailuresOccurredWithTimes().toString());
        java.lang.System.out.println("Multiple Failure List");
        java.lang.System.out.println(scenario.getMultiFailuresList().toString());
    }
}
