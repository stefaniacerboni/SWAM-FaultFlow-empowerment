package it.unifi.stlab.faultflow.model.utils;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.oristool.math.OmegaBigDecimal;
import org.oristool.math.expression.Expolynomial;
import org.oristool.math.function.GEN;
import org.oristool.simulator.samplers.MetropolisHastings;

public class ExpolynomialDistribution extends AbstractRealDistribution {
    private GEN expolynomialFunction;
    public ExpolynomialDistribution(String density, OmegaBigDecimal eft, OmegaBigDecimal lft) {
        expolynomialFunction = GEN.newExpolynomial(density, eft, lft);
    }

    @Override
    public double density(double x) {
        //TODO calculate density (no function in Sirio Expolynomial Class)
        return 0;
    }

    @Override
    public double cumulativeProbability(double x) {
        //TODO calculate cumulativeProbability (no function in Sirio Expolynomial Class)
        return 0;
    }

    @Override
    public double getNumericalMean() {
        //TODO calculate numericalMean (no function in Sirio Expolynomial Class)

        return 0;
    }

    @Override
    public double getNumericalVariance() {
        //TODO calculate numericalVariance (no function in Sirio Expolynomial Class)

        return 0;
    }

    @Override
    public double getSupportLowerBound() {
        return getEft().doubleValue();
    }

    @Override
    public double getSupportUpperBound() {
        return getLft().doubleValue();
    }

    @Deprecated
    @Override
    public boolean isSupportLowerBoundInclusive() {
        return false;
    }

    @Deprecated
    @Override
    public boolean isSupportUpperBoundInclusive() {
        return false;
    }

    @Override
    public boolean isSupportConnected() {
        return false;
    }

    @Override
    public double sample() {
        MetropolisHastings metropolisHastings = new MetropolisHastings(expolynomialFunction);
        return metropolisHastings.getSample().doubleValue();
    }

    public String getDensity() {
        return expolynomialFunction.getDensity().toString();
    }

    public OmegaBigDecimal getEft() {
        return expolynomialFunction.getDomainsEFT();
    }

    public OmegaBigDecimal getLft() {
        return expolynomialFunction.getDomainsLFT();
    }
}
