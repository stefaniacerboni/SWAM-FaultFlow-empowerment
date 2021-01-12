package it.unifi.stlab.fault2failure.knowledge.utils;

import org.apache.commons.math3.distribution.*;
import org.oristool.math.function.EXP;
import org.oristool.math.function.Erlang;
import org.oristool.math.function.GEN;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;

public class PDFParser {
    public static StochasticTransitionFeature parseStringToStochasticTransitionFeature(String arisingPDF) {
        String typePDF = arisingPDF.toLowerCase().replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = arisingPDF.substring(typePDF.length() + 1, arisingPDF.length() - 1);
        String[] args;
        switch (typePDF) {
            case "": //assume dirac(0)
                return StochasticTransitionFeature.newDeterministicInstance("0");
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
            case "gaussian"://a=μ-radq(3)*(σ); b=μ+radq(3)*(σ)
                args = arguments.split(",");
                double factor = Math.sqrt(3)*Double.parseDouble(args[1]);
                String a = ""+(Double.parseDouble(args[0])-factor);
                String b = ""+(Double.parseDouble(args[0])+factor);
                return StochasticTransitionFeature.newUniformInstance(a,b);
            default:
                throw new UnsupportedOperationException("PDF not supported");
        }
    }

    public static RealDistribution parseStringToRealDistribution(String arisingPDF) {
        String typePDF = arisingPDF.toLowerCase().replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = arisingPDF.substring(typePDF.length() + 1, arisingPDF.length() - 1);
        String[] args;
        switch (typePDF) {
            case"": //assume dirac(0)
                return new NormalDistribution(0.0, Double.MIN_VALUE);
            case "uniform":
                //two arguments
                args = arguments.split(",");
                return new UniformRealDistribution(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
            case "dirac":
                //one argument
                return new NormalDistribution(Double.parseDouble(arguments), Double.MIN_VALUE);
            case "exp":
                //one argument
                return new ExponentialDistribution(1/(Double.parseDouble(arguments)));
            case "erlang":
                args = arguments.split(",");
                //assuming args[1] is integer TODO correct
                return new GammaDistribution(Double.parseDouble(args[0]), (Integer.parseInt(args[1])));
            case "gaussian":
                args = arguments.split(",");//args[0] is the mean, args[1] is the Standard Deviation
                return new NormalDistribution(Double.parseDouble(args[0]),Double.parseDouble(args[1]));
            default:
                throw new UnsupportedOperationException("PDF not supported");
        }
    }

    public static RealDistribution parseStochasticTransitionFeatureToRealDistribution(StochasticTransitionFeature stochasticTransitionFeature) {
        if (Erlang.class.equals(stochasticTransitionFeature.density().getClass())) {
            return new GammaDistribution(((Erlang) stochasticTransitionFeature.density()).getShape(),
                    Double.parseDouble(String.valueOf(((Erlang) stochasticTransitionFeature.density()).getLambda())));
        } else if (GEN.class.equals(stochasticTransitionFeature.density().getClass())) {
            String domain = ((GEN) stochasticTransitionFeature.density()).getDomain().toString().replaceAll(" ", "").replace("\n", "");
            String[] bounds = domain.split("<=");
            if (bounds[0].equals(bounds[2])) {
                return new NormalDistribution(Double.parseDouble(bounds[0]), Double.MIN_VALUE);
            } else {
                return new UniformRealDistribution(Double.parseDouble(bounds[0]), Double.parseDouble(bounds[2]));
            }
        } else if (EXP.class.equals(stochasticTransitionFeature.density().getClass())) {
            return new ExponentialDistribution(1/Double.parseDouble(String.valueOf(((EXP) stochasticTransitionFeature.density()).getLambda().intValue())));

        } else
            throw new UnsupportedOperationException("This type of StochasticTransitionFeature is unsupported");
    }

    public static StochasticTransitionFeature parseRealDistributionToStochasticTransitionFeature(RealDistribution realDistribution) {
        if (realDistribution.getClass().equals(UniformRealDistribution.class)) {
            return StochasticTransitionFeature.newUniformInstance(String.valueOf(realDistribution.getSupportLowerBound()),
                    String.valueOf(realDistribution.getSupportUpperBound()));
        } else if (realDistribution.getClass().equals(NormalDistribution.class)) {
            if (((NormalDistribution) realDistribution).getStandardDeviation() == Double.MIN_VALUE)
                return StochasticTransitionFeature.newDeterministicInstance(String.valueOf(realDistribution.getNumericalMean()));
            else {
                double factor = Math.sqrt(realDistribution.getNumericalVariance()*3);
                String a = ""+(realDistribution.getNumericalMean()-factor);
                String b = ""+(realDistribution.getNumericalMean()+factor);
                return StochasticTransitionFeature.newUniformInstance(a,b);
            }
        } else if (realDistribution.getClass().equals(ExponentialDistribution.class)) {
            return StochasticTransitionFeature.newExponentialInstance(String.valueOf((int)(1/(((ExponentialDistribution) realDistribution).getMean()))));
        } else if (realDistribution.getClass().equals(GammaDistribution.class)) {
            return StochasticTransitionFeature.newErlangInstance(((int) ((GammaDistribution) realDistribution).getShape()), String.valueOf((int)((GammaDistribution) realDistribution).getScale()));
        } else
            throw new UnsupportedOperationException("This type of RealDistribution is unsupported");
    }

    public static String parseStochasticTransitionFeatureToString(StochasticTransitionFeature stochasticTransitionFeature){
        if (Erlang.class.equals(stochasticTransitionFeature.density().getClass())) {
            return "erlang("+ ((Erlang)stochasticTransitionFeature.density()).getShape()+","+((Erlang) stochasticTransitionFeature.density()).getLambda()+")";
        } else if (GEN.class.equals(stochasticTransitionFeature.density().getClass())) {
            String domain = ((GEN) stochasticTransitionFeature.density()).getDomain().toString().replaceAll(" ", "").replace("\n", "");
            String[] bounds = domain.split("<=");
            if(bounds[0].equals(bounds[2]))
                return "dirac("+bounds[0]+")";
            else
                return "uniform(" +bounds[0]+","+bounds[2]+")";
        } else if (EXP.class.equals(stochasticTransitionFeature.density().getClass())) {
            return "exp("+((EXP) stochasticTransitionFeature.density()).getLambda()+")";

        } else
            throw new UnsupportedOperationException("This type of StochasticTransitionFeature is unsupported");
    }

    public static String parseRealDistributionToString(RealDistribution realDistribution) {
        if (realDistribution.getClass().equals(NormalDistribution.class)){
            if(((NormalDistribution) realDistribution).getStandardDeviation() == Double.MIN_VALUE){
                return "dirac(" +(int) ((NormalDistribution) realDistribution).getMean() + ")";
            }
            else{
                return "gaussian("+(int)((NormalDistribution) realDistribution).getMean()+","+(int)((NormalDistribution)realDistribution).getStandardDeviation()+")";
            }
        }
        else if(realDistribution.getClass().equals(UniformRealDistribution.class))
            return "uniform("+ (int)realDistribution.getSupportLowerBound()+","+ (int)realDistribution.getSupportUpperBound()+")";
        else if (realDistribution.getClass().equals(ExponentialDistribution.class)) {
            return "exp("+ (int) (1 /(((ExponentialDistribution) realDistribution).getMean()))+")";
        } else if (realDistribution.getClass().equals(GammaDistribution.class)) {
            return "erlang("+(int)((GammaDistribution) realDistribution).getShape()+","+(int)((GammaDistribution) realDistribution).getScale()+")";
        }
        else
            throw new UnsupportedOperationException("This type of RealDistribution is unsupported");
    }

}
