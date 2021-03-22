package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.utils.BooleanExpressionConverter;
import it.unifi.stlab.faultflow.model.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "errormodes")
public class ErrorMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private final String name;

    @OneToMany
    @JoinTable(
            name="errormode_faultmodes",
            joinColumns = @JoinColumn( name="errormode_uuid"),
            inverseJoinColumns = @JoinColumn( name="faultmode_fk")
    )
    private final List<FaultMode> inputFaultModes;
    @Transient
    @Lob
    @Convert(converter = BooleanExpressionConverter.class)
    private BooleanExpression activationFunction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outgoing_failure_fk")
    private FailureMode outgoingFailure;

    @Column(name = "time_to_failure_pdf")
    private String timetofailurePDF;

    public ErrorMode() {
        this.name = "";
        this.inputFaultModes = new ArrayList<>();
        this.activationFunction=null;
        this.outgoingFailure=null;
        this.timetofailurePDF="";
    }

    /**
     * Create and ErrorMode by saying its name and its EnablingFunction (or ActivationFunction).
     * The CDF can be set later by calling its setter.
     *
     * @param name     ErrorMode's Name. It'll be used in the PetriNet translation of the model, to give a name to its corresponding place.
     *                 Must be unique.
     * @param function the ActivationFunction (or enablingFunction) of the ErrorMode, expressed as an instance of BooleanExpression.
     */
    public ErrorMode(String name, BooleanExpression function) {
        this.name = name;
        this.activationFunction = function;
        this.inputFaultModes = activationFunction.extractIncomingFaults();
        this.outgoingFailure = null;
    }

    public ErrorMode(String name) {
        this.name = name;
        this.activationFunction = null;
        this.inputFaultModes = new ArrayList<>();
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure) {
        this(name, function);
        this.outgoingFailure = outgoingFailure;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, RealDistribution timetofailurePDF) {
        this(name, function, outgoingFailure);
        this.timetofailurePDF = PDFParser.parseRealDistributionToString(timetofailurePDF);;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, String timetofailurePDF) {
        this(name, function, outgoingFailure);
        setPDF(timetofailurePDF);
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, RealDistribution timetofailurePDF, Component component) {
        this(name, function, outgoingFailure, timetofailurePDF);
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, String timetofailurePDF, Component component) {
        this(name, function, outgoingFailure, timetofailurePDF);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return this.name;
    }

    public BooleanExpression getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(BooleanExpression activationFunction) {
        this.activationFunction = activationFunction;
    }

    public List<FaultMode> getInputFaultModes() {
        return inputFaultModes;
    }

    public FailureMode getOutgoingFailure() {
        return this.outgoingFailure;
    }

    public String getTimetofailurePDFToString() {
        return timetofailurePDF;
    }

    public RealDistribution getTimetofailurePDF() {
        return PDFParser.parseStringToRealDistribution(timetofailurePDF);
    }

    public void setPDF(String timetofailurePDF) {
        this.timetofailurePDF = timetofailurePDF;
    }

    public void setOutGoingFailure(FailureMode fm) {
        this.outgoingFailure = fm;
    }

    public boolean checkActivationFunction() {
        return activationFunction.compute();
    }

    public boolean checkFaultIsPresent(String name) {
        return this.inputFaultModes.stream().anyMatch(x -> x.name.equals(name));
    }

    public void addInputFaultMode(FaultMode... faultMode) {
        this.inputFaultModes.addAll(Arrays.asList(faultMode));
    }

    public void addOutputFailureMode(FailureMode failureMode) {
        this.outgoingFailure = failureMode;
    }

    public void setEnablingCondition(String booleanExpression, HashMap<String, FaultMode> faultModes) {
        this.activationFunction = BooleanExpression.config(booleanExpression, faultModes);
    }
}
