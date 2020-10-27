package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Fault;
import it.unifi.stlab.fault2failure.operational.Scenario;
import org.apache.commons.math3.distribution.RealDistribution;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that builds an example system composed of four Meta Components (SteamBoiler, Valve, Sensor, Controller).
 * The system represented is well known in system reliability as the SteamBoiler Example.
 * The class also gives an example on how to create failureModes, BooleanExpression and PropagationPorts.
 * Stores everything into the following attributes:
 * -metaComponents collects all the MetaComponents in the system
 * -failModes collects all the FailureModes that could happen inside the system
 * -failConnections collects all the PropagationPorts that describe the connections between faults and failures
 * as well as their ErrorMode, MetaComponents
 */
public class SteamBoilerModelBuilder {
    private static SteamBoilerModelBuilder single_instance = null;
    private static HashMap<String, FaultMode> faultModes;
    private static System system;

    private SteamBoilerModelBuilder() {
        faultModes = new HashMap<>();

        MetaComponent steamBoilerMC = new MetaComponent("SteamBoiler");
        MetaComponent valve1MC = new MetaComponent("Valve1");
        MetaComponent valve2MC = new MetaComponent("Valve2");
        MetaComponent controllerMC = new MetaComponent("Controller");
        MetaComponent sensor1MC = new MetaComponent("Sensor1");
        MetaComponent sensor2MC = new MetaComponent("Sensor2");
        MetaComponent sensor3MC = new MetaComponent("Sensor3");

        CompositionPort steamBoiler = new CompositionPort(steamBoilerMC);
        steamBoiler.addChild(controllerMC);
        steamBoiler.addChild(valve1MC);
        steamBoiler.addChild(valve2MC);
        CompositionPort subsystem = new CompositionPort(controllerMC);
        subsystem.addChild(sensor1MC);
        subsystem.addChild(sensor2MC);
        subsystem.addChild(sensor3MC);
        system = new System("SteamBoiler");
        system.addComponent(steamBoilerMC, valve1MC, valve2MC, controllerMC, sensor1MC, sensor2MC, sensor3MC);


        EndogenousFaultMode enED_S1 = new EndogenousFaultMode("Sensor1_ED");
        EndogenousFaultMode enMD_S1 = new EndogenousFaultMode("Sensor1_MD");
        EndogenousFaultMode enED_S2 = new EndogenousFaultMode("Sensor2_ED");
        EndogenousFaultMode enMD_S2 = new EndogenousFaultMode("Sensor2_MD");
        EndogenousFaultMode enED_S3 = new EndogenousFaultMode("Sensor3_ED");
        EndogenousFaultMode enMD_S3 = new EndogenousFaultMode("Sensor3_MD");

        faultModes.put(enED_S1.getName(), enED_S1);
        faultModes.put(enMD_S1.getName(), enMD_S1);
        faultModes.put(enED_S2.getName(), enED_S2);
        faultModes.put(enMD_S2.getName(), enMD_S2);
        faultModes.put(enED_S3.getName(), enED_S3);
        faultModes.put(enMD_S3.getName(), enMD_S3);


        FailureMode sensor1_PressureValueFailure = new FailureMode("Sensor1_PressureValueFailure");
        ErrorMode eM_S1 = new ErrorMode("Sensor1_Propagation");
        eM_S1.addInputFaultMode(enED_S1,enMD_S1);
        eM_S1.addOutputFailureMode(sensor1_PressureValueFailure);
        eM_S1.setEnablingCondition("Sensor1_ED || Sensor1_MD", faultModes);
        eM_S1.setPDF("erlang(5,1)");
        sensor1MC.addErrorMode(eM_S1);

        FailureMode sensor2_PressureValueFailure = new FailureMode("Sensor2_PressureValueFailure");
        ErrorMode eM_S2 = new ErrorMode("Sensor2_Propagation");
        eM_S2.addInputFaultMode(enED_S2,enMD_S2);
        eM_S2.addOutputFailureMode(sensor2_PressureValueFailure);
        eM_S2.setEnablingCondition("Sensor2_ED || Sensor2_MD", faultModes);
        eM_S2.setPDF("erlang(5,1)");
        sensor2MC.addErrorMode(eM_S2);

        FailureMode sensor3_PressureValueFailure = new FailureMode("Sensor3_PressureValueFailure");
        ErrorMode eM_S3 = new ErrorMode("Sensor3_Propagation");
        eM_S3.addInputFaultMode(enED_S3,enMD_S3);
        eM_S3.addOutputFailureMode(sensor3_PressureValueFailure);
        eM_S3.setEnablingCondition("Sensor3_ED || Sensor3_MD", faultModes);
        eM_S3.setPDF("erlang(5,1)");
        sensor3MC.addErrorMode(eM_S3);

        EndogenousFaultMode enHF_C = new EndogenousFaultMode("Controller_HF");
        ExogenousFaultMode exPVF1_C = new ExogenousFaultMode("Controller_PressureValueFault1");
        ExogenousFaultMode exPVF2_C = new ExogenousFaultMode("Controller_PressureValueFault2");
        ExogenousFaultMode exPVF3_C = new ExogenousFaultMode("Controller_PressureValueFault3");
        faultModes.put(enHF_C.getName(), enHF_C);
        faultModes.put(exPVF1_C.getName(), exPVF1_C);
        faultModes.put(exPVF2_C.getName(), exPVF2_C);
        faultModes.put(exPVF3_C.getName(), exPVF3_C);

        FailureMode controller_CommandOmissionFailure = new FailureMode("Controller_CommandOmissionFailure");
        ErrorMode eM_C = new ErrorMode("Controller_Propagation");
        eM_C.addInputFaultMode(enHF_C,exPVF1_C, exPVF2_C, exPVF3_C);
        eM_C.addOutputFailureMode(controller_CommandOmissionFailure);
        eM_C.setEnablingCondition("2/3(Controller_PressureValueFault1, Controller_PressureValueFault2, Controller_PressureValueFault3)||(Controller_HF)", faultModes);
        eM_C.setPDF("erlang(7,1)");
        controllerMC.addErrorMode(eM_C);

        EndogenousFaultMode enED_V1 = new EndogenousFaultMode("Valve1_ED");
        EndogenousFaultMode enMD_V1 = new EndogenousFaultMode("Valve1_MD");
        EndogenousFaultMode enED_V2 = new EndogenousFaultMode("Valve2_ED");
        EndogenousFaultMode enMD_V2 = new EndogenousFaultMode("Valve2_MD");
        faultModes.put(enED_V1.getName(), enED_V1);
        faultModes.put(enMD_V1.getName(), enMD_V1);
        faultModes.put(enED_V2.getName(), enED_V2);
        faultModes.put(enMD_V2.getName(), enMD_V2);

        ExogenousFaultMode exCOF_V = new ExogenousFaultMode("Valve_CommandOmissionFault");
        faultModes.put(exCOF_V.getName(), exCOF_V);

        FailureMode valve1_OpenOmissionFailure = new FailureMode("Valve1_OpenOmissionFailure");
        ErrorMode eM_V1 = new ErrorMode("Valve1_Propagation");
        eM_V1.addInputFaultMode(enED_V1, enMD_V1, exCOF_V);
        eM_V1.addOutputFailureMode(valve1_OpenOmissionFailure);
        eM_V1.setEnablingCondition("Valve1_ED || Valve1_MD || Valve_CommandOmissionFault", faultModes);
        eM_V1.setPDF("erlang(9,1)");
        valve1MC.addErrorMode(eM_V1);


        FailureMode valve2_OpenOmissionFailure = new FailureMode("Valve2_OpenOmissionFailure");
        ErrorMode eM_V2 = new ErrorMode("Valve2_Propagation");
        eM_V2.addInputFaultMode(enED_V2, enMD_V2, exCOF_V);
        eM_V2.addOutputFailureMode(valve2_OpenOmissionFailure);
        eM_V2.setEnablingCondition("Valve2_ED || Valve2_MD || Valve_CommandOmissionFault", faultModes);
        eM_V2.setPDF("erlang(9,1)");
        valve2MC.addErrorMode(eM_V2);

        ExogenousFaultMode exOOF1_SB = new ExogenousFaultMode("SteamBoiler_OpenOmissionFault1");
        ExogenousFaultMode exOOF2_SB = new ExogenousFaultMode("SteamBoiler_OpenOmissionFault2");
        faultModes.put(exOOF1_SB.getName(), exOOF1_SB);
        faultModes.put(exOOF2_SB.getName(), exOOF2_SB);

        FailureMode steamBoiler_Failure = new FailureMode("SteamBoiler_Failure");
        ErrorMode eM_SB = new ErrorMode("steamBoiler_Propagation");
        eM_SB.addInputFaultMode(exOOF1_SB, exOOF2_SB);
        eM_SB.addOutputFailureMode(steamBoiler_Failure);
        eM_SB.setEnablingCondition("SteamBoiler_OpenOmissionFault1 && SteamBoiler_OpenOmissionFault2", faultModes);
        eM_SB.setPDF("erlang(7,1)");
        steamBoilerMC.addErrorMode(eM_SB);

        sensor1MC.addPropagationPort(new PropagationPort(sensor1_PressureValueFailure, exPVF1_C, controllerMC));
        sensor2MC.addPropagationPort(new PropagationPort(sensor2_PressureValueFailure, exPVF2_C, controllerMC));
        sensor3MC.addPropagationPort(new PropagationPort(sensor3_PressureValueFailure, exPVF3_C, controllerMC));

        controllerMC.addPropagationPort(
                new PropagationPort(controller_CommandOmissionFailure, exCOF_V, valve1MC),
                new PropagationPort(controller_CommandOmissionFailure, exCOF_V, valve2MC));

        valve1MC.addPropagationPort(new PropagationPort(valve1_OpenOmissionFailure, exOOF1_SB, steamBoilerMC));
        valve2MC.addPropagationPort(new PropagationPort(valve2_OpenOmissionFailure, exOOF2_SB, steamBoilerMC));
    }

