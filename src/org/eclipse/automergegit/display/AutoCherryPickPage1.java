/**
 * 
 */
package org.eclipse.automergegit.display;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * ***************************************************************<br>
 * <b>iGDA - Projet AutoMergeGit</b><br>
 * <b>TYPE</b> :  AutoCherryPickPage1<br>
 * <b>NOM</b> : AutoCherryPickPage1.java<br>
 * <b>SUJET</b> : <br>
 * <b>COMMENTAIRE</b> : <br>
 * **************************************************************
 * 
 * @author remy.torres
 * @version $Revision: 1.0 $ $Date: 6 nov. 2018 12:46:58 $
 */
public class AutoCherryPickPage1 extends WizardPage {

	private Text text1;

	public AutoCherryPickPage1() {
		super("Cherry-pick");
		setTitle("Automatic cherry-pick");
		setDescription("Select the location of your git project");
	}

	@Override
	public void createControl(Composite parent) {
		AutoCherryPick oAutoCherryPick = (AutoCherryPick) getWizard();
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 2;
        Label label1 = new Label(container, SWT.NONE);
        label1.setText("Location of git project :");
        
        text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
        text1.setText("");
        text1.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!text1.getText().isEmpty()) {
                	oAutoCherryPick.setLocationOfGitProject(text1.getText());
                    setPageComplete(true);
                }else {
                	setPageComplete(false);
                }
            }
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			
        });
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        text1.setLayoutData(gd);
		setControl(container);
		setPageComplete(false);
	}

}
