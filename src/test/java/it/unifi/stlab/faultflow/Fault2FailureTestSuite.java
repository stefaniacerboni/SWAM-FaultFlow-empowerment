package it.unifi.stlab.faultflow;

import it.unifi.stlab.faultflow.endpoint.SystemEndpointTest;
import it.unifi.stlab.faultflow.model.operational.ScenarioTest;
import it.unifi.stlab.faultflow.model.propagation.BooleanExpressionTest;
import it.unifi.stlab.faultflow.model.utils.PDFParserTest;
import it.unifi.stlab.faultflow.translator.PetriNetTranslatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({PetriNetTranslatorTest.class, BooleanExpressionTest.class, ScenarioTest.class, PDFParserTest.class,
        SystemEndpointTest.class})
public class Fault2FailureTestSuite {

}
