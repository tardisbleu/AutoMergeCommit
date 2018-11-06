package org.eclipse.automergegit.handlers;

import org.eclipse.automergegit.display.AutoCherryPick;
import org.eclipse.automergegit.display.BranchList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		//BranchList.main(null);
		WizardDialog wizardDialog = new WizardDialog(window.getShell(),new AutoCherryPick());
		wizardDialog.open();
		return null;
	}
}
