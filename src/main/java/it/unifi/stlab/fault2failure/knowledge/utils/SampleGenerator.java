package it.unifi.stlab.fault2failure.knowledge.utils;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.math.BigDecimal;

public class SampleGenerator {
    public static double generate(String pdf){
        String typePDF = pdf.toLowerCase().replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = pdf.substring(typePDF.length() + 1, pdf.length() - 1);
        String[] args;
        switch (typePDF) {
            case "uniform":
                //two arguments
                args = arguments.split(",");
                return new UniformRealDistribution(Double.parseDouble(args[0]), Double.parseDouble(args[1])).sample();
            case "dirac":
                //one argument
                return new NormalDistribution(Double.parseDouble(arguments), Double.MIN_VALUE).sample();
            case "exp":
                //one argument
                return new ExponentialDistribution(Double.parseDouble(arguments)).sample();
            case "erlang":
                args = arguments.split(",");
                //assuming args[1] is integer TODO correct
                return new GammaDistribution(Double.parseDouble(args[0]), Double.parseDouble(args[1])).sample();
            case "gaussian":
                args = arguments.split(",");
                return new NormalDistribution(Double.parseDouble(args[0]), Double.parseDouble(args[1])).sample();
            default:
                throw new UnsupportedOperationException("PDF not supported");
        }
    }
}
