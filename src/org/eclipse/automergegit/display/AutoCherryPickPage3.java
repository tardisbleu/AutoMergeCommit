/**
 * 
 */
package org.eclipse.automergegit.display;

import java.util.Iterator;

import org.eclipse.automergegit.connector.GitConnector;
import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.model.CommitGit;
import org.eclipse.automergegit.preferences.PreferenceConstants;
import org.eclipse.automergegit.provider.CommitTableProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;

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

	private Text text;
	private Button searchCommit;
	private TableViewer tableViewer;
	private Table table;

	public AutoCherryPickPage3() {
		super("Cherry-pick");
		setTitle("Automatic cherry-pick");
		setDescription("Select your commit to cherry-pick");
	}

	@Override
	public void createControl(Composite parent) {
		AutoCherryPick oAutoCherryPick = (AutoCherryPick) getWizard();
		Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new RowLayout(SWT.HORIZONTAL));
        
        text = new Text(container, SWT.BORDER);
		text.setLayoutData(new RowData(560, SWT.DEFAULT));
		
		searchCommit = new Button(container, SWT.NONE);
		searchCommit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(!text.getText().isEmpty()) {
					String pathToGitProject = PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_STRING);
					tableViewer.setInput(GitConnector.findCommitByNameAndBranch(pathToGitProject, oAutoCherryPick.getvBranchGit(),text.getText()));
					tableViewer.refresh();
				}
			}
		});
		searchCommit.setText("Search commit");
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table = tableViewer.getTable();
		table.setLayoutData(new RowData(SWT.DEFAULT, 276));
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn tblclmnCommitName = new TableColumn(table, SWT.NONE);
		tblclmnCommitName.setWidth(550);
		tblclmnCommitName.setText("Commit message");
		
		TableColumn tblclmnCreateBy = new TableColumn(table, SWT.NONE);
		tblclmnCreateBy.setWidth(100);
		tblclmnCreateBy.setText("Create by");
		
		CommitTableProvider oCommitTableProvider = new CommitTableProvider();
		tableViewer.setContentProvider(oCommitTableProvider);
		tableViewer.setLabelProvider(oCommitTableProvider);
		
		oAutoCherryPick.clearvCommitGit();
		setControl(container);
	}
	
	@Override
	public IWizardPage getNextPage() {
		AutoCherryPick oAutoCherryPick = (AutoCherryPick) getWizard();
		IStructuredSelection listIStructuredSelection = (IStructuredSelection) tableViewer.getSelection();
		if(!listIStructuredSelection.isEmpty()) {
			for (Iterator<CommitGit> iterator = listIStructuredSelection.iterator(); iterator.hasNext();) {
				oAutoCherryPick.addCommitGit(iterator.next());
			}
		}
		return super.getNextPage();
	}

}
