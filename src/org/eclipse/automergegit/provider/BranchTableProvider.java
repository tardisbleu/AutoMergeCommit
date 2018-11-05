package org.eclipse.automergegit.provider;

import java.util.List;

import org.eclipse.automergegit.model.BranchGit;

public class BranchTableProvider extends AbstractTableContentLabelProvider {

	@Override
    public String getColumnText(Object element, int columnIndex) {
		BranchGit branch = (BranchGit) element;
        switch (columnIndex) {
	        case 0:
	            return branch.getName();
	        case 1:
	            return branch.getCreateBy();
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