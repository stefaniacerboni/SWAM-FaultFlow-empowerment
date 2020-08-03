package it.unifi.stlab.fault2failure.knowledge.propagation;

import org.oristool.models.stpn.trees.StochasticTransitionFeature;

public class EndogenousFaultMode extends FaultMode{
    private StochasticTransitionFeature arisingPDF;

    public EndogenousFaultMode(String name){
        this.name = name;
        this.arisingPDF= null;
    }


    @Override
    public boolean compute() {
        return false;
    }
}