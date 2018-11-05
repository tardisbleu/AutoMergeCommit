package org.eclipse.automergegit.connector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;

import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.model.CommitGit;
import org.eclipse.jgit.api.CherryPickCommand;
import org.eclipse.jgit.api.CherryPickResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitConnector {
	public static Vector<BranchGit> findBranchByName(String sGitDir,String name){
		Vector<BranchGit> vBranchGit = new Vector<>();
		try {
			Git oGit = new Git(getRepository(sGitDir));
			List<Ref> listOfBranch = oGit.branchList().setListMode(ListMode.ALL).call();
			if(!listOfBranch.isEmpty()) {
				for (Ref ref : listOfBranch) {
					if(ref.getName().contains(name)) {
						vBranchGit.add(new BranchGit(ref.getName(), ""));
					}
				}
			}
			oGit.close();
		} catch (Exception e) {
		}
		return vBranchGit;
	}
	
	public static Vector<CommitGit> findCommitByNameAndBranch(String sGitDir, String[] listBranchName, String name){
		Vector<CommitGit> vCommitGit = new Vector<>();
		try {
			Git oGit = new Git(getRepository(sGitDir));
			for (String branchName : listBranchName) {
				for (RevCommit commit : oGit.log().add(getRepository(sGitDir).resolve(branchName)).call()) {
					if(commit.getShortMessage().contains(name)) {
						vCommitGit.add(new CommitGit(commit.getId(),commit.getShortMessage(),commit.getAuthorIdent().getName()));
					}
				}	
			}
			oGit.close();
		} catch (Exception e) {
		}
		return vCommitGit;
	}
	
	private static Repository getRepository(String dir) throws IOException {
		return new FileRepositoryBuilder().setGitDir(new File(dir)).readEnvironment().findGitDir().setMustExist(true).build();
		
	}
	
	public static CherryPickResult cherryPickAllCommit(String sGitDir,Vector<AnyObjectId> vCommitId) throws IOException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, NoHeadException, GitAPIException {
		Git oGit = new Git(getRepository(sGitDir));
		CherryPickCommand oCherryPickCommand = oGit.cherryPick();
		for (AnyObjectId commitId : vCommitId) {
			oCherryPickCommand.include(commitId);
		}
		CherryPickResult resultOfCherryPick = oCherryPickCommand.call();
		oGit.close();
		return resultOfCherryPick;
	}
	
	public static boolean pushToUpStream(String sGitDir) {
		try {
			Git oGit = new Git(getRepository(sGitDir));
			oGit.push().setRemote("origin").call();
			oGit.close();
			return true;
		} catch (GitAPIException | IOException e) {
			return false;
		}
	}
}
