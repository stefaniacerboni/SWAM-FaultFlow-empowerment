package it.unifi.stlab.fault2failure.knowledge.utils;

import org.oristool.models.stpn.trees.StochasticTransitionFeature;

public class ParseString {
    public static StochasticTransitionFeature parseStringToPDF (String arisingPDF){
        String typePDF = arisingPDF.replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = arisingPDF.substring(typePDF.length() + 1, arisingPDF.length() - 1);
        String[] args;
        switch (typePDF) {
            case "uniform":
                //two arguments
                args = arguments.split(",");
                return StochasticTransitionFeature.newUniformInstance(args[0], args[1]);
            case "dirac":
                //one argument
                return StochasticTransitionFeature.newDeterministicInstance(arguments);
            case "exp":
                //one argument
                return StochasticTransitionFeature.newExponentialInstance(arguments);
            case "erlang":
                args = arguments.split(",");
                return StochasticTransitionFeature.newErlangInstance(Integer.parseInt(args[0]), args[1]);
            default:
                throw new UnsupportedOperationException("PDF not supported");
        }
    }
}
