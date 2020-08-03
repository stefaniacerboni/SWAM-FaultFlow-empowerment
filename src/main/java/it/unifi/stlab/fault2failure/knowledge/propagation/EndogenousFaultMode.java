package it.unifi.stlab.fault2failure.knowledge.propagation;

import org.jfree.util.StringUtils;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;

public class EndogenousFaultMode extends FaultMode{
    private StochasticTransitionFeature arisingPDF;

    public EndogenousFaultMode(String name){
        this.name = name;
        this.arisingPDF= null;
    }

    public StochasticTransitionFeature getArisingPDF() {
        return arisingPDF;
    }

    public void setArisingPDF(String arisingPDF) {
        String typePDF = arisingPDF.replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = arisingPDF.substring(typePDF.length()+1, arisingPDF.length()-1);
        String[] args;
        switch (typePDF){
            case "uniform":
                //two arguments
                args = arguments.split(",");
                this.arisingPDF = StochasticTransitionFeature.newUniformInstance(args[0], args[1]);
                break;
            case "dirac":
                //one argument
                this.arisingPDF = StochasticTransitionFeature.newDeterministicInstance(arguments);
                break;
            case "exp":
                //one argument
                this.arisingPDF = StochasticTransitionFeature.newExponentialInstance(arguments);
                break;
            case "erlang":
                args = arguments.split(",");
                this.arisingPDF = StochasticTransitionFeature.newErlangInstance(Integer.parseInt(args[0]), args[1]);
                break;
        }

    }

    @Override
    public boolean compute() {
        return false;
    }
}