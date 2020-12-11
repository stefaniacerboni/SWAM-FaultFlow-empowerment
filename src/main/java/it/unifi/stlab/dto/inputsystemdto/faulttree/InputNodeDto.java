package it.unifi.stlab.dto.inputsystemdto.faulttree;

import java.math.BigDecimal;
import java.util.List;

public class InputNodeDto {

	private String externalId;
	private String label;
	
	private NodeType nodeType;
	
	private String pdf;
	
	private GateType gateType;
	private Integer k;
	private Integer n; 
	private BigDecimal delay;

	private List<AliasDTO> aliases;
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public NodeType getNodeType() {
		return nodeType;
	}
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public GateType getGateType() {
		return gateType;
	}
	public void setGateType(GateType type) {
		this.gateType = type;
	}
	public Integer getK() {
		return k;
	}
	public void setK(Integer k) {
		this.k = k;
	}
	public Integer getN() {
		return n;
	}
	public void setN(Integer n) {
		this.n = n;
	}
	public BigDecimal getDelay() {
		return delay;
	}
	public void setDelay(BigDecimal delay) {
		this.delay = delay;
	}
	public List<AliasDTO> getAliases() {
		return aliases;
	}

	public void setAliases(List<AliasDTO> aliases) {
		this.aliases = aliases;
	}
}
