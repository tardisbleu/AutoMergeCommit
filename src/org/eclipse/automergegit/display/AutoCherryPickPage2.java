/**
 * 
 */
package org.eclipse.automergegit.display;

import java.util.Iterator;

import org.eclipse.automergegit.connector.GitConnector;
import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.preferences.PreferenceConstants;
import org.eclipse.automergegit.provider.BranchTableProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
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
 * <b>TYPE</b> :  AutoCherryPickPage2<br>
 * <b>NOM</b> : AutoCherryPickPage2.java<br>
 * <b>SUJET</b> : <br>
 * <b>COMMENTAIRE</b> : <br>
 * **************************************************************
 * 
 * @author remy.torres
 * @version $Revision: 1.0 $ $Date: 6 nov. 2018 12:47:26 $
 */
public class AutoCherryPickPage2 extends WizardPage {

	private Text text;
	private Button searchBranch;
	protected TableViewer tableViewer;
	private Table table;
	private TableColumn tblclmnBranchName;
	private TableColumn tblclmnCreateBy;

	public AutoCherryPickPage2() {
		super("Cherry-pick");
		setTitle("Automatic cherry-pick");
		setDescription("Select branch");
	}

	@Override
	public void createControl(Composite parent) {
		AutoCherryPick oAutoCherryPick = (AutoCherryPick) getWizard();
		Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new RowLayout(SWT.HORIZONTAL));
        
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new RowData(550, SWT.DEFAULT));
		text.setToolTipText("Name of the branch");
		
		searchBranch = new Button(container, SWT.CENTER);
		searchBranch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(!text.getText().isEmpty()) {
					tableViewer.setInput(GitConnector.findBranchByName(oAutoCherryPick.getLocationOfGitProject(), text.getText()));
					tableViewer.refresh();
				}
			}
		});
		searchBranch.setText("Search branch");
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table = tableViewer.getTable();
		table.setLayoutData(new RowData(SWT.DEFAULT, 276));
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		tblclmnBranchName = new TableColumn(table, SWT.NONE);
		tblclmnBranchName.setWidth(550);
		tblclmnBranchName.setText("Branch name");
		
		tblclmnCreateBy = new TableColumn(table, SWT.NONE);
		tblclmnCreateBy.setWidth(100);
		tblclmnCreateBy.setText("Create by");
		
		BranchTableProvider oBranchTableProvider = new BranchTableProvider();
		tableViewer.setContentProvider(oBranchTableProvider);
		tableViewer.setLabelProvider(oBranchTableProvider);
		oAutoCherryPick.clearvBranchGit();
		setControl(container);
	}
	
	@Override
	public IWizardPage getNextPage() {
		AutoCherryPick oAutoCherryPick = (AutoCherryPick) getWizard();
		IStructuredSelection listIStructuredSelection = (IStructuredSelection) tableViewer.getSelection();
		if(!listIStructuredSelection.isEmpty()) {
			for (Iterator<BranchGit> iterator = listIStructuredSelection.iterator(); iterator.hasNext();) {
				oAutoCherryPick.addBranchGit(iterator.next());
			}
		}
		return super.getNextPage();
	}

}
