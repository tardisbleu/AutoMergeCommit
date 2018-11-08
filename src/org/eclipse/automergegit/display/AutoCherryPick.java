/**
 * 
 */
package org.eclipse.automergegit.display;

import java.util.Vector;

import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.model.CommitGit;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * ***************************************************************<br>
 * <b>iGDA - Projet AutoMergeGit</b><br>
 * <b>TYPE</b> :  AutoCherryPick<br>
 * <b>NOM</b> : AutoCherryPick.java<br>
 * <b>SUJET</b> : <br>
 * <b>COMMENTAIRE</b> : <br>
 * **************************************************************
 * 
 * @author remy.torres
 * @version $Revision: 1.0 $ $Date: 6 nov. 2018 12:46:16 $
 */
public class AutoCherryPick extends Wizard {
	
	protected AutoCherryPickPage1 p1;
	protected AutoCherryPickPage2 p2;
	protected AutoCherryPickPage3 p3;
	protected AutoCherryPickPage4 p4;
	protected String locationOfGitProject;
	protected Vector<BranchGit> vBranchGit;
	protected Vector<CommitGit> vCommitGit;

	public AutoCherryPick() {
        super();
        setNeedsProgressMonitor(true);
        vBranchGit = new Vector<>();
        vCommitGit = new Vector<>();
        locationOfGitProject = "";
    }

	@Override
	public void addPages() {
		p1 = new AutoCherryPickPage1();
		p2 = new AutoCherryPickPage2();
		p3 = new AutoCherryPickPage3();
		p4 = new AutoCherryPickPage4();
        addPage(p1);
        addPage(p2);
        addPage(p3);
        addPage(p4);
	}
	

	@Override
	public boolean performFinish() {
		if(this.getContainer().getCurrentPage() == p4) {
			return true;
		}
		return false;
	}
	
	public String getLocationOfGitProject() {
		return locationOfGitProject;
	}
	public void setLocationOfGitProject(String locationOfGitProject) {
		this.locationOfGitProject = locationOfGitProject;
	}
	
	public void addBranchGit(BranchGit pBranchGit) {
		vBranchGit.add(pBranchGit);
	}
	public void clearvBranchGit() {
		vBranchGit.clear();
	}
	public Vector<BranchGit> getvBranchGit() {
		return vBranchGit;
	}
	public void setvBranchGit(Vector<BranchGit> vBranchGit) {
		this.vBranchGit = vBranchGit;
	}
	
	public void addCommitGit(CommitGit pCommitGit) {
		vCommitGit.add(pCommitGit);
	}
	public void clearvCommitGit() {
		vCommitGit.clear();
	}
	public Vector<CommitGit> getvCommitGit() {
		return vCommitGit;
	}
	public void setvCommitGit(Vector<CommitGit> vCommitGit) {
		this.vCommitGit = vCommitGit;
	}
	

}
