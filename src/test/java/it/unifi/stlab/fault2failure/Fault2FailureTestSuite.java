package it.unifi.stlab.fault2failure;
import it.unifi.stlab.fault2failure.operational.ScenarioTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import it.unifi.stlab.fault2failure.knowledge.propagation.BooleanExpressionTest;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslatorTest;

@RunWith(Suite.class)
@SuiteClasses({ PetriNetTranslatorTest.class, BooleanExpressionTest.class, ScenarioTest.class})
public class Fault2FailureTestSuite {

}