package org.eclipse.automergegit.display;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.automergegit.connector.GitConnector;
import org.eclipse.automergegit.model.BranchGit;
import org.eclipse.automergegit.model.CommitGit;
import org.eclipse.automergegit.preferences.PreferenceConstants;
import org.eclipse.automergegit.provider.BranchTableProvider;
import org.eclipse.automergegit.provider.CommitTableProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jgit.api.CherryPickResult;
import org.eclipse.jgit.api.CherryPickResult.CherryPickStatus;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CommitList {

	protected Shell shell;
	private Table table;
	private Text text;
	private Button next;
	private Button close;
	private TableViewer tableViewer;
	private static String[] listBranch;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			listBranch = args;
			CommitList window = new CommitList();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setFullScreen(true);
		shell.setSize(700, 400);
		shell.setMinimumSize(700, 400);
		shell.setText("Commit list");
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new RowData(560, SWT.DEFAULT));
		
		Button searchCommit = new Button(shell, SWT.NONE);
		searchCommit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(!text.getText().isEmpty()) {
					String pathToGitProject = PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_STRING);
					tableViewer.setInput(GitConnector.findCommitByNameAndBranch(pathToGitProject, listBranch,text.getText()));
					tableViewer.refresh();
				}
			}
		});
		searchCommit.setText("Search commit");
		
		tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
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
		
		CommitTableProvider oBranchTableProvider = new CommitTableProvider();
		tableViewer.setContentProvider(oBranchTableProvider);
		tableViewer.setLabelProvider(oBranchTableProvider);
		
		close = new Button(shell, SWT.NONE);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		close.setText("Close X");
		
		next = new Button(shell, SWT.NONE);
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				IStructuredSelection listIStructuredSelection = (IStructuredSelection) tableViewer.getSelection();
				if(!listIStructuredSelection.isEmpty()) {
					Vector<AnyObjectId> vCommitId = new Vector<>();
					for (Iterator<CommitGit> iterator = listIStructuredSelection.iterator(); iterator.hasNext();) {
						CommitGit oCommitGit = (CommitGit) iterator.next();
						vCommitId.add(oCommitGit.getId());
					}
					try {
						String pathToGitProject = PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_STRING);
						CherryPickResult resultOfCherryPick = GitConnector.cherryPickAllCommit(pathToGitProject, vCommitId);
						switch (resultOfCherryPick.getStatus()) {
							case OK:
								String push = PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_CHOICE);
								if(push.equals("1")) {
									boolean pushOK = GitConnector.pushToUpStream(pathToGitProject);
									if (pushOK) {
										MessageDialog.openInformation(shell, "Cherry-pick", "All cherry-pick is done and push is done");
									}else {
										MessageDialog.openInformation(shell, "Cherry-pick", "All cherry-pick is done but an error has occurred during push branch");
									}
								}
								break;
							case CONFLICTING:
								MessageDialog.openWarning(shell, "Cherry-pick", "Conflicts : You need to resolve conflicts");
								break;
							case FAILED:
								MessageDialog.openError(shell, "Cherry-pick", "Error : Error during cherry-pick");
								break;
						}
					} catch (IOException | GitAPIException e1) {
						e1.printStackTrace();
					}
					shell.close();
				}
			}
		});
		next.setText("Commit >");

	}

}
