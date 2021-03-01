package it.unifi.stlab;

import it.unifi.stlab.model.propagation.BooleanExpressionTest;
import it.unifi.stlab.translator.PetriNetTranslatorTest;
import it.unifi.stlab.model.operational.ScenarioTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({PetriNetTranslatorTest.class, BooleanExpressionTest.class, ScenarioTest.class})
public class Fault2FailureTestSuite {

}
