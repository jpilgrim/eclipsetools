package de.jevopi.j2og.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

import de.jevopi.j2og.config.ConfigEntry;

public abstract class J2OGFieldEditorPreferencePage extends FieldEditorPreferencePage {

	public void addBooleanField(ConfigEntry configEntry, String label) {
		addField(new BooleanFieldEditor(configEntry.name, label, getFieldEditorParent()));
	}

}
