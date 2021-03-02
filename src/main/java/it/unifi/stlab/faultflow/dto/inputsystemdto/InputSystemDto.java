package it.unifi.stlab.faultflow.dto.inputsystemdto;

import it.unifi.stlab.faultflow.dto.inputsystemdto.bdd.InputBddDto;
import it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree.InputFaultTreeDto;

public class InputSystemDto {
    private InputBddDto bdd;
    private InputFaultTreeDto faultTree;

    public InputBddDto getBdd() {
        return bdd;
    }

    public void setBdd(InputBddDto bdd) {
        this.bdd = bdd;
    }

    public InputFaultTreeDto getFaultTree() {
        return faultTree;
    }

    public void setFaultTree(InputFaultTreeDto faultTree) {
        this.faultTree = faultTree;
    }
}
