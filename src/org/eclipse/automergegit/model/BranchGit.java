package org.eclipse.automergegit.model;

public class BranchGit {
	private String name;
	private String createBy;
	
	public BranchGit(String name,String createBy) {
		this.name = name;
		this.createBy = createBy;
	}
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