    public static SteamBoilerModelBuilder getInstance() {
        if (single_instance == null)
            single_instance = new SteamBoilerModelBuilder();
        return single_instance;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

    public System getSystem() {
        return system;
    }

    public static void createBaseDigitalTwin(Scenario scenario, System system, String serial){
        scenario.setSystem(system.getComponents().stream()
                .map(c -> new Component(c.getName() + serial, c))
                .collect(Collectors.toList()));
    }

    public static void injectFaultsIntoScenario(Scenario scenario, String serial){
        Fault sensor1_ED = new Fault("sensor1_ED", faultModes.get("Sensor1_ED"));
        Fault sensor2_MD = new Fault("sensor2_MD", faultModes.get("Sensor2_MD"));
        Fault valve1_MD = new Fault("valve1_MD", faultModes.get("Valve1_MD"));

        Map<String, Component> currentSystem = scenario.getCurrentSystemMap();
        scenario.addFault(sensor1_ED, BigDecimal.valueOf(SampleGenerator.generate("dirac(10)")), currentSystem.get("Sensor1"+serial));
        scenario.addFault(sensor2_MD, BigDecimal.valueOf(SampleGenerator.generate("dirac(14)")), currentSystem.get("Sensor2"+serial));
        scenario.addFault(valve1_MD, BigDecimal.valueOf(SampleGenerator.generate("dirac(14)")), currentSystem.get("Valve1"+serial));
    }
}
