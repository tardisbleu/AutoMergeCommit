package org.eclipse.automergegit.model;

import org.eclipse.jgit.lib.AnyObjectId;

public class CommitGit {
	private AnyObjectId id;
	private String name;
	private String createBy;

	public CommitGit(AnyObjectId id,String name,String createBy) {
		this.id = id;
		this.name = name;
		this.createBy = createBy;
	}

	public AnyObjectId getId() {
		return id;
	}

	public void setId(AnyObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	
}
