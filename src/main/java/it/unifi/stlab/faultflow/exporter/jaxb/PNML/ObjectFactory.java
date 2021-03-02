
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the JavaToXpn package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Literal_QNAME = new QName("pnml.apromore.org", "literal");
    private final static QName _Empty_QNAME = new QName("pnml.apromore.org", "empty");
    private final static QName _Targets_QNAME = new QName("pnml.apromore.org", "targets");
    private final static QName _Source_QNAME = new QName("pnml.apromore.org", "source");
    private final static QName _TransitionCondition_QNAME = new QName("pnml.apromore.org", "transitionCondition");
    private final static QName _Invoke_QNAME = new QName("pnml.apromore.org", "invoke");
    private final static QName _PartnerLinks_QNAME = new QName("pnml.apromore.org", "partnerLinks");
    private final static QName _ExtensionAssignOperation_QNAME = new QName("pnml.apromore.org", "extensionAssignOperation");
    private final static QName _Sources_QNAME = new QName("pnml.apromore.org", "sources");
    private final static QName _Wait_QNAME = new QName("pnml.apromore.org", "wait");
    private final static QName _CatchAll_QNAME = new QName("pnml.apromore.org", "catchAll");
    private final static QName _Reply_QNAME = new QName("pnml.apromore.org", "reply");
    private final static QName _Copy_QNAME = new QName("pnml.apromore.org", "copy");
    private final static QName _Catch_QNAME = new QName("pnml.apromore.org", "catch");
    private final static QName _From_QNAME = new QName("pnml.apromore.org", "from");
    private final static QName _CompensationHandler_QNAME = new QName("pnml.apromore.org", "compensationHandler");
    private final static QName _JoinCondition_QNAME = new QName("pnml.apromore.org", "joinCondition");
    private final static QName _Target_QNAME = new QName("pnml.apromore.org", "target");
    private final static QName _Pnml_QNAME = new QName("pnml.apromore.org", "pnml");
    private final static QName _Documentation_QNAME = new QName("pnml.apromore.org", "documentation");
    private final static QName _Query_QNAME = new QName("pnml.apromore.org", "query");
    private final static QName _FromParts_QNAME = new QName("pnml.apromore.org", "fromParts");
    private final static QName _Variables_QNAME = new QName("pnml.apromore.org", "variables");
    private final static QName _Receive_QNAME = new QName("pnml.apromore.org", "receive");
    private final static QName _Assign_QNAME = new QName("pnml.apromore.org", "assign");
    private final static QName _To_QNAME = new QName("pnml.apromore.org", "to");
    private final static QName _ToPart_QNAME = new QName("pnml.apromore.org", "toPart");
    private final static QName _Variable_QNAME = new QName("pnml.apromore.org", "variable");
    private final static QName _PartnerLink_QNAME = new QName("pnml.apromore.org", "partnerLink");
    private final static QName _ToParts_QNAME = new QName("pnml.apromore.org", "toParts");
    private final static QName _FromPart_QNAME = new QName("pnml.apromore.org", "fromPart");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: JavaToXpn
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransitionToolspecificType }
     * 
     */
    public TransitionToolspecificType createTransitionToolspecificType() {
        return new TransitionToolspecificType();
    }

    /**
     * Create an instance of {@link NetType }
     * 
     */
    public NetType createNetType() {
        return new NetType();
    }

    /**
     * Create an instance of {@link PlaceType }
     * 
     */
    public PlaceType createPlaceType() {
        return new PlaceType();
    }

    /**
     * Create an instance of {@link TActivityContainer }
     * 
     */
    public TActivityContainer createTActivityContainer() {
        return new TActivityContainer();
    }

    /**
     * Create an instance of {@link TWait }
     * 
     */
    public TWait createTWait() {
        return new TWait();
    }

    /**
     * Create an instance of {@link TSources }
     * 
     */
    public TSources createTSources() {
        return new TSources();
    }

    /**
     * Create an instance of {@link TExtensionAssignOperation }
     * 
     */
    public TExtensionAssignOperation createTExtensionAssignOperation() {
        return new TExtensionAssignOperation();
    }

    /**
     * Create an instance of {@link Line }
     * 
     */
    public Line createLine() {
        return new Line();
    }

    /**
     * Create an instance of {@link TPartnerLinks }
     * 
     */
    public TPartnerLinks createTPartnerLinks() {
        return new TPartnerLinks();
    }

    /**
     * Create an instance of {@link TInvoke }
     * 
     */
    public TInvoke createTInvoke() {
        return new TInvoke();
    }

    /**
     * Create an instance of {@link TSource }
     * 
     */
    public TSource createTSource() {
        return new TSource();
    }

    /**
     * Create an instance of {@link TCondition }
     * 
     */
    public TCondition createTCondition() {
        return new TCondition();
    }

    /**
     * Create an instance of {@link TTargets }
     * 
     */
    public TTargets createTTargets() {
        return new TTargets();
    }

    /**
     * Create an instance of {@link TLiteral }
     * 
     */
    public TLiteral createTLiteral() {
        return new TLiteral();
    }

    /**
     * Create an instance of {@link TEmpty }
     * 
     */
    public TEmpty createTEmpty() {
        return new TEmpty();
    }

    /**
     * Create an instance of {@link TFrom }
     * 
     */
    public TFrom createTFrom() {
        return new TFrom();
    }

    /**
     * Create an instance of {@link TCopy }
     * 
     */
    public TCopy createTCopy() {
        return new TCopy();
    }

    /**
     * Create an instance of {@link TCatch }
     * 
     */
    public TCatch createTCatch() {
        return new TCatch();
    }

    /**
     * Create an instance of {@link TReply }
     * 
     */
    public TReply createTReply() {
        return new TReply();
    }

    /**
     * Create an instance of {@link TReceive }
     * 
     */
    public TReceive createTReceive() {
        return new TReceive();
    }

    /**
     * Create an instance of {@link TVariables }
     * 
     */
    public TVariables createTVariables() {
        return new TVariables();
    }

    /**
     * Create an instance of {@link TFromParts }
     * 
     */
    public TFromParts createTFromParts() {
        return new TFromParts();
    }

    /**
     * Create an instance of {@link TDocumentation }
     * 
     */
    public TDocumentation createTDocumentation() {
        return new TDocumentation();
    }

    /**
     * Create an instance of {@link TQuery }
     * 
     */
    public TQuery createTQuery() {
        return new TQuery();
    }

    /**
     * Create an instance of {@link PnmlType }
     * 
     */
    public PnmlType createPnmlType() {
        return new PnmlType();
    }

    /**
     * Create an instance of {@link Fill }
     * 
     */
    public Fill createFill() {
        return new Fill();
    }

    /**
     * Create an instance of {@link TTarget }
     * 
     */
    public TTarget createTTarget() {
        return new TTarget();
    }

    /**
     * Create an instance of {@link TFromPart }
     * 
     */
    public TFromPart createTFromPart() {
        return new TFromPart();
    }

    /**
     * Create an instance of {@link TToParts }
     * 
     */
    public TToParts createTToParts() {
        return new TToParts();
    }

    /**
     * Create an instance of {@link TPartnerLink }
     * 
     */
    public TPartnerLink createTPartnerLink() {
        return new TPartnerLink();
    }

    /**
     * Create an instance of {@link TVariable }
     * 
     */
    public TVariable createTVariable() {
        return new TVariable();
    }

    /**
     * Create an instance of {@link TToPart }
     * 
     */
    public TToPart createTToPart() {
        return new TToPart();
    }

    /**
     * Create an instance of {@link TTo }
     * 
     */
    public TTo createTTo() {
        return new TTo();
    }

    /**
     * Create an instance of {@link Font }
     * 
     */
    public Font createFont() {
        return new Font();
    }

    /**
     * Create an instance of {@link TAssign }
     * 
     */
    public TAssign createTAssign() {
        return new TAssign();
    }

    /**
     * Create an instance of {@link PlaceToolspecificType }
     * 
     */
    public PlaceToolspecificType createPlaceToolspecificType() {
        return new PlaceToolspecificType();
    }

    /**
     * Create an instance of {@link TCorrelations }
     * 
     */
    public TCorrelations createTCorrelations() {
        return new TCorrelations();
    }

    /**
     * Create an instance of {@link PositionType }
     * 
     */
    public PositionType createPositionType() {
        return new PositionType();
    }

    /**
     * Create an instance of {@link OccuredtransitionType }
     * 
     */
    public OccuredtransitionType createOccuredtransitionType() {
        return new OccuredtransitionType();
    }

    /**
     * Create an instance of {@link GraphicsArcType }
     * 
     */
    public GraphicsArcType createGraphicsArcType() {
        return new GraphicsArcType();
    }

    /**
     * Create an instance of {@link TActivity }
     * 
     */
    public TActivity createTActivity() {
        return new TActivity();
    }

    /**
     * Create an instance of {@link SuperModelType }
     * 
     */
    public SuperModelType createSuperModelType() {
        return new SuperModelType();
    }

    /**
     * Create an instance of {@link ResourcesType }
     * 
     */
    public ResourcesType createResourcesType() {
        return new ResourcesType();
    }

    /**
     * Create an instance of {@link SimulationsType }
     * 
     */
    public SimulationsType createSimulationsType() {
        return new SimulationsType();
    }

    /**
     * Create an instance of {@link ImportPlaceType }
     * 
     */
    public ImportPlaceType createImportPlaceType() {
        return new ImportPlaceType();
    }

    /**
     * Create an instance of {@link TExtensibleElements }
     * 
     */
    public TExtensibleElements createTExtensibleElements() {
        return new TExtensibleElements();
    }

    /**
     * Create an instance of {@link AnnotationGraphisType }
     * 
     */
    public AnnotationGraphisType createAnnotationGraphisType() {
        return new AnnotationGraphisType();
    }

    /**
     * Create an instance of {@link OrganizationUnitType }
     * 
     */
    public OrganizationUnitType createOrganizationUnitType() {
        return new OrganizationUnitType();
    }

    /**
     * Create an instance of {@link GraphicsSimpleType }
     * 
     */
    public GraphicsSimpleType createGraphicsSimpleType() {
        return new GraphicsSimpleType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link GraphicsNodeType }
     * 
     */
    public GraphicsNodeType createGraphicsNodeType() {
        return new GraphicsNodeType();
    }

    /**
     * Create an instance of {@link TCorrelationWithPattern }
     * 
     */
    public TCorrelationWithPattern createTCorrelationWithPattern() {
        return new TCorrelationWithPattern();
    }

    /**
     * Create an instance of {@link ArcNameType }
     * 
     */
    public ArcNameType createArcNameType() {
        return new ArcNameType();
    }

    /**
     * Create an instance of {@link NetToolspecificType }
     * 
     */
    public NetToolspecificType createNetToolspecificType() {
        return new NetToolspecificType();
    }

    /**
     * Create an instance of {@link NodeNameType }
     * 
     */
    public NodeNameType createNodeNameType() {
        return new NodeNameType();
    }

    /**
     * Create an instance of {@link TransitionType }
     * 
     */
    public TransitionType createTransitionType() {
        return new TransitionType();
    }

    /**
     * Create an instance of {@link TransitionResourceType }
     * 
     */
    public TransitionResourceType createTransitionResourceType() {
        return new TransitionResourceType();
    }

    /**
     * Create an instance of {@link ArcInscriptionType }
     * 
     */
    public ArcInscriptionType createArcInscriptionType() {
        return new ArcInscriptionType();
    }

    /**
     * Create an instance of {@link DimensionType }
     * 
     */
    public DimensionType createDimensionType() {
        return new DimensionType();
    }

    /**
     * Create an instance of {@link TExpression }
     * 
     */
    public TExpression createTExpression() {
        return new TExpression();
    }

    /**
     * Create an instance of {@link ArcToolspecificType }
     * 
     */
    public ArcToolspecificType createArcToolspecificType() {
        return new ArcToolspecificType();
    }

    /**
     * Create an instance of {@link RoleType }
     * 
     */
    public RoleType createRoleType() {
        return new RoleType();
    }

    /**
     * Create an instance of {@link ArcType }
     * 
     */
    public ArcType createArcType() {
        return new ArcType();
    }

    /**
     * Create an instance of {@link PageType }
     * 
     */
    public PageType createPageType() {
        return new PageType();
    }

    /**
     * Create an instance of {@link TransitionsequenceType }
     * 
     */
    public TransitionsequenceType createTransitionsequenceType() {
        return new TransitionsequenceType();
    }

    /**
     * Create an instance of {@link TCorrelationsWithPattern }
     * 
     */
    public TCorrelationsWithPattern createTCorrelationsWithPattern() {
        return new TCorrelationsWithPattern();
    }

    /**
     * Create an instance of {@link ArcTypeType }
     * 
     */
    public ArcTypeType createArcTypeType() {
        return new ArcTypeType();
    }

    /**
     * Create an instance of {@link InstanceType }
     * 
     */
    public InstanceType createInstanceType() {
        return new InstanceType();
    }

    /**
     * Create an instance of {@link SimulationType }
     * 
     */
    public SimulationType createSimulationType() {
        return new SimulationType();
    }

    /**
     * Create an instance of {@link ResourceMappingType }
     * 
     */
    public ResourceMappingType createResourceMappingType() {
        return new ResourceMappingType();
    }

    /**
     * Create an instance of {@link TCorrelation }
     * 
     */
    public TCorrelation createTCorrelation() {
        return new TCorrelation();
    }

    /**
     * Create an instance of {@link NetNameType }
     * 
     */
    public NetNameType createNetNameType() {
        return new NetNameType();
    }

    /**
     * Create an instance of {@link ReferencePlaceType }
     * 
     */
    public ReferencePlaceType createReferencePlaceType() {
        return new ReferencePlaceType();
    }

    /**
     * Create an instance of {@link TriggerType }
     * 
     */
    public TriggerType createTriggerType() {
        return new TriggerType();
    }

    /**
     * Create an instance of {@link OperatorType }
     * 
     */
    public OperatorType createOperatorType() {
        return new OperatorType();
    }

    /**
     * Create an instance of {@link ResourceType }
     * 
     */
    public ResourceType createResourceType() {
        return new ResourceType();
    }

    /**
     * Create an instance of {@link TransitionToolspecificType.Trigger }
     * 
     */
    public TransitionToolspecificType.Trigger createTransitionToolspecificTypeTrigger() {
        return new TransitionToolspecificType.Trigger();
    }

    /**
     * Create an instance of {@link TransitionToolspecificType.TransitionResource }
     * 
     */
    public TransitionToolspecificType.TransitionResource createTransitionToolspecificTypeTransitionResource() {
        return new TransitionToolspecificType.TransitionResource();
    }

    /**
     * Create an instance of {@link NetType.Name }
     * 
     */
    public NetType.Name createNetTypeName() {
        return new NetType.Name();
    }

    /**
     * Create an instance of {@link NetType.Page }
     * 
     */
    public NetType.Page createNetTypePage() {
        return new NetType.Page();
    }

    /**
     * Create an instance of {@link PlaceType.InitialMarking }
     * 
     */
    public PlaceType.InitialMarking createPlaceTypeInitialMarking() {
        return new PlaceType.InitialMarking();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TLiteral }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "literal")
    public JAXBElement<TLiteral> createLiteral(TLiteral value) {
        return new JAXBElement<TLiteral>(_Literal_QNAME, TLiteral.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TEmpty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "empty")
    public JAXBElement<TEmpty> createEmpty(TEmpty value) {
        return new JAXBElement<TEmpty>(_Empty_QNAME, TEmpty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TTargets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "targets")
    public JAXBElement<TTargets> createTargets(TTargets value) {
        return new JAXBElement<TTargets>(_Targets_QNAME, TTargets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "source")
    public JAXBElement<TSource> createSource(TSource value) {
        return new JAXBElement<TSource>(_Source_QNAME, TSource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TCondition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "transitionCondition")
    public JAXBElement<TCondition> createTransitionCondition(TCondition value) {
        return new JAXBElement<TCondition>(_TransitionCondition_QNAME, TCondition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TInvoke }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "invoke")
    public JAXBElement<TInvoke> createInvoke(TInvoke value) {
        return new JAXBElement<TInvoke>(_Invoke_QNAME, TInvoke.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TPartnerLinks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "partnerLinks")
    public JAXBElement<TPartnerLinks> createPartnerLinks(TPartnerLinks value) {
        return new JAXBElement<TPartnerLinks>(_PartnerLinks_QNAME, TPartnerLinks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TExtensionAssignOperation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "extensionAssignOperation")
    public JAXBElement<TExtensionAssignOperation> createExtensionAssignOperation(TExtensionAssignOperation value) {
        return new JAXBElement<TExtensionAssignOperation>(_ExtensionAssignOperation_QNAME, TExtensionAssignOperation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSources }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "sources")
    public JAXBElement<TSources> createSources(TSources value) {
        return new JAXBElement<TSources>(_Sources_QNAME, TSources.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TWait }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "wait")
    public JAXBElement<TWait> createWait(TWait value) {
        return new JAXBElement<TWait>(_Wait_QNAME, TWait.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TActivityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "catchAll")
    public JAXBElement<TActivityContainer> createCatchAll(TActivityContainer value) {
        return new JAXBElement<TActivityContainer>(_CatchAll_QNAME, TActivityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "reply")
    public JAXBElement<TReply> createReply(TReply value) {
        return new JAXBElement<TReply>(_Reply_QNAME, TReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TCopy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "copy")
    public JAXBElement<TCopy> createCopy(TCopy value) {
        return new JAXBElement<TCopy>(_Copy_QNAME, TCopy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TCatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "catch")
    public JAXBElement<TCatch> createCatch(TCatch value) {
        return new JAXBElement<TCatch>(_Catch_QNAME, TCatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TFrom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "from")
    public JAXBElement<TFrom> createFrom(TFrom value) {
        return new JAXBElement<TFrom>(_From_QNAME, TFrom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TActivityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "compensationHandler")
    public JAXBElement<TActivityContainer> createCompensationHandler(TActivityContainer value) {
        return new JAXBElement<TActivityContainer>(_CompensationHandler_QNAME, TActivityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TCondition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "joinCondition")
    public JAXBElement<TCondition> createJoinCondition(TCondition value) {
        return new JAXBElement<TCondition>(_JoinCondition_QNAME, TCondition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TTarget }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "target")
    public JAXBElement<TTarget> createTarget(TTarget value) {
        return new JAXBElement<TTarget>(_Target_QNAME, TTarget.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PnmlType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "pnml")
    public JAXBElement<PnmlType> createPnml(PnmlType value) {
        return new JAXBElement<PnmlType>(_Pnml_QNAME, PnmlType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TDocumentation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "documentation")
    public JAXBElement<TDocumentation> createDocumentation(TDocumentation value) {
        return new JAXBElement<TDocumentation>(_Documentation_QNAME, TDocumentation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "query")
    public JAXBElement<TQuery> createQuery(TQuery value) {
        return new JAXBElement<TQuery>(_Query_QNAME, TQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TFromParts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "fromParts")
    public JAXBElement<TFromParts> createFromParts(TFromParts value) {
        return new JAXBElement<TFromParts>(_FromParts_QNAME, TFromParts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TVariables }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "variables")
    public JAXBElement<TVariables> createVariables(TVariables value) {
        return new JAXBElement<TVariables>(_Variables_QNAME, TVariables.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TReceive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "receive")
    public JAXBElement<TReceive> createReceive(TReceive value) {
        return new JAXBElement<TReceive>(_Receive_QNAME, TReceive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TAssign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "assign")
    public JAXBElement<TAssign> createAssign(TAssign value) {
        return new JAXBElement<TAssign>(_Assign_QNAME, TAssign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TTo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "to")
    public JAXBElement<TTo> createTo(TTo value) {
        return new JAXBElement<TTo>(_To_QNAME, TTo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TToPart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "toPart")
    public JAXBElement<TToPart> createToPart(TToPart value) {
        return new JAXBElement<TToPart>(_ToPart_QNAME, TToPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "variable")
    public JAXBElement<TVariable> createVariable(TVariable value) {
        return new JAXBElement<TVariable>(_Variable_QNAME, TVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TPartnerLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "partnerLink")
    public JAXBElement<TPartnerLink> createPartnerLink(TPartnerLink value) {
        return new JAXBElement<TPartnerLink>(_PartnerLink_QNAME, TPartnerLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TToParts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "toParts")
    public JAXBElement<TToParts> createToParts(TToParts value) {
        return new JAXBElement<TToParts>(_ToParts_QNAME, TToParts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TFromPart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "pnml.apromore.org", name = "fromPart")
    public JAXBElement<TFromPart> createFromPart(TFromPart value) {
        return new JAXBElement<TFromPart>(_FromPart_QNAME, TFromPart.class, null, value);
    }

}
