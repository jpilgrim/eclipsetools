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
package de.jevopi.j2og.jdt.ui;

import static de.jevopi.j2og.config.Config.CONTEXT_GRAY;
import static de.jevopi.j2og.config.Config.FORCEALLASSOCIATIONS;
import static de.jevopi.j2og.config.Config.OMIT_COMMON_PACKAGEPREFIX;
import static de.jevopi.j2og.config.Config.RECURSIVE;
import static de.jevopi.j2og.config.Config.SHOW_ATTRIBUTES;
import static de.jevopi.j2og.config.Config.SHOW_ATTRIBUTTYPES;
import static de.jevopi.j2og.config.Config.SHOW_CONFIRMATION;
import static de.jevopi.j2og.config.Config.SHOW_CONTEXT;
import static de.jevopi.j2og.config.Config.SHOW_DEPENDENCIES;
import static de.jevopi.j2og.config.Config.SHOW_GETTERSETTER;
import static de.jevopi.j2og.config.Config.SHOW_OPERATIONS;
import static de.jevopi.j2og.config.Config.SHOW_OVERRIDINGS;
import static de.jevopi.j2og.config.Config.SHOW_PACKAGE;
import static de.jevopi.j2og.config.Config.SHOW_PACKAGE_NAME_CONTEXT;
import static de.jevopi.j2og.config.Config.SHOW_PARAMETERNAMES;
import static de.jevopi.j2og.config.Config.SHOW_PARAMETERTYPES;
import static de.jevopi.j2og.config.Config.SHOW_PRIVATE;
import static de.jevopi.j2og.config.Config.SHOW_PROTECTED;
import static de.jevopi.j2og.config.Config.SHOW_STATICATTRIBUTES;
import static de.jevopi.j2og.config.Config.SHOW_STATICOPERATIONS;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.jevopi.j2og.jdt.J2OGJDTPlugin;
import de.jevopi.j2og.ui.J2OGFieldEditorPreferencePage;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class J2OGJDTPreferencePage extends J2OGFieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 *
	 */
	public J2OGJDTPreferencePage() {
		setPreferenceStore(J2OGJDTPlugin.getDefault().getPreferenceStore());
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

		addBooleanField(SHOW_ATTRIBUTES, "show attribute compartement");
		addBooleanField(SHOW_OPERATIONS, "show operation compartement");

		addBooleanField(SHOW_PRIVATE, "show private members");
		addBooleanField(SHOW_PACKAGE, "show package members");
		addBooleanField(SHOW_PROTECTED, "show protected members");
		// addBooleanField(PreferenceInitializer.SHOW_PUBLIC,
		// "public",
		// getFieldEditorParent()));

		addBooleanField(RECURSIVE, "sub packages");
		addBooleanField(OMIT_COMMON_PACKAGEPREFIX, "omit common package prefix");
		addBooleanField(SHOW_PACKAGE, "show package names");

		addBooleanField(SHOW_GETTERSETTER, "show getter/setter");
		addBooleanField(SHOW_OVERRIDINGS, "show overriding methods");

		addBooleanField(SHOW_ATTRIBUTTYPES, "show attribute types");
		addBooleanField(SHOW_PARAMETERTYPES, "show parameter types");
		addBooleanField(SHOW_PARAMETERNAMES, "show parameter names");

		addBooleanField(SHOW_STATICATTRIBUTES, "show static attributes");
		addBooleanField(SHOW_STATICOPERATIONS, "show static operations");

		// addBooleanField(
		// PreferenceInitializer.CONVERTATTRIBUTESTOASSOCIATIONS,
		// "create associations",
		// getFieldEditorParent()));
		addBooleanField(FORCEALLASSOCIATIONS, "ignore scopes for associations");
		addBooleanField(SHOW_DEPENDENCIES, "show dependencies");

		addBooleanField(SHOW_CONTEXT, "show context");
		addBooleanField(SHOW_PACKAGE_NAME_CONTEXT, "show package names of context");
		addBooleanField(CONTEXT_GRAY, "render context in gray");

		addBooleanField(SHOW_CONFIRMATION, "show confirmation");

	}

}
