package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;

import java.util.HashMap;

public class PollutionMonitorModelBuilder {
    private static PollutionMonitorModelBuilder single_instance;
    private static System system;
    private static HashMap<String, FaultMode> faultModes;
    private static HashMap<String, FailureMode> failureModes;
    private static HashMap<String, ErrorMode> errorModes;

    private PollutionMonitorModelBuilder(){
        faultModes = new HashMap<>();
        failureModes = new HashMap<>();
        errorModes = new HashMap<>();

        // Definizione composizione del sistema

        system = new System("PollutionMonitorSystem");
        Component pollutionMonitor = new Component("PollutionMonitor");
        Component persistenceSystem = new Component("PersistenceSystem");
        Component smartAgent = new Component("SmartAgent");
        Component dataStorage = new Component("DataStorage");
        Component ingestionSubsystem = new Component("IngestionSubsystem");
        Component iotBroker = new Component("IoTBroker");
        Component fieldDeviceSubsystem = new Component("FieldDeviceSubsystem");
        Component mapReduceProcessor = new Component("MapReduceProcessor");
        Component pollutantSensor = new Component("PollutantSensor");


        system.addComponent(pollutionMonitor,
                persistenceSystem, smartAgent,
                dataStorage, ingestionSubsystem,
                iotBroker, fieldDeviceSubsystem, mapReduceProcessor,
                pollutantSensor);
        system.setTopLevelComponent(pollutionMonitor);

        CompositionPort psFds = new CompositionPort(pollutantSensor, fieldDeviceSubsystem);
        fieldDeviceSubsystem.addCompositionPorts(psFds);

        CompositionPort iotBIs = new CompositionPort(iotBroker, ingestionSubsystem);
        CompositionPort fdsIs = new CompositionPort(fieldDeviceSubsystem, ingestionSubsystem);
        CompositionPort mrpIs = new CompositionPort(mapReduceProcessor, ingestionSubsystem);
        ingestionSubsystem.addCompositionPorts(iotBIs, fdsIs, mrpIs);

        CompositionPort dsPs = new CompositionPort(dataStorage, persistenceSystem);
        CompositionPort isPS = new CompositionPort(ingestionSubsystem, persistenceSystem);
        persistenceSystem.addCompositionPorts(dsPs, isPS);

        CompositionPort psPm = new CompositionPort(persistenceSystem, pollutionMonitor);
        CompositionPort saPm = new CompositionPort(smartAgent, pollutionMonitor);
        pollutionMonitor.addCompositionPorts(psPm, saPm);


        // Definizione di Fault Mode Endogeni

        EndogenousFaultMode enFM_iotBO = new EndogenousFaultMode("IoTBroker_BufferOverflow");
        enFM_iotBO.setArisingPDF("exp(120)");
        EndogenousFaultMode enFM_iotUB = new EndogenousFaultMode("IoTBroker_UnavailableBroker");
        enFM_iotUB.setArisingPDF("exp(730)");
        EndogenousFaultMode enFM_dsHddI = new EndogenousFaultMode("DataStorage_HDDIssue");
        enFM_dsHddI.setArisingPDF("exp(1825)");
        EndogenousFaultMode enFM_psDBMSI = new EndogenousFaultMode("PersistenceSubsystem_DBMSIssue");
        enFM_psDBMSI.setArisingPDF("exp(180)");
        EndogenousFaultMode enFM_saU = new EndogenousFaultMode("SmartAgent_Unavailable");
        enFM_saU.setArisingPDF("exp(60)");

        faultModes.put(enFM_iotBO.getName(), enFM_iotBO);
        faultModes.put(enFM_iotUB.getName(), enFM_iotUB);
        faultModes.put(enFM_dsHddI.getName(), enFM_dsHddI);
        faultModes.put(enFM_psDBMSI.getName(), enFM_psDBMSI);
        faultModes.put(enFM_saU.getName(), enFM_saU);

        // Definizione di Fault Mode Esogeni

        ExogenousFaultMode exFM_4 = new ExogenousFaultMode("EX4");
        ExogenousFaultMode exFM_5 = new ExogenousFaultMode("EX5");
        ExogenousFaultMode exFM_6 = new ExogenousFaultMode("EX6");

        faultModes.put(exFM_4.getName(), exFM_4);
        faultModes.put(exFM_5.getName(), exFM_5);
        faultModes.put(exFM_6.getName(), exFM_6);

        // Definizione delle Failure Mode per IngestionSubSystem

        FailureMode fM_IF = new FailureMode("IngestionFailure");
        ErrorMode eM_IS = new ErrorMode("IngestionSubsystem_ToFailure");
        eM_IS.addInputFaultMode(enFM_iotBO, enFM_iotUB);
        eM_IS.addOutputFailureMode(fM_IF);
        eM_IS.setEnablingCondition("IoTBroker_BufferOverflow || IoTBroker_UnavailableBroker", faultModes);
        eM_IS.setPDF("dirac(0)");
        errorModes.put(eM_IS.getName(), eM_IS);
        failureModes.put(fM_IF.getDescription(), fM_IF);


        ingestionSubsystem.addErrorMode(eM_IS);

        // Definizione delle propagation port

        ingestionSubsystem.addPropagationPort(
                new PropagationPort(fM_IF, exFM_5, smartAgent));

        // Definizione delle Failure Mode per PersistenceSubsystem

        FailureMode fM_SF = new FailureMode("StorageFailure");
        ErrorMode eM_PS = new ErrorMode("PersistenceSubsystem_ToFailure");
        eM_PS.addInputFaultMode(enFM_dsHddI, enFM_psDBMSI);
        eM_PS.addOutputFailureMode(fM_SF);
        eM_PS.setEnablingCondition("DataStorage_HDDIssue || PersistenceSubsystem_DBMSIssue", faultModes);
        eM_PS.setPDF("dirac(0)");

        errorModes.put(eM_PS.getName(), eM_PS);
        failureModes.put(fM_SF.getDescription(), fM_SF);

        persistenceSystem.addErrorMode(eM_PS);

        // Definizione delle propagation port

        persistenceSystem.addPropagationPort(
                new PropagationPort(fM_SF, exFM_4, smartAgent));

        // Definizione delle Failure Mode per SmartAgent

        FailureMode fM_NADF = new FailureMode("NoAnalysisDataFailure");
        ErrorMode eM_SA1 = new ErrorMode("SmartAgent_ToFailure1");
        eM_SA1.addInputFaultMode(exFM_4, exFM_5);
        eM_SA1.addOutputFailureMode(fM_NADF);
        eM_SA1.setEnablingCondition("EX4 || EX5", faultModes);
        eM_SA1.setPDF("exp(4)");

        errorModes.put(eM_SA1.getName(), eM_SA1);
        failureModes.put(fM_NADF.getDescription(), fM_NADF);

        FailureMode fM_NSF = new FailureMode("NoSuggestionFailure");
        ErrorMode eM_SA2 = new ErrorMode("SmartAgent_ToFailure2");
        eM_SA2.addInputFaultMode(exFM_6, enFM_saU);
        eM_SA2.addOutputFailureMode(fM_NSF);
        eM_SA2.setEnablingCondition("EX6 || SmartAgent_Unavailable", faultModes);
        eM_SA2.setPDF("dirac(0)");

        errorModes.put(eM_SA2.getName(), eM_SA2);
        failureModes.put(fM_NSF.getDescription(), fM_NSF);

        smartAgent.addErrorMode(eM_SA1);
        smartAgent.addErrorMode(eM_SA2);

        // Definizione delle propagation port

        smartAgent.addPropagationPort(
                new PropagationPort(fM_NADF, exFM_6, smartAgent));



    }

    public static PollutionMonitorModelBuilder getInstance() {
        if (single_instance == null)
            single_instance = new PollutionMonitorModelBuilder();
        return single_instance;
    }

    public System getSystem() {
        return system;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

}
