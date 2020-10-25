package it.unifi.stlab.fault2failure.knowledge.utils;

import org.junit.Test;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PDFParserTest {

    @Test
    public void testUniformPDF(){
        String input = "Uniform(1,3)";
        StochasticTransitionFeature actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        StochasticTransitionFeature expected = StochasticTransitionFeature.newUniformInstance(BigDecimal.valueOf(1),BigDecimal.valueOf(3));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());
    }

    @Test
    public void testDiracPDF(){
        String input = "Dirac(3)";
        StochasticTransitionFeature actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        StochasticTransitionFeature expected = StochasticTransitionFeature.newDeterministicInstance(BigDecimal.valueOf(3));
        assertEquals(expected.density().toMathematicaString(), actual.density().toMathematicaString());
    }

    @Test
    public void testExponentialPDF(){
        String input = "Exp(3)";
        StochasticTransitionFeature actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        StochasticTransitionFeature expected = StochasticTransitionFeature.newExponentialInstance(BigDecimal.valueOf(3));
        assert(actual.density().equals(expected.density()));
    }

    @Test
    public void testErlangPDF(){
        String input = "Erlang(3,4)";
        StochasticTransitionFeature actual = PDFParser.parseStringToStochasticTransitionFeature(input);
        StochasticTransitionFeature expected = StochasticTransitionFeature.newErlangInstance(3, BigDecimal.valueOf(4));
        assertEquals(actual.density().toMathematicaString(), expected.density().toMathematicaString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testException(){
        String input = "poisson(5,3)";
        PDFParser.parseStringToStochasticTransitionFeature(input);
    }
}
