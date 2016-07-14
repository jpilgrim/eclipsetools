/*******************************************************************************
 * Copyright (c) 2012 Jens von Pilgrim
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *    Jens von Pilgrim - initial API and implementation
 ******************************************************************************/
package de.jevopi.j2og.emf.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.jevopi.j2og.emf.J2OGEMFPlugin;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class J2OGEMFPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 *
	 */
	public J2OGEMFPreferencePage() {
		setPreferenceStore(J2OGEMFPlugin.getDefault().getPreferenceStore());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 * @since Aug 21, 2011
	 */
	@Override
	public void init(IWorkbench i_workbench) {
		// TODO Implement method JavaToOmniGrafflePreferencePage#init

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 * @since Aug 21, 2011
	 */
	@Override
	protected void createFieldEditors() {

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_ATTRIBUTES, "show attribute compartement",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_OPERATIONS, "show operation compartement",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_PRIVATE, "show private members",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_PACKAGE, "show package members",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_PROTECTED, "show protected members",
				getFieldEditorParent()));
		// addField(new BooleanFieldEditor(PreferenceInitializer.SHOW_PUBLIC,
		// "public",
		// getFieldEditorParent()));

		// addField(new
		// BooleanFieldEditor(J2OGEMFPreferenceInitializer.RECURSIVE,
		// "sub packages", getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.OMIT_COMMON_PACKAGEPREFIX,
				"omit common package prefix", getFieldEditorParent()));

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_GETTERSETTER, "show getter/setter",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_OVERRIDINGS, "show overriding methods",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_ATTRIBUTTYPES, "show attribute types",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_PARAMETERTYPES, "show parameter types",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_PARAMETERNAMES, "show parameter names",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_STATICATTRIBUTES, "show static attributes",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_STATICOPERATIONS, "show static operations",
				getFieldEditorParent()));

		// addField(new BooleanFieldEditor(
		// PreferenceInitializer.CONVERTATTRIBUTESTOASSOCIATIONS,
		// "create associations",
		// getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.FORCEALLASSOCIATIONS,
				"ignore scopes for associations", getFieldEditorParent()));
		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_DEPENDENCIES, "show dependencies",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_CONTEXT, "show context",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(J2OGEMFPreferenceInitializer.SHOW_CONFIRMATION, "show confirmation",
				getFieldEditorParent()));

	}

}
