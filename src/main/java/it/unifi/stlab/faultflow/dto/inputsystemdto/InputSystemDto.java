package it.unifi.stlab.faultflow.dto.inputsystemdto;

import it.unifi.stlab.faultflow.dto.inputsystemdto.bdd.InputBddDto;
import it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree.FaultTreeDto;

public class InputSystemDto {
    private InputBddDto bdd;
    private FaultTreeDto faultTree;

    public InputBddDto getBdd() {
        return bdd;
    }

    public void setBdd(InputBddDto bdd) {
        this.bdd = bdd;
    }

    public FaultTreeDto getFaultTree() {
        return faultTree;
    }

    public void setFaultTree(FaultTreeDto faultTree) {
        this.faultTree = faultTree;
    }
}
