package it.unifi.stlab.fault2failure.knowledge.utils;

import org.apache.commons.math3.distribution.*;
import org.junit.Test;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PDFParserTest {

    @Test
    public void testParseStringToStochasticTransitionFeature(){
        String input;
        StochasticTransitionFeature actual;
        StochasticTransitionFeature expected;

        input = "Uniform(1,3)";
        actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newUniformInstance(BigDecimal.valueOf(1),BigDecimal.valueOf(3));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());

        input = "Dirac(3)";
        actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newDeterministicInstance(BigDecimal.valueOf(3));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());

        input = "Exp(3)";
        actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newExponentialInstance(BigDecimal.valueOf(3));
        assert(actual.density().equals(expected.density()));

        input = "Erlang(3,4)";
        actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newErlangInstance(3, BigDecimal.valueOf(4));
        assertEquals(actual.density().toMathematicaString(), expected.density().toMathematicaString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testException(){
        String input = "poisson(5,3)";
        PDFParser.parseStringToStochasticTransitionFeature(input);
    }

    @Test
    public void testParseStringToRealDistribution(){
        String input;
        RealDistribution actual;
        RealDistribution expected;

        input = "Uniform(1,3)";
        actual = PDFParser.parseStringToRealDistribution(input);
        expected = new UniformRealDistribution(1,3);
        assertEquals(expected.getClass(), actual.getClass());

        input = "Dirac(3)";
        actual = PDFParser.parseStringToRealDistribution(input);
        expected = new NormalDistribution(3, Double.MIN_VALUE);
        assertEquals(expected.getClass(), actual.getClass());

        input = "Exp(3)";
        actual = PDFParser.parseStringToRealDistribution(input);
        expected = new ExponentialDistribution(3);
        assertEquals(expected.getClass(), actual.getClass());

        input = "Erlang(3,4)";
        actual = PDFParser.parseStringToRealDistribution(input);
        expected = new GammaDistribution(3,4);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void testParseStochasticTransitionFeatureToRealDistribution(){
        StochasticTransitionFeature input;
        RealDistribution expected;
        RealDistribution actual;

        input = StochasticTransitionFeature.newUniformInstance(BigDecimal.valueOf(1),BigDecimal.valueOf(3));
        actual = PDFParser.parseStochasticTransitionFeatureToRealDistribution(input);
        expected = new UniformRealDistribution(1,3);
        assertEquals(expected.getClass(), actual.getClass());

        input = StochasticTransitionFeature.newDeterministicInstance(BigDecimal.valueOf(3));
        actual = PDFParser.parseStochasticTransitionFeatureToRealDistribution(input);
        expected = new NormalDistribution(3, Double.MIN_VALUE);
        assertEquals(expected.getClass(), actual.getClass());

        input = StochasticTransitionFeature.newExponentialInstance(BigDecimal.valueOf(3));
        actual = PDFParser.parseStochasticTransitionFeatureToRealDistribution(input);
        expected = new ExponentialDistribution(3);
        assertEquals(expected.getClass(), actual.getClass());

        input = StochasticTransitionFeature.newErlangInstance(3, BigDecimal.valueOf(4));
        actual = PDFParser.parseStochasticTransitionFeatureToRealDistribution(input);
        expected = new GammaDistribution(3,4);
        assertEquals(expected.getClass(), actual.getClass());
    }
    @Test
    public void testParseRealDistributionToStochasticTransitionFeature(){
        RealDistribution input;
        StochasticTransitionFeature expected;
        StochasticTransitionFeature actual;

        input = new UniformRealDistribution(1,3);
        actual = PDFParser.parseRealDistributionToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newUniformInstance(BigDecimal.valueOf(1),BigDecimal.valueOf(3));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());

        input = new NormalDistribution(3, Double.MIN_VALUE);
        actual = PDFParser.parseRealDistributionToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newDeterministicInstance(BigDecimal.valueOf(3));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());

        input = new ExponentialDistribution(3);
        actual = PDFParser.parseRealDistributionToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newExponentialInstance(BigDecimal.valueOf(1/3.0));
        assert(actual.density().equals(expected.density()));

        input = new GammaDistribution(3,4);
        actual = PDFParser.parseRealDistributionToStochasticTransitionFeature(input);
        expected = StochasticTransitionFeature.newErlangInstance(3, BigDecimal.valueOf(4));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());
    }

    @Test
    public void testParseStochasticTransitionFeatureToString(){
        StochasticTransitionFeature input;
        String expected;
        String actual;

        input = StochasticTransitionFeature.newUniformInstance(BigDecimal.valueOf(1),BigDecimal.valueOf(3));
        actual = PDFParser.parseStochasticTransitionFeatureToString(input);
        expected = "uniform(1,3)";
        assertEquals(actual, expected);

        input = StochasticTransitionFeature.newDeterministicInstance(BigDecimal.valueOf(3));
        actual = PDFParser.parseStochasticTransitionFeatureToString(input);
        expected = "dirac(3)";
        assertEquals(actual, expected);

        input = StochasticTransitionFeature.newExponentialInstance(BigDecimal.valueOf(3));
        actual = PDFParser.parseStochasticTransitionFeatureToString(input);
        expected = "exp(3)";
        assertEquals(actual,expected);

        input = StochasticTransitionFeature.newErlangInstance(3, BigDecimal.valueOf(4));
        actual = PDFParser.parseStochasticTransitionFeatureToString(input);
        expected = "erlang(3,4)";
        assertEquals(actual, expected);
    }

    @Test
    public void testParseRealDistributionToString(){
        RealDistribution input;
        String expected;
        String actual;

        input = new UniformRealDistribution(1,3);
        actual = PDFParser.parseRealDistributionToString(input);
        expected = "uniform(1,3)";
        assertEquals(actual, expected);

        input = new NormalDistribution(3, Double.MIN_VALUE);
        actual = PDFParser.parseRealDistributionToString(input);
        expected = "dirac(3)";
        assertEquals(actual, expected);

        input = new ExponentialDistribution(1.0/3);
        actual = PDFParser.parseRealDistributionToString(input);
        expected = "exp(3)";
        assertEquals(expected, actual);

        input = new GammaDistribution(3,4);
        actual = PDFParser.parseRealDistributionToString(input);
        expected = "erlang(3,4)";
        assertEquals(actual, expected);
    }
}
