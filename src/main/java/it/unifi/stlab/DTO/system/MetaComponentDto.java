package it.unifi.stlab.DTO.system;

public class MetaComponentDto {
    String name;
    CompositionPortDto compositionPort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompositionPortDto getCompositionPortDto() {
        return compositionPort;
    }

    public void setCompositionPortDto(CompositionPortDto compositionPortDto) {
        this.compositionPort = compositionPortDto;
    }
}
