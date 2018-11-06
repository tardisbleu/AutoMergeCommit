/**
 * 
 */
package org.eclipse.automergegit.display;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * ***************************************************************<br>
 * <b>iGDA - Projet AutoMergeGit</b><br>
 * <b>TYPE</b> :  AutoCherryPickPage4<br>
 * <b>NOM</b> : AutoCherryPickPage4.java<br>
 * <b>SUJET</b> : <br>
 * <b>COMMENTAIRE</b> : <br>
 * **************************************************************
 * 
 * @author remy.torres
 * @version $Revision: 1.0 $ $Date: 6 nov. 2018 13:01:12 $
 */
public class AutoCherryPickPage4 extends WizardPage {

	public AutoCherryPickPage4() {
		super("Cherry-pick");
		setTitle("Automatic cherry-pick");
		setDescription("Summary of cherry-pick");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
	}

}
