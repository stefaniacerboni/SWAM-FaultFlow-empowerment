package it.unifi.stlab.faultflow.model.propagation;

import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.faultflow.model.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.operators.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BooleanExpressionTest {
    private FaultMode A_Fault1, A_Fault2, A_Fault3;
    private BooleanExpression a_Failure1;

    @Before
    public void setBooleanExpression() {
        //Testing (!Fault1)&&((Fault2)||(Fault3))
        A_Fault1 = new EndogenousFaultMode("A_Fault1");
        A_Fault2 = new EndogenousFaultMode("A_Fault2");
        A_Fault3 = new EndogenousFaultMode("A_Fault3");

        a_Failure1 = new AND();
        Operator notA_Fault1 = new NOT();
        notA_Fault1.addChild(A_Fault1);
        a_Failure1.addChild(notA_Fault1);
        Operator a_Failure1b = new OR();
        a_Failure1b.addChild(A_Fault2);
        a_Failure1b.addChild(A_Fault3);
        a_Failure1.addChild(a_Failure1b);
    }

    @Test
    public void testCompute() {
        //Testing (!Fault1)&&((Fault2)||(Fault3))
        //All switched off-> NOT is True -first part of the AND true-, OR is false
        assertFalse(a_Failure1.compute());
        A_Fault2.setState(true);
        //A_Fault2 switched on-> OR is true, BooleanExpression all true
        assertTrue(a_Failure1.compute());
        A_Fault1.setState(true);
        //A_Fault1 is true, !A_Fault1 is false, first part of the AND false-> all false
        assertFalse(a_Failure1.compute());
    }

    @Test
    public void testExtractIncomingFails() {
        List<FaultMode> expected = new ArrayList<>();
        expected.add(A_Fault1);
        expected.add(A_Fault2);
        expected.add(A_Fault3);

        List<FaultMode> actual = a_Failure1.extractIncomingFaults();
        assertThat(actual, is(expected));
    }

    @Test
    public void testAddChild() {
        assertEquals(3, a_Failure1.extractIncomingFaults().size());
        a_Failure1.addChild(new EndogenousFaultMode("Temp"));
        assertEquals(4, a_Failure1.extractIncomingFaults().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddChild_NotInLeaf() {
        A_Fault1.addChild(new EndogenousFaultMode("Temp"));
    }

    @Test
    public void testRemoveChild() {
        assertEquals(3, a_Failure1.extractIncomingFaults().size());
        a_Failure1.removeChild(A_Fault2);
        assertEquals(2, a_Failure1.extractIncomingFaults().size());
    }

    @Test
    public void testRemoveOperator() {
        AND temp = new AND();
        temp.addChild(new EndogenousFaultMode("tempFail"));
        a_Failure1.addChild(temp);
        assertEquals(4, a_Failure1.extractIncomingFaults().size());
        a_Failure1.removeChild(temp);
        assertEquals(3, a_Failure1.extractIncomingFaults().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveChild_NotInLeaf() {
        A_Fault1.removeChild(A_Fault2);
    }

    @Test
    public void testToString() {
        HashMap<String, FaultMode> failModes = new HashMap<>();
        BooleanExpression a_failure = BooleanExpression.config("(!Fault1)&&(Fault2||Fault3)", failModes);
        assertEquals("(!Fault1>0)&&((Fault2>0)||(Fault3>0))", a_failure.toString());
    }

    @Test
    public void testAddInNOT() {
        //NOT is an Unary Operator, so it must have always just one element (FailureMode or Operator) inside its list of elements.
        Operator not = new NOT();
        not.addChild(new EndogenousFaultMode("temp1"));
        assertEquals(1, not.getElements().size());
        Assert.assertEquals("temp1>0", not.getElements().get(0).toString());
        //When I add another element, delete the previous one and add the new one
        not.addChild(new EndogenousFaultMode("temp2"));
        assertEquals(1, not.getElements().size());
        Assert.assertEquals("temp2>0", not.getElements().get(0).toString());
    }

    @Test
    public void testKOutOfN() {
        Operator kofn = new KofN(2, 3);
        kofn.addChild(A_Fault1);
        kofn.addChild(A_Fault2);
        kofn.addChild(A_Fault3);
        assertFalse(kofn.compute());
        A_Fault1.setState(true);
        assertFalse(kofn.compute());
        A_Fault2.setState(true);
        assertTrue(kofn.compute());
        A_Fault3.setState(true);
        assertTrue(kofn.compute());
        assertEquals("(A_Fault1>0&&A_Fault2>0)||(A_Fault1>0&&A_Fault3>0)||(A_Fault2>0&&A_Fault3>0)", kofn.toString());
    }
}
