package org.eclipse.automergegit.provider;

import java.util.List;

import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.model.CommitGit;
import org.eclipse.jgit.revwalk.DepthWalk.Commit;

public class CommitTableProvider extends AbstractTableContentLabelProvider{
	@Override
    public String getColumnText(Object element, int columnIndex) {
		CommitGit oCommitGit = (CommitGit) element;
        switch (columnIndex) {
	        case 0:
	            return oCommitGit.getName();
	        case 1:
	        	return oCommitGit.getCreateBy();
	        default:
	            return null;
        }
    }

	@SuppressWarnings("unchecked")
	@Override
    public Object[] getElements(Object input) {
        List<BranchGit> list = (List<BranchGit>) input;
        return list.toArray();
    }
}
