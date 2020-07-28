package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Class that builds an example system composed of four Meta Components (SteamBoiler, Valve, Sensor, Controller).
 * The system represented is well known in system reliability as the SteamBoiler Example.
 * The class also gives an example on how to create failureModes, BooleanExpression and PropagationPorts.
 * Stores everything into the following attributes:
 * -metaComponents collects all the MetaComponents in the system
 * -failModes collects all the FailureModes that could happen inside the system
 * -failConnections collects all the PropagationPorts that describe the connections between faults and failures
 *                   as well as their ErrorMode, MetaComponents
 */
public class SteamBoilerModelBuilder{

    private static HashMap<String, MetaComponent> metaComponents;
    private static HashMap<String, FailureMode> failModes;
    private static HashMap<String, List<PropagationPort>> failConnections;

    public static HashMap<String, MetaComponent> getMetaComponents() {
        return metaComponents;
    }

    public static HashMap<String, FailureMode> getFailModes() {
        return failModes;
    }

    public static HashMap<String, List<PropagationPort>> getFailConnections() {
        return failConnections;
    }

    public static void build(){
        metaComponents = new HashMap<>();
        failModes = new HashMap<>();
        failConnections = new HashMap<>();
        /*
        metaComponents.put("SteamBoiler", new MetaComponent("SteamBoiler"));
        CompositionPort steamBoiler = new CompositionPort(metaComponents.get("SteamBoiler"));
        metaComponents.put("Valve", new MetaComponent("Valve"));
        metaComponents.put("Controller", new MetaComponent("Controller"));
        steamBoiler.addChild(metaComponents.get("Controller"));
        steamBoiler.addChild(metaComponents.get("Valve"));
        CompositionPort subsystem = new CompositionPort(metaComponents.get("Controller"));
        metaComponents.put("Sensor", new MetaComponent("Sensor"));
        subsystem.addChild(metaComponents.get("Sensor"));



        failModes.put("Sensor_ED", new FailureMode("Sensor_ED"));
        failModes.put("Sensor_MD", new FailureMode("Sensor_MD"));
        failModes.put("Pressure_Value_Failure", new FailureMode("Pressure_Value_Failure"));

        failModes.put("Controller_HF", new FailureMode("Controller_HF"));
        failModes.put("Pressure_Value_Fault", new FailureMode("Pressure_Value_Fault"));
        failModes.put("Command_Omission_Failure", new FailureMode("Command_Omission_Failure"));


        failModes.put("Valve_ED", new FailureMode("Valve_ED"));
        failModes.put("Valve_MD", new FailureMode("Valve_MD"));
        failModes.put("Command_Omission_Fault", new FailureMode("Command_Omission_Fault"));
        failModes.put("Open_Omission_Failure", new FailureMode("Open_Omission_Failure"));

        failModes.put("Open_Omission_Fault", new FailureMode("Open_Omission_Fault"));
        failModes.put("SteamBoiler_Failure", new FailureMode("SteamBoiler_Failure"));

        BooleanExpression sensor = BooleanExpression.config("Sensor_ED || Sensor_MD", failModes);
        BooleanExpression controller = BooleanExpression.config("2/3(Pressure_Value_Fault)||(Controller_HF)", failModes);
        BooleanExpression valve = BooleanExpression.config("Valve_ED || Valve_MD || Command_Omission_Fault", failModes);
        BooleanExpression steam_boiler = BooleanExpression.config("2/2(Open_Omission_Fault)", failModes);


        ErrorMode sensor_Propagation = new ErrorMode("sensor_Propagation", sensor, failModes.get("Pressure_Value_Failure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("5"), new BigDecimal("1")));
        ErrorMode controller_Propagation = new ErrorMode("controller_Propagation", controller, failModes.get("Command_Omission_Failure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("7"), new BigDecimal("1")));
        ErrorMode valve_Propagation = new ErrorMode("valve_Propagation", valve, failModes.get("Open_Omission_Failure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("9"), new BigDecimal("1")));
        ErrorMode steamBoiler_Propagation = new ErrorMode("steamBoiler_Propagation", steam_boiler, failModes.get("SteamBoiler_Failure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("7"), new BigDecimal("1")));

        failConnections.computeIfAbsent("Sensor_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor_ED"), failModes.get("Pressure_Value_Failure"), sensor_Propagation, metaComponents.get("Sensor")));
        failConnections.computeIfAbsent("Sensor_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor_MD"), failModes.get("Pressure_Value_Failure"), sensor_Propagation, metaComponents.get("Sensor")));
        failConnections.computeIfAbsent("Controller_HF", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_HF"), failModes.get("Command_Omission_Failure"), controller_Propagation, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Pressure_Value_Fault", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Pressure_Value_Fault"), failModes.get("Command_Omission_Failure"), controller_Propagation, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Valve_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve_ED"), failModes.get("Open_Omission_Failure"), valve_Propagation, metaComponents.get("Valve")));
        failConnections.computeIfAbsent("Valve_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve_MD"), failModes.get("Open_Omission_Failure"), valve_Propagation, metaComponents.get("Valve")));
        failConnections.computeIfAbsent("Command_Omission_Fault", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Command_Omission_Fault"), failModes.get("Open_Omission_Failure"), valve_Propagation, metaComponents.get("Valve")));
        failConnections.computeIfAbsent("Open_Omission_Fault", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Open_Omission_Fault"), failModes.get("SteamBoiler_Failure"), steamBoiler_Propagation, metaComponents.get("SteamBoiler")));

        failConnections.computeIfAbsent("Pressure_Value_Failure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Pressure_Value_Failure"), failModes.get("Pressure_Value_Fault"), null, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Command_Omission_Failure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Command_Omission_Failure"), failModes.get("Command_Omission_Fault"), null, metaComponents.get("Valve")));
        failConnections.computeIfAbsent("Open_Omission_Failure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Open_Omission_Failure"), failModes.get("Open_Omission_Fault"), null, metaComponents.get("SteamBoiler")));

         */
        metaComponents.put("SteamBoiler", new MetaComponent("SteamBoiler"));
        CompositionPort steamBoiler = new CompositionPort(metaComponents.get("SteamBoiler"));
        metaComponents.put("Valve1", new MetaComponent("Valve1"));
        metaComponents.put("Valve2", new MetaComponent("Valve2"));
        metaComponents.put("Controller", new MetaComponent("Controller"));
        steamBoiler.addChild(metaComponents.get("Controller"));
        steamBoiler.addChild(metaComponents.get("Valve1"));
        steamBoiler.addChild(metaComponents.get("Valve2"));
        CompositionPort subsystem = new CompositionPort(metaComponents.get("Controller"));
        metaComponents.put("Sensor1", new MetaComponent("Sensor1"));
        subsystem.addChild(metaComponents.get("Sensor1"));
        metaComponents.put("Sensor2", new MetaComponent("Sensor2"));
        subsystem.addChild(metaComponents.get("Sensor2"));
        metaComponents.put("Sensor3", new MetaComponent("Sensor3"));
        subsystem.addChild(metaComponents.get("Sensor3"));



        failModes.put("Sensor1_ED", new FailureMode("Sensor1_ED"));
        failModes.put("Sensor1_MD", new FailureMode("Sensor1_MD"));
        failModes.put("Sensor2_ED", new FailureMode("Sensor2_ED"));
        failModes.put("Sensor2_MD", new FailureMode("Sensor2_MD"));
        failModes.put("Sensor3_ED", new FailureMode("Sensor3_ED"));
        failModes.put("Sensor3_MD", new FailureMode("Sensor3_MD"));
        failModes.put("Sensor1_PressureValueFailure", new FailureMode("Sensor1_PressureValueFailure"));
        failModes.put("Sensor2_PressureValueFailure", new FailureMode("Sensor2_PressureValueFailure"));
        failModes.put("Sensor3_PressureValueFailure", new FailureMode("Sensor3_PressureValueFailure"));

        failModes.put("Controller_HF", new FailureMode("Controller_HF"));
        failModes.put("Controller_PressureValueFault1", new FailureMode("Controller_PressureValueFault1"));
        failModes.put("Controller_PressureValueFault2", new FailureMode("Controller_PressureValueFault2"));
        failModes.put("Controller_PressureValueFault3", new FailureMode("Controller_PressureValueFault3"));
        failModes.put("Controller_CommandOmissionFailure", new FailureMode("Controller_CommandOmissionFailure"));


        failModes.put("Valve1_ED", new FailureMode("Valve1_ED"));
        failModes.put("Valve1_MD", new FailureMode("Valve1_MD"));
        failModes.put("Valve2_ED", new FailureMode("Valve2_ED"));
        failModes.put("Valve2_MD", new FailureMode("Valve2_MD"));
        failModes.put("Valve_CommandOmissionFault", new FailureMode("Valve_CommandOmissionFault"));
        failModes.put("Valve1_OpenOmissionFailure", new FailureMode("Valve1_OpenOmissionFailure"));
        failModes.put("Valve2_OpenOmissionFailure", new FailureMode("Valve2_OpenOmissionFailure"));


        failModes.put("SteamBoiler_OpenOmissionFault1", new FailureMode("SteamBoiler_OpenOmissionFault1"));
        failModes.put("SteamBoiler_OpenOmissionFault2", new FailureMode("SteamBoiler_OpenOmissionFault2"));
        failModes.put("SteamBoiler_Failure", new FailureMode("SteamBoiler_Failure"));

        BooleanExpression sensor1 = BooleanExpression.config("Sensor1_ED || Sensor1_MD", failModes);
        BooleanExpression sensor2 = BooleanExpression.config("Sensor2_ED || Sensor2_MD", failModes);
        BooleanExpression sensor3 = BooleanExpression.config("Sensor3_ED || Sensor3_MD", failModes);
        BooleanExpression controller = BooleanExpression.config("2/3(Controller_PressureValueFault1, Controller_PressureValueFault2, Controller_PressureValueFault3)||(Controller_HF)", failModes);
        BooleanExpression valve1 = BooleanExpression.config("Valve1_ED || Valve1_MD || Valve_CommandOmissionFault", failModes);
        BooleanExpression valve2 = BooleanExpression.config("Valve2_ED || Valve2_MD || Valve_CommandOmissionFault", failModes);
        BooleanExpression steam_boiler = BooleanExpression.config("SteamBoiler_OpenOmissionFault1 && SteamBoiler_OpenOmissionFault2", failModes);


        ErrorMode sensor1_Propagation = new ErrorMode("Sensor1_Propagation", sensor1, failModes.get("Sensor1_PressureValueFailure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("5"), new BigDecimal("1")));
        ErrorMode sensor2_Propagation = new ErrorMode("Sensor2_Propagation", sensor2, failModes.get("Sensor2_PressureValueFailure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("5"), new BigDecimal("1")));
        ErrorMode sensor3_Propagation = new ErrorMode("Sensor3_Propagation", sensor3, failModes.get("Sensor3_PressureValueFailure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("5"), new BigDecimal("1")));


        ErrorMode controller_Propagation = new ErrorMode("Controller_Propagation", controller, failModes.get("Controller_CommandOmissionFailure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("7"), new BigDecimal("1")));
        ErrorMode valve1_Propagation = new ErrorMode("Valve1_Propagation", valve1, failModes.get("Valve1_OpenOmissionFailure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("9"), new BigDecimal("1")));
        ErrorMode valve2_Propagation = new ErrorMode("Valve2_Propagation", valve2, failModes.get("Valve2_OpenOmissionFailure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("9"), new BigDecimal("1")));
        ErrorMode steamBoiler_Propagation = new ErrorMode("steamBoiler_Propagation", steam_boiler, failModes.get("SteamBoiler_Failure"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("7"), new BigDecimal("1")));

        failConnections.computeIfAbsent("Sensor1_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor1_ED"), failModes.get("Sensor1_PressureValueFailure"), sensor1_Propagation, metaComponents.get("Sensor1")));
        failConnections.computeIfAbsent("Sensor1_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor1_MD"), failModes.get("Sensor1_PressureValueFailure"), sensor1_Propagation, metaComponents.get("Sensor1")));
        failConnections.computeIfAbsent("Sensor2_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor2_ED"), failModes.get("Sensor2_PressureValueFailure"), sensor2_Propagation, metaComponents.get("Sensor2")));
        failConnections.computeIfAbsent("Sensor2_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor2_MD"), failModes.get("Sensor2_PressureValueFailure"), sensor2_Propagation, metaComponents.get("Sensor2")));
        failConnections.computeIfAbsent("Sensor3_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor3_ED"), failModes.get("Sensor3_PressureValueFailure"), sensor3_Propagation, metaComponents.get("Sensor3")));
        failConnections.computeIfAbsent("Sensor3_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor3_MD"), failModes.get("Sensor3_PressureValueFailure"), sensor3_Propagation, metaComponents.get("Sensor3")));


        failConnections.computeIfAbsent("Controller_HF", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_HF"), failModes.get("Command_Omission_Failure"), controller_Propagation, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Controller_PressureValueFault1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_PressureValueFault1"), failModes.get("Controller_CommandOmissionFailure"), controller_Propagation, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Controller_PressureValueFault2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_PressureValueFault2"), failModes.get("Controller_CommandOmissionFailure"), controller_Propagation, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Controller_PressureValueFault3", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_PressureValueFault3"), failModes.get("Controller_CommandOmissionFailure"), controller_Propagation, metaComponents.get("Controller")));

        failConnections.computeIfAbsent("Valve1_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve1_ED"), failModes.get("Valve1_OpenOmissionFailure"), valve1_Propagation, metaComponents.get("Valve1")));
        failConnections.computeIfAbsent("Valve1_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve1_MD"), failModes.get("Valve1_OpenOmissionFailure"), valve1_Propagation, metaComponents.get("Valve1")));
        failConnections.computeIfAbsent("Valve2_ED", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve2_ED"), failModes.get("Valve2_OpenOmissionFailure"), valve2_Propagation, metaComponents.get("Valve2")));
        failConnections.computeIfAbsent("Valve2_MD", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve2_MD"), failModes.get("Valve2_OpenOmissionFailure"), valve2_Propagation, metaComponents.get("Valve2")));

        failConnections.computeIfAbsent("Valve_CommandOmissionFault", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve_CommandOmissionFault"), failModes.get("Valve1_OpenOmissionFailure"), valve1_Propagation, metaComponents.get("Valve1")));
        failConnections.computeIfAbsent("Valve_CommandOmissionFault", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve_CommandOmissionFault"), failModes.get("Valve2_OpenOmissionFailure"), valve2_Propagation, metaComponents.get("Valve2")));
        failConnections.computeIfAbsent("SteamBoiler_OpenOmissionFault1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("SteamBoiler_OpenOmissionFault1"), failModes.get("SteamBoiler_Failure"), steamBoiler_Propagation, metaComponents.get("SteamBoiler")));
        failConnections.computeIfAbsent("SteamBoiler_OpenOmissionFault2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("SteamBoiler_OpenOmissionFault2"), failModes.get("SteamBoiler_Failure"), steamBoiler_Propagation, metaComponents.get("SteamBoiler")));


        failConnections.computeIfAbsent("Sensor1_PressureValueFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor1_PressureValueFailure"), failModes.get("Controller_PressureValueFault1"), null, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Sensor2_PressureValueFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor2_PressureValueFailure"), failModes.get("Controller_PressureValueFault2"), null, metaComponents.get("Controller")));
        failConnections.computeIfAbsent("Sensor3_PressureValueFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Sensor3_PressureValueFailure"), failModes.get("Controller_PressureValueFault3"), null, metaComponents.get("Controller")));

        failConnections.computeIfAbsent("Controller_CommandOmissionFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_CommandOmissionFailure"), failModes.get("Valve_CommandOmissionFault"), null, metaComponents.get("Valve1")));
        failConnections.computeIfAbsent("Controller_CommandOmissionFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Controller_CommandOmissionFailure"), failModes.get("Valve_CommandOmissionFault"), null, metaComponents.get("Valve2")));
        failConnections.computeIfAbsent("Valve1_OpenOmissionFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve1_OpenOmissionFailure"), failModes.get("SteamBoiler_OpenOmissionFault1"), null, metaComponents.get("SteamBoiler")));
        failConnections.computeIfAbsent("Valve2_OpenOmissionFailure", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("Valve2_OpenOmissionFailure"), failModes.get("SteamBoiler_OpenOmissionFault2"), null, metaComponents.get("SteamBoiler")));


    }
}
