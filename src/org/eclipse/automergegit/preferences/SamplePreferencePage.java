package org.eclipse.automergegit.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.IWorkbench;

public class SamplePreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public SamplePreferencePage() {
		super(GRID);
		setPreferenceStore(PlatformUI.getPreferenceStore());
	}
	
	public void createFieldEditors() {

		addField(new StringFieldEditor(PreferenceConstants.P_STRING, "Path to git project : ", getFieldEditorParent()));
		
		addField(new RadioGroupFieldEditor(PreferenceConstants.P_CHOICE, "", 1,
				new String[][] { { "Only cherry-pick", "0" }, { "Cherry-pick and push to upstream", "1" }
			}, getFieldEditorParent()));
			
	}

	public void init(IWorkbench arg0) {
		
	}
	
}