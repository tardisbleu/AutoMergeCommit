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
 * <b>TYPE</b> :  AutoCherryPickPage3<br>
 * <b>NOM</b> : AutoCherryPickPage3.java<br>
 * <b>SUJET</b> : <br>
 * <b>COMMENTAIRE</b> : <br>
 * **************************************************************
 * 
 * @author remy.torres
 * @version $Revision: 1.0 $ $Date: 6 nov. 2018 13:00:28 $
 */
public class AutoCherryPickPage3 extends WizardPage {

	public AutoCherryPickPage3() {
		super("Cherry-pick");
		setTitle("Automatic cherry-pick");
		setDescription("Select your commit to cherry-pick");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
	}

}
