package it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree;

public class ParentingDto {

	private String parentId;
	private String childId;

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
}
