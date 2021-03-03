package it.unifi.stlab.launcher.systembuilder;

import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.CompositionPort;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.*;

import java.util.HashMap;

public class PollutionMonitorTargetDesignBuilder{

    private static PollutionMonitorTargetDesignBuilder single_instance;
    private static System system;
    private static HashMap<String, FaultMode> faultModes;
    private static HashMap<String, FailureMode> failureModes;
    private static HashMap<String, ErrorMode> errorModes;

    private PollutionMonitorTargetDesignBuilder() {
        faultModes = new HashMap<>();
        failureModes = new HashMap<>();
        errorModes = new HashMap<>();

        // Definizione composizione del sistema

        system = new System("PollutionMonitorSystem_Target");
        Component pollutionMonitor = new Component("PollutionMonitor");
        Component persistenceSystem = new Component("PersistenceSystem");
        Component smartAgent = new Component("SmartAgent");
        Component dataStorage = new Component("DataStorage");
        Component ingestionSubsystemOzone = new Component("IngestionSubsystemOzone");
        Component ingestionSubsystem1PM10 = new Component("IngestionSubsystem1PM10");
        Component ingestionSubsystem2PM10 = new Component("IngestionSubsystem2PM10");
        Component ingestionSubsystem3PM10 = new Component("IngestionSubsystem3PM10");
        Component iotBroker = new Component("IoTBroker");
        Component fieldDeviceSubsystemOzone = new Component("FieldDeviceSubsystemOzone");
        Component fieldDeviceSubsystem1PM10 = new Component("FieldDeviceSubsystem1PM10");
        Component fieldDeviceSubsystem2PM10 = new Component("FieldDeviceSubsystem2PM10");
        Component fieldDeviceSubsystem3PM10 = new Component("FieldDeviceSubsystem3PM10");
        Component mapReduceProcessorOzone = new Component("MapReduceProcessorOzone");
        Component mapReduceProcessorPM10 = new Component("MapReduceProcessorPM10");
        Component ozoneSensor = new Component("OzoneSensor");
        Component pm10Sensor1 = new Component("PM10Sensor1");
        Component pm10Sensor2 = new Component("PM10Sensor2");
        Component pm10Sensor3 = new Component("PM10Sensor3");


        system.addComponent(pollutionMonitor,
                persistenceSystem, smartAgent,
                dataStorage, ingestionSubsystem1PM10, ingestionSubsystem2PM10, ingestionSubsystem3PM10,
                ingestionSubsystemOzone, iotBroker,
                fieldDeviceSubsystemOzone, fieldDeviceSubsystem1PM10, fieldDeviceSubsystem2PM10, fieldDeviceSubsystem3PM10,
                mapReduceProcessorOzone, mapReduceProcessorPM10,
                ozoneSensor, pm10Sensor1, pm10Sensor2, pm10Sensor3);
        system.setTopLevelComponent(pollutionMonitor);

        CompositionPort pm10Fds3 = new CompositionPort(pm10Sensor3, fieldDeviceSubsystem3PM10);
        fieldDeviceSubsystem3PM10.addCompositionPorts(pm10Fds3);
        CompositionPort pm10Fds2 = new CompositionPort(pm10Sensor2, fieldDeviceSubsystem2PM10);
        fieldDeviceSubsystem2PM10.addCompositionPorts(pm10Fds2);
        CompositionPort pm10Fds1 = new CompositionPort(pm10Sensor1, fieldDeviceSubsystem1PM10);
        fieldDeviceSubsystem1PM10.addCompositionPorts(pm10Fds1);

        CompositionPort osFdsO = new CompositionPort(ozoneSensor, fieldDeviceSubsystemOzone);
        fieldDeviceSubsystemOzone.addCompositionPorts(osFdsO);

        CompositionPort fds1Is = new CompositionPort(fieldDeviceSubsystem1PM10, ingestionSubsystem1PM10);
        ingestionSubsystem1PM10.addCompositionPorts(fds1Is);
        CompositionPort fds2Is = new CompositionPort(fieldDeviceSubsystem2PM10, ingestionSubsystem2PM10);
        ingestionSubsystem2PM10.addCompositionPorts(fds2Is);
        CompositionPort fds3Is = new CompositionPort(fieldDeviceSubsystem3PM10, ingestionSubsystem3PM10);
        ingestionSubsystem3PM10.addCompositionPorts(fds3Is);
        CompositionPort fdsoIs = new CompositionPort(fieldDeviceSubsystemOzone, ingestionSubsystemOzone);
        ingestionSubsystemOzone.addCompositionPorts(fdsoIs);

        CompositionPort mrPM10Is1 = new CompositionPort(mapReduceProcessorPM10, ingestionSubsystem1PM10);
        ingestionSubsystem1PM10.addCompositionPorts(mrPM10Is1);
        CompositionPort mrPM10Is2 = new CompositionPort(mapReduceProcessorPM10, ingestionSubsystem2PM10);
        ingestionSubsystem2PM10.addCompositionPorts(mrPM10Is2);
        CompositionPort mrPM10Is3 = new CompositionPort(mapReduceProcessorPM10, ingestionSubsystem3PM10);
        ingestionSubsystem3PM10.addCompositionPorts(mrPM10Is3);
        CompositionPort mrOIso = new CompositionPort(mapReduceProcessorOzone, ingestionSubsystemOzone);
        ingestionSubsystemOzone.addCompositionPorts(mrOIso);

        CompositionPort iotBIso = new CompositionPort(iotBroker, ingestionSubsystemOzone);
        ingestionSubsystemOzone.addCompositionPorts(iotBIso);

        CompositionPort is1Ps = new CompositionPort(ingestionSubsystem1PM10, persistenceSystem);
        CompositionPort is2Ps = new CompositionPort(ingestionSubsystem2PM10, persistenceSystem);
        CompositionPort is3Ps = new CompositionPort(ingestionSubsystem3PM10, persistenceSystem);
        CompositionPort isoPs = new CompositionPort(ingestionSubsystemOzone, persistenceSystem);
        persistenceSystem.addCompositionPorts(is1Ps, is2Ps, is3Ps, isoPs);

        CompositionPort dsPs = new CompositionPort(dataStorage, persistenceSystem);
        persistenceSystem.addCompositionPorts(dsPs);

        CompositionPort psPm = new CompositionPort(persistenceSystem, pollutionMonitor);
        CompositionPort saPm = new CompositionPort(smartAgent, pollutionMonitor);
        pollutionMonitor.addCompositionPorts(psPm, saPm);

        // Definizione di Fault Mode Endogeni


        EndogenousFaultMode enFM_dosi = new EndogenousFaultMode("DefectiveOzoneSensorIssue");
        enFM_dosi.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_poti = new EndogenousFaultMode("PublisherOzoneTopicIssue");
        enFM_poti.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_soti = new EndogenousFaultMode("SubscriberOzoneTopicIssue");
        enFM_soti.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_oswmrbi = new EndogenousFaultMode("OzoneSWMRBugIssue");
        enFM_oswmrbi.setArisingPDF("exp(1/540)");
        EndogenousFaultMode enFM_dpm10s1 = new EndogenousFaultMode("DefectivePM10Sensor1Issue");
        enFM_dpm10s1.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_dpm10s2 = new EndogenousFaultMode("DefectivePM10Sensor2Issue");
        enFM_dpm10s2.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_dpm10s3 = new EndogenousFaultMode("DefectivePM10Sensor3Issue");
        enFM_dpm10s3.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_ppm10ti1 = new EndogenousFaultMode("PublisherPM10TopicIssue1");
        enFM_ppm10ti1.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_ppm10ti2 = new EndogenousFaultMode("PublisherPM10TopicIssue2");
        enFM_ppm10ti2.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_ppm10ti3 = new EndogenousFaultMode("PublisherPM10TopicIssue3");
        enFM_ppm10ti3.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_spm10ti = new EndogenousFaultMode("SubscriberPM10TopicIssue");
        enFM_spm10ti.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_pm10swmrbi = new EndogenousFaultMode("PM10SWMRBugIssue");
        enFM_pm10swmrbi.setArisingPDF("exp(1/540)");
        EndogenousFaultMode enFM_swb= new EndogenousFaultMode("SmartAgent_SWBug");
        enFM_swb.setArisingPDF("exp(1/1080)");

        faultModes.put(enFM_dosi.getName(), enFM_dosi);
        faultModes.put(enFM_poti.getName(), enFM_poti);
        faultModes.put(enFM_soti.getName(), enFM_soti);
        faultModes.put(enFM_oswmrbi.getName(), enFM_oswmrbi);
        faultModes.put(enFM_dpm10s1.getName(), enFM_dpm10s1);
        faultModes.put(enFM_dpm10s2.getName(), enFM_dpm10s2);
        faultModes.put(enFM_dpm10s3.getName(), enFM_dpm10s3);
        faultModes.put(enFM_ppm10ti1.getName(), enFM_ppm10ti1);
        faultModes.put(enFM_ppm10ti2.getName(), enFM_ppm10ti2);
        faultModes.put(enFM_ppm10ti3.getName(), enFM_ppm10ti3);
        faultModes.put(enFM_spm10ti.getName(), enFM_spm10ti);
        faultModes.put(enFM_pm10swmrbi.getName(), enFM_pm10swmrbi);
        faultModes.put(enFM_swb.getName(), enFM_swb);

        // Definizione di Fault Mode Esogeni

        ExogenousFaultMode exFM_odsensf = new ExogenousFaultMode("OzoneDataSensorFault");
        ExogenousFaultMode exFM_odsynf = new ExogenousFaultMode("OzoneDataSynthesisFault");
        ExogenousFaultMode exFM_odrf = new ExogenousFaultMode("OzoneDataRefinementFault");
        ExogenousFaultMode exFM_pm10ds1f = new ExogenousFaultMode("PM10DataSensor1Fault");
        ExogenousFaultMode exFM_pm10ds2f = new ExogenousFaultMode("PM10DataSensor2Fault");
        ExogenousFaultMode exFM_pm10ds3f = new ExogenousFaultMode("PM10DataSensor3Fault");
        ExogenousFaultMode exFM_pm10dsf = new ExogenousFaultMode("PM10DataSynthesisFault");
        ExogenousFaultMode exFM_pm10drf = new ExogenousFaultMode("PM10DataRefinementFault");
        ExogenousFaultMode exFM_acdf = new ExogenousFaultMode("AnalysisCorruptedDataFault");

        faultModes.put(exFM_odsensf.getName(), exFM_odsensf);
        faultModes.put(exFM_odsynf.getName(), exFM_odsynf);
        faultModes.put(exFM_odrf.getName(), exFM_odrf);
        faultModes.put(exFM_pm10ds1f.getName(), exFM_pm10ds1f);
        faultModes.put(exFM_pm10ds2f.getName(), exFM_pm10ds2f);
        faultModes.put(exFM_pm10ds3f.getName(), exFM_pm10ds3f);
        faultModes.put(exFM_pm10dsf.getName(), exFM_pm10dsf);
        faultModes.put(exFM_pm10drf.getName(), exFM_pm10drf);
        faultModes.put(exFM_acdf.getName(), exFM_acdf);

        // Definizione delle Failure Mode per IngestionSubSystem

        FailureMode fM_pm10ds1f = new FailureMode("PM10DataSensor1Failure");
        ErrorMode eM_IS1 = new ErrorMode("ingestionSubsystem1_prop");
        eM_IS1.addInputFaultMode(enFM_ppm10ti1, enFM_dpm10s1);
        eM_IS1.addOutputFailureMode(fM_pm10ds1f);
        eM_IS1.setEnablingCondition("PublisherPM10TopicIssue1 || DefectivePM10Sensor1Issue", faultModes);
        eM_IS1.setPDF("exp(48)");
        errorModes.put(eM_IS1.getName(), eM_IS1);
        failureModes.put(fM_pm10ds1f.getDescription(), fM_pm10ds1f);

        ingestionSubsystem1PM10.addErrorMode(eM_IS1);

        ingestionSubsystem1PM10.addPropagationPort(new PropagationPort(fM_pm10ds1f, exFM_pm10ds1f, mapReduceProcessorPM10));

        FailureMode fM_pm10ds2f = new FailureMode("PM10DataSensor2Failure");
        ErrorMode eM_IS2 = new ErrorMode("ingestionSubsystem2_prop");
        eM_IS2.addInputFaultMode(enFM_ppm10ti2, enFM_dpm10s2);
        eM_IS2.addOutputFailureMode(fM_pm10ds2f);
        eM_IS2.setEnablingCondition("PublisherPM10TopicIssue2 || DefectivePM10Sensor2Issue", faultModes);
        eM_IS2.setPDF("exp(48)");
        errorModes.put(eM_IS2.getName(), eM_IS2);
        failureModes.put(fM_pm10ds2f.getDescription(), fM_pm10ds2f);

        ingestionSubsystem2PM10.addErrorMode(eM_IS2);

        ingestionSubsystem2PM10.addPropagationPort(new PropagationPort(fM_pm10ds2f, exFM_pm10ds2f, mapReduceProcessorPM10));


        FailureMode fM_pm10ds3f = new FailureMode("PM10DataSensor3Failure");
        ErrorMode eM_IS3 = new ErrorMode("ingestionSubsystem3_prop");
        eM_IS3.addInputFaultMode(enFM_ppm10ti3, enFM_dpm10s3);
        eM_IS3.addOutputFailureMode(fM_pm10ds3f);
        eM_IS3.setEnablingCondition("PublisherPM10TopicIssue3 || DefectivePM10Sensor3Issue", faultModes);
        eM_IS3.setPDF("exp(48)");
        errorModes.put(eM_IS3.getName(), eM_IS3);
        failureModes.put(fM_pm10ds3f.getDescription(), fM_pm10ds3f);

        ingestionSubsystem3PM10.addErrorMode(eM_IS3);

        ingestionSubsystem3PM10.addPropagationPort(new PropagationPort(fM_pm10ds3f, exFM_pm10ds3f, mapReduceProcessorPM10));


        FailureMode fM_ozonedsensf = new FailureMode("OzoneDataSensorFailure");
        ErrorMode eM_ISO = new ErrorMode("ingestionSubsystem_prop");
        eM_ISO.addInputFaultMode(enFM_dosi, enFM_poti);
        eM_ISO.addOutputFailureMode(fM_ozonedsensf);
        eM_ISO.setEnablingCondition("DefectiveOzoneSensorIssue || PublisherOzoneTopicIssue", faultModes);
        eM_ISO.setPDF("exp(48)");
        errorModes.put(eM_ISO.getName(), eM_ISO);
        failureModes.put(fM_pm10ds3f.getDescription(), fM_pm10ds3f);

        ingestionSubsystemOzone.addErrorMode(eM_ISO);

        ingestionSubsystemOzone.addPropagationPort(new PropagationPort(fM_ozonedsensf, exFM_odsensf, mapReduceProcessorOzone));

        FailureMode fM_ozonedsynf = new FailureMode("OzoneDataSynthesisFailure");
        ErrorMode em_odsp = new ErrorMode("OzoneDataSynthesis_prop");
        em_odsp.addInputFaultMode(exFM_odsensf);
        em_odsp.addOutputFailureMode(fM_ozonedsynf);
        em_odsp.setEnablingCondition("1/1(OzoneDataSensorFault)", faultModes);
        em_odsp.setPDF("dirac(0)");
        errorModes.put(em_odsp.getName(), em_odsp);
        failureModes.put(fM_ozonedsynf.getDescription(), fM_ozonedsynf);

        FailureMode fM_ozonedrf = new FailureMode("OzoneDataRefinementFailure");
        ErrorMode em_mrop = new ErrorMode("mapreduceprocessorOzone_prop");
        em_mrop.addInputFaultMode(exFM_odsynf, enFM_soti, enFM_oswmrbi);
        em_mrop.addOutputFailureMode(fM_ozonedrf);
        em_mrop.setEnablingCondition("OzoneDataSynthesisFault || SubscriberOzoneTopicIssue || OzoneSWMRBugIssue", faultModes);
        em_mrop.setPDF("erlang(5,2)");
        errorModes.put(em_mrop.getName(), em_mrop);
        failureModes.put(fM_ozonedrf.getDescription(), fM_ozonedrf);

        mapReduceProcessorOzone.addErrorMode(em_odsp, em_mrop);
        mapReduceProcessorOzone.addPropagationPort(
                new PropagationPort(fM_ozonedsynf, exFM_odsynf, mapReduceProcessorOzone),
                new PropagationPort(fM_ozonedrf, exFM_odrf, smartAgent));

        FailureMode fM_pm10dsf = new FailureMode("PM10DataSynthesisFailure");
        ErrorMode em_pm10dsfp = new ErrorMode("PM10DataSynthesisFailure_prop_id");
        em_pm10dsfp.addInputFaultMode(exFM_pm10ds1f, exFM_pm10ds2f, exFM_pm10ds3f);
        em_pm10dsfp.addOutputFailureMode(fM_pm10dsf);
        em_pm10dsfp.setEnablingCondition("2/3(PM10DataSensor1Fault, PM10DataSensor2Fault, PM10DataSensor3Fault)", faultModes);
        em_pm10dsfp.setPDF("dirac(0)");
        errorModes.put(em_pm10dsfp.getName(), em_pm10dsfp);
        failureModes.put(fM_pm10dsf.getDescription(), fM_pm10dsf);

        FailureMode fM_pm10drf = new FailureMode("PM10DataRefinementFailure");
        ErrorMode em_mrppm10p = new ErrorMode("mapreduceprocessorPM10_prop");
        em_mrppm10p.addInputFaultMode(exFM_pm10dsf, enFM_spm10ti, enFM_pm10swmrbi);
        em_mrppm10p.addOutputFailureMode(fM_pm10drf);
        em_mrppm10p.setEnablingCondition("PM10DataSynthesisFault || SubscriberPM10TopicIssue || PM10SWMRBugIssue", faultModes);
        em_mrppm10p.setPDF("erlang(5,2)");
        errorModes.put(em_mrppm10p.getName(), em_mrppm10p);
        failureModes.put(fM_pm10drf.getDescription(), fM_pm10drf);

        mapReduceProcessorPM10.addErrorMode(em_pm10dsfp, em_mrppm10p);

        mapReduceProcessorPM10.addPropagationPort(
                new PropagationPort(fM_pm10dsf, exFM_pm10dsf, mapReduceProcessorPM10),
                new PropagationPort(fM_pm10drf, exFM_pm10drf, smartAgent)
        );

        FailureMode fM_acdf = new FailureMode("AnalysisCorruptedDataFailure");
        ErrorMode em_cdp = new ErrorMode("corruptedData_prop");
        em_cdp.addInputFaultMode(exFM_pm10drf, exFM_odrf);
        em_cdp.addOutputFailureMode(fM_acdf);
        em_cdp.setEnablingCondition("PM10DataRefinementFault || OzoneDataRefinementFault", faultModes);
        em_cdp.setPDF("dirac(0)");
        errorModes.put(em_cdp.getName(), em_cdp);
        failureModes.put(fM_acdf.getDescription(), fM_acdf);

        smartAgent.addPropagationPort(new PropagationPort(fM_acdf, exFM_acdf, smartAgent));

        FailureMode fM_wsf = new FailureMode("WrongSuggestionFailure");
        ErrorMode em_wsp = new ErrorMode("wrongSuggestion_prop");
        em_wsp.addInputFaultMode(exFM_acdf, enFM_swb);
        em_wsp.addOutputFailureMode(fM_wsf);
        em_wsp.setEnablingCondition("AnalysisCorruptedDataFault || SmartAgent_SWBug", faultModes);
        em_wsp.setPDF("dirac(0)");
        errorModes.put(em_wsp.getName(), em_wsp);
        failureModes.put(fM_wsf.getDescription(), fM_wsf);

        smartAgent.addErrorMode(em_cdp, em_wsp);
    }

    public static PollutionMonitorTargetDesignBuilder getInstance(){
        if (single_instance == null)
            single_instance = new PollutionMonitorTargetDesignBuilder();
        return single_instance;
    }

    public System getSystem() {
        return system;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

}
