/*******************************************************************************
 * Copyright (c) 2015 Jens von Pilgrim
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *    Jens von Pilgrim - initial API and implementation
 ******************************************************************************/
package de.jevopi.j2og.emf.ui;

import static de.jevopi.j2og.config.Config.CONTEXT_GRAY;
import static de.jevopi.j2og.config.Config.ENUMS_AS_ATTRIBUTES;
import static de.jevopi.j2og.config.Config.OMIT_COMMON_PACKAGEPREFIX;
import static de.jevopi.j2og.config.Config.SHOW_ATTRIBUTTYPES;
import static de.jevopi.j2og.config.Config.SHOW_CONTEXT;
import static de.jevopi.j2og.config.Config.SHOW_DEPENDENCIES;
import static de.jevopi.j2og.config.Config.SHOW_OVERRIDINGS;
import static de.jevopi.j2og.config.Config.SHOW_PACKAGE_NAME;
import static de.jevopi.j2og.config.Config.SHOW_PACKAGE_NAME_CONTEXT;
import static de.jevopi.j2og.config.Config.SHOW_PARAMETERNAMES;
import static de.jevopi.j2og.config.Config.SHOW_PARAMETERTYPES;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.emf.J2OGEMFPlugin;
import de.jevopi.j2og.ui.J2OGConfigDialog;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class J2OGEMFConfigDialog extends J2OGConfigDialog {

	protected J2OGEMFConfigDialog(Shell i_parentShell) {
		super(i_parentShell);
	}

	public static Config show(Shell i_shell) {
		J2OGEMFConfigDialog dialog = new J2OGEMFConfigDialog(i_shell);
		int code = dialog.open();
		if (code != 0) {
			return null;
		}
		return dialog.getConfig();
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		IPreferenceStore store = J2OGEMFPlugin.getDefault().getPreferenceStore();
		return store;
	}

	@Override
	protected void createPanels(Composite parent) {
		createCompartmentPanel(parent);
		// everything is public, omit: createScopePanel(parent);
		createPackagePanel(parent);
		createMemberPanel(parent);
		createAssocPanel(parent);
	}

	protected void createPackagePanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Package");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, SHOW_PACKAGE_NAME, "show package name");
		createSelectButton(panel, OMIT_COMMON_PACKAGEPREFIX, "omit common prefix");
		createSelectButton(panel, SHOW_CONTEXT, "show context");
		createSelectButton(panel, CONTEXT_GRAY, "render context gray");
		createSelectButton(panel, SHOW_PACKAGE_NAME_CONTEXT, "show package name of context");
	}

	@Override
	protected void createAssocPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Associations and Dependencies");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, SHOW_DEPENDENCIES, "show dependencies");
		createSelectButton(panel, ENUMS_AS_ATTRIBUTES, "show enums as attributes");
	}

	@Override
	protected void createMemberPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Member Settings");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, SHOW_ATTRIBUTTYPES, "attribute types");
		createSelectButton(panel, SHOW_PARAMETERNAMES, "parameter names");
		createSelectButton(panel, SHOW_PARAMETERTYPES, "parameter types");
		createSelectButton(panel, SHOW_OVERRIDINGS, "overriding operations");

	}

}
