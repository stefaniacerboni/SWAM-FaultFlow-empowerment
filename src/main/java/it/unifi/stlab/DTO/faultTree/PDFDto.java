package it.unifi.stlab.DTO.faultTree;

import java.math.BigDecimal;

public class PDFDto {
	
	protected String density;
	protected BigDecimal eft;
	protected BigDecimal lft;
	
	public PDFDto() {}
	
	public String getDensity() {
		return density;
	}
	public void setDensity(String density) {
		this.density = density;
	}

	public BigDecimal getEft() {
		return eft;
	}
	public void setEft(BigDecimal eft) {
		this.eft = eft;
	}

	public BigDecimal getLft() {
		return lft;
	}
	public void setLft(BigDecimal lft) {
		this.lft = lft;
	}

}
