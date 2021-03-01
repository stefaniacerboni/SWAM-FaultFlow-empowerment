package it.unifi.stlab.utils.builder;

import it.unifi.stlab.model.knowledge.composition.Component;
import it.unifi.stlab.model.knowledge.composition.CompositionPort;
import it.unifi.stlab.model.knowledge.composition.System;
import it.unifi.stlab.model.knowledge.propagation.*;

import java.util.HashMap;

public class SteamBoilerModelBuilder {

    private static SteamBoilerModelBuilder single_instance;
    private static System system;
    private static HashMap<String, FaultMode> faultModes;
    private static HashMap<String, FailureMode> failureModes;
    private static HashMap<String, ErrorMode> errorModes;

    private SteamBoilerModelBuilder() {
        faultModes = new HashMap<>();
        failureModes = new HashMap<>();
        errorModes = new HashMap<>();

        // Definizione composizione del sistema

        system = new System("SteamBoiler_SYS");
        Component steamBoiler = new Component("SteamBoiler");
        Component valve1 = new Component("Valve1");
        Component valve2 = new Component("Valve2");
        Component controller = new Component("Controller");
        Component sensor1 = new Component("Sensor1");
        Component sensor2 = new Component("Sensor2");
        Component sensor3 = new Component("Sensor3");
        system.addComponent(steamBoiler,
                valve1, valve2,
                controller,
                sensor1, sensor2, sensor3);
        system.setTopLevelComponent(steamBoiler);
        CompositionPort cs1 = new CompositionPort(sensor1, controller);
        CompositionPort cs2 = new CompositionPort(sensor2, controller);
        CompositionPort cs3 = new CompositionPort(sensor3, controller);
        controller.addCompositionPorts(cs1, cs2, cs3);

        CompositionPort sbv1 = new CompositionPort(valve1, steamBoiler);
        CompositionPort sbv2 = new CompositionPort(valve2, steamBoiler);
        CompositionPort sbc = new CompositionPort(controller, steamBoiler);
        steamBoiler.addCompositionPorts(sbv1, sbv2, sbc);

        // Definizione di Fault Mode Endogeni

        EndogenousFaultMode enFM_S1MD = new EndogenousFaultMode("Sensor1_MD");
        enFM_S1MD.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_S1ED = new EndogenousFaultMode("Sensor1_ED");
        enFM_S1ED.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_S2MD = new EndogenousFaultMode("Sensor2_MD");
        enFM_S2MD.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_S2ED = new EndogenousFaultMode("Sensor2_ED");
        enFM_S2ED.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_S3MD = new EndogenousFaultMode("Sensor3_MD");
        enFM_S3MD.setArisingPDF("exp(1/180)");
        EndogenousFaultMode enFM_S3ED = new EndogenousFaultMode("Sensor3_ED");
        enFM_S3ED.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_CHF = new EndogenousFaultMode("Controller_HF");
        enFM_CHF.setArisingPDF("exp(10)");
        EndogenousFaultMode enFM_V1ED = new EndogenousFaultMode("Valve1_ED");
        enFM_V1ED.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_V1HF = new EndogenousFaultMode("Valve1_HF");
        enFM_V1HF.setArisingPDF("exp(1/2500)");
        EndogenousFaultMode enFM_V2ED = new EndogenousFaultMode("Valve2_ED");
        enFM_V2ED.setArisingPDF("exp(1/365)");
        EndogenousFaultMode enFM_V2HF = new EndogenousFaultMode("Valve2_HF");
        enFM_V2HF.setArisingPDF("exp(1/2500)");

        faultModes.put(enFM_S1MD.getName(), enFM_S1MD);
        faultModes.put(enFM_S1ED.getName(), enFM_S1ED);
        faultModes.put(enFM_S2MD.getName(), enFM_S2MD);
        faultModes.put(enFM_S2ED.getName(), enFM_S2ED);
        faultModes.put(enFM_S3MD.getName(), enFM_S3MD);
        faultModes.put(enFM_S3ED.getName(), enFM_S3ED);
        faultModes.put(enFM_CHF.getName(), enFM_CHF);
        faultModes.put(enFM_V1ED.getName(), enFM_V1ED);
        faultModes.put(enFM_V1HF.getName(), enFM_V1HF);
        faultModes.put(enFM_V2ED.getName(), enFM_V2ED);
        faultModes.put(enFM_V2HF.getName(), enFM_V2HF);

        // Definizione di Fault Mode Esogeni

        ExogenousFaultMode exFM_CPVF1 = new ExogenousFaultMode("Controller_PressureValueFault1");
        ExogenousFaultMode exFM_CPVF2 = new ExogenousFaultMode("Controller_PressureValueFault2");
        ExogenousFaultMode exFM_CPVF3 = new ExogenousFaultMode("Controller_PressureValueFault3");
        ExogenousFaultMode exFM_VCOF = new ExogenousFaultMode("Valve_CommandOmissionFault");
        ExogenousFaultMode exFM_SBOO1 = new ExogenousFaultMode("SteamBoiler_OpenOmissionFault1");
        ExogenousFaultMode exFM_SBOO2 = new ExogenousFaultMode("SteamBoiler_OpenOmissionFault2");

        faultModes.put(exFM_CPVF1.getName(), exFM_CPVF1);
        faultModes.put(exFM_CPVF2.getName(), exFM_CPVF2);
        faultModes.put(exFM_CPVF3.getName(), exFM_CPVF3);
        faultModes.put(exFM_VCOF.getName(), exFM_VCOF);
        faultModes.put(exFM_SBOO1.getName(), exFM_SBOO1);
        faultModes.put(exFM_SBOO2.getName(), exFM_SBOO2);

        // Definizione delle Failure Mode per Sensor1, Sensor2, Sensor3

        FailureMode fM_S1PVF = new FailureMode("Sensor1_PressureValueFailure");
        ErrorMode eM_S1 = new ErrorMode("Sensor1_ToFailure");
        eM_S1.addInputFaultMode(enFM_S1ED, enFM_S1MD);
        eM_S1.addOutputFailureMode(fM_S1PVF);
        eM_S1.setEnablingCondition("Sensor1_MD || Sensor1_ED", faultModes);
        eM_S1.setPDF("erlang(5,1)");
        errorModes.put(eM_S1.getName(), eM_S1);
        failureModes.put(fM_S1PVF.getDescription(), fM_S1PVF);

        sensor1.addErrorMode(eM_S1);

        FailureMode fM_S2PVF = new FailureMode("Sensor2_PressureValueFailure");
        ErrorMode eM_S2 = new ErrorMode("Sensor2_ToFailure");
        eM_S2.addInputFaultMode(enFM_S2ED, enFM_S2MD);
        eM_S2.addOutputFailureMode(fM_S2PVF);
        eM_S2.setEnablingCondition("Sensor2_MD || Sensor2_ED", faultModes);
        eM_S2.setPDF("erlang(5,1)");
        errorModes.put(eM_S2.getName(), eM_S2);
        failureModes.put(fM_S2PVF.getDescription(), fM_S2PVF);

        sensor2.addErrorMode(eM_S2);

        FailureMode fM_S3PVF = new FailureMode("Sensor3_PressureValueFailure");
        ErrorMode eM_S3 = new ErrorMode("Sensor3_ToFailure");
        eM_S3.addInputFaultMode(enFM_S3ED, enFM_S3MD);
        eM_S3.addOutputFailureMode(fM_S3PVF);
        eM_S3.setEnablingCondition("Sensor3_MD || Sensor3_ED", faultModes);
        eM_S3.setPDF("erlang(5,1)");
        errorModes.put(eM_S3.getName(), eM_S3);
        failureModes.put(fM_S3PVF.getDescription(), fM_S3PVF);

        sensor3.addErrorMode(eM_S3);

        // Definizione delle propagation port

        sensor1.addPropagationPort(
                new PropagationPort(fM_S1PVF, exFM_CPVF1, controller));
        sensor2.addPropagationPort(
                new PropagationPort(fM_S2PVF, exFM_CPVF2, controller));
        sensor3.addPropagationPort(
                new PropagationPort(fM_S3PVF, exFM_CPVF3, controller));

        // Definizione delle Failure Mode per Controller

        FailureMode fM_C = new FailureMode("Controller_CommandOmissionFailure");
        ErrorMode eM_C = new ErrorMode("Controller_ToFailure");
        eM_C.addInputFaultMode(enFM_CHF, exFM_CPVF1, exFM_CPVF2, exFM_CPVF3);
        eM_C.addOutputFailureMode(fM_C);
        eM_C.setEnablingCondition(
                "2/3(Controller_PressureValueFault1, " +
                        "Controller_PressureValueFault2, " +
                        "Controller_PressureValueFault3) || Controller_HF", faultModes);
        eM_C.setPDF("erlang(7,1)");
        errorModes.put(eM_C.getName(), eM_C);
        failureModes.put(fM_C.getDescription(), fM_C);

        controller.addErrorMode(eM_C);

        controller.addPropagationPort(
                new PropagationPort(fM_C, exFM_VCOF, valve1),
                new PropagationPort(fM_C, exFM_VCOF, valve2)
        );

        // Definizione delle Failure Mode per Valve1, Valve2

        FailureMode fM_V1 = new FailureMode("Valve1_OpenOmissionFailure");
        ErrorMode eM_V1 = new ErrorMode("Valve1_ToFailure");
        eM_V1.addInputFaultMode(exFM_VCOF, enFM_V1ED, enFM_V1HF);
        eM_V1.addOutputFailureMode(fM_V1);
        eM_V1.setEnablingCondition("Valve1_HF || Valve1_ED || Valve_CommandOmissionFault", faultModes);
        eM_V1.setPDF("erlang(2,1)");
        errorModes.put(eM_V1.getName(), eM_V1);
        failureModes.put(fM_V1.getDescription(), fM_V1);

        valve1.addErrorMode(eM_V1);

        FailureMode fM_V2 = new FailureMode("Valve2_OpenOmissionFailure");
        ErrorMode eM_V2 = new ErrorMode("Valve2_ToFailure");
        eM_V2.addInputFaultMode(exFM_VCOF, enFM_V2ED, enFM_V2HF);
        eM_V2.addOutputFailureMode(fM_V2);
        eM_V2.setEnablingCondition("Valve2_HF || Valve2_ED || Valve_CommandOmissionFault", faultModes);
        eM_V2.setPDF("erlang(2,1)");
        errorModes.put(eM_V2.getName(), eM_V2);
        failureModes.put(fM_V2.getDescription(), fM_V2);

        valve2.addErrorMode(eM_V2);

        valve1.addPropagationPort(
                new PropagationPort(fM_V1, exFM_SBOO1, steamBoiler)
        );
        valve2.addPropagationPort(
                new PropagationPort(fM_V2, exFM_SBOO2, steamBoiler)
        );

        // Definizione delle Failure Mode per SteamBoiler

        FailureMode fM_SB = new FailureMode("SystemFailure");
        ErrorMode eM_SB = new ErrorMode("SteamBoiler_ToFailure");
        eM_SB.addInputFaultMode(exFM_SBOO1, exFM_SBOO2);
        eM_SB.addOutputFailureMode(fM_SB);
        eM_SB.setEnablingCondition("SteamBoiler_OpenOmissionFault1 && SteamBoiler_OpenOmissionFault2", faultModes);
        eM_SB.setPDF("dirac(0)");
        errorModes.put(eM_SB.getName(), eM_SB);
        failureModes.put(fM_SB.getDescription(), fM_SB);

        steamBoiler.addErrorMode(eM_SB);
    }

    public static SteamBoilerModelBuilder getInstance() {
        if (single_instance == null)
            single_instance = new SteamBoilerModelBuilder();
        return single_instance;
    }

    public System getSystem() {
        return system;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

}
