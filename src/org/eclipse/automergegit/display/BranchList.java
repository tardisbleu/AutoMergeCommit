package org.eclipse.automergegit.display;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.automergegit.connector.GitConnector;
import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.preferences.PreferenceConstants;
import org.eclipse.automergegit.provider.BranchTableProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class BranchList {
	protected Shell shlBranchList;
	private Table table;
	private Text text;
	private Button next;
	private Button close;
	private Button searchBranch;
	private TableViewer tableViewer;
	private TableColumn tblclmnBranchName;
	private TableColumn tblclmnCreateBy;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BranchList window = new BranchList();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlBranchList.open();
		shlBranchList.layout();
		while (!shlBranchList.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	@SuppressWarnings("unchecked")
	protected void createContents() {
		shlBranchList = new Shell();
		shlBranchList.setFullScreen(true);
		shlBranchList.setSize(700, 400);
		shlBranchList.setMinimumSize(700, 400);
		shlBranchList.setText("Branch list");
		shlBranchList.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		text = new Text(shlBranchList, SWT.BORDER);
		text.setLayoutData(new RowData(550, SWT.DEFAULT));
		text.setToolTipText("Name of the branch");
		
		searchBranch = new Button(shlBranchList, SWT.CENTER);
		searchBranch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(!text.getText().isEmpty()) {
					String pathToGitProject = PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_STRING);
					tableViewer.setInput(GitConnector.findBranchByName(pathToGitProject, text.getText()));
					tableViewer.refresh();
				}
			}
		});
		searchBranch.setText("Search branch");
		
		
		tableViewer = new TableViewer(shlBranchList, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
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
		
		close = new Button(shlBranchList, SWT.NONE);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shlBranchList.close();
			}
		});
		close.setText("Close X");
		
		next = new Button(shlBranchList, SWT.NONE);
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				IStructuredSelection listIStructuredSelection = (IStructuredSelection) tableViewer.getSelection();
				if(!listIStructuredSelection.isEmpty()) {
					String[] listeBranchName = new String[listIStructuredSelection.size()];
					int i = 0;
					for (Iterator<BranchGit> iterator = listIStructuredSelection.iterator(); iterator.hasNext();) {
						BranchGit oBranchGit = (BranchGit) iterator.next();
						listeBranchName[i] = oBranchGit.getName();
						i++;
					}
					shlBranchList.close();
					CommitList.main(listeBranchName);
				}
			}
		});
		next.setText("Next >");

	}

}
