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
package de.jevopi.j2og.ui;

import static de.jevopi.j2og.config.Config.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.config.ConfigEntry;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public abstract class J2OGConfigDialog extends Dialog {
	Config config = null;

	protected final Map<ConfigEntry, Button> selectButtons;


	private boolean showConfirmation;

	protected J2OGConfigDialog(Shell i_parentShell) {
		super(i_parentShell);
		setShellStyle(getShellStyle() & SWT.SHEET);
		selectButtons = new HashMap<>();
	}

	protected abstract IPreferenceStore getPreferenceStore();

	public Config getConfig() {
		return config;
	}

	@Override
	protected void okPressed() {
		config = new Config();

		for (Entry<ConfigEntry, Button> keyButton: selectButtons.entrySet()) {
			config.set(keyButton.getKey(), keyButton.getValue().getSelection());
		}

		config.set(SHOW_CONFIRMATION, showConfirmation);

		super.okPressed();
	}

	@Override
	protected Control createContents(Composite parent) {

		Composite panel = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createPanels(panel);

		applyDialogFont(panel);
		buttonBar = createButtonBar(panel);

		initSettings();

		return panel;
	}

	protected void createPanels(Composite parent) {
		createCompartmentPanel(parent);
		createScopePanel(parent);
		createPackagePanel(parent);
		createMemberPanel(parent);
		createAssocPanel(parent);
	}

	protected void initSettings() {
		IPreferenceStore store = getPreferenceStore();
		for(Entry<ConfigEntry, Button> keyButton: selectButtons.entrySet()) {
			keyButton.getValue().setSelection(store.getBoolean(keyButton.getKey().name));
		}
		showConfirmation = store.getBoolean(SHOW_CONFIRMATION.name);

	}

	/**
	 * @param i_panel
	 * @since Aug 19, 2011
	 */
	protected void createScopePanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Scope");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, SHOW_PRIVATE, "private");
		createSelectButton(panel, SHOW_PACKAGE, "package");
		createSelectButton(panel, SHOW_PROTECTED, "protected");
		createSelectButton(panel, SHOW_PUBLIC, "public").setEnabled(false);
	}

	protected Button createSelectButton(Composite parent, ConfigEntry configEntry, String label) {
		if (selectButtons.containsKey(configEntry)) {
			throw new IllegalStateException("Button for " + configEntry + " already defined.");
		}
		Button button = new Button(parent, SWT.CHECK);
		selectButtons.put(configEntry, button);

		button.setText(label);
		return button;
	}

	protected void createPackagePanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Package");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, RECURSIVE, "sub packages");
		createSelectButton(panel, SHOW_PACKAGE_NAME, "show package name");
		createSelectButton(panel, OMIT_COMMON_PACKAGEPREFIX, "omit common prefix");
		createSelectButton(panel, SHOW_CONTEXT, "show context");
		createSelectButton(panel, CONTEXT_GRAY, "render context gray");
		createSelectButton(panel, SHOW_PACKAGE_NAME_CONTEXT, "show package name of context");
	}

	protected void createCompartmentPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Compartements");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, SHOW_ATTRIBUTES, "show attributes");
		createSelectButton(panel, SHOW_OPERATIONS, "show operations");


	}

	protected void createAssocPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Associations and Dependencies");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, CONVERTATTRIBUTESTOASSOCIATIONS, "convert attributes");
		createSelectButton(panel, FORCEALLASSOCIATIONS, "ignore scope for associations");
		createSelectButton(panel, SHOW_DEPENDENCIES, "show dependencies");
		createSelectButton(panel, ENUMS_AS_ATTRIBUTES, "show enums as attributes");
	}

	protected void createMemberPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Member Settings");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSelectButton(panel, SHOW_GETTERSETTER, "getter/setter");
		createSelectButton(panel, SHOW_ATTRIBUTTYPES, "attribute types");
		createSelectButton(panel, SHOW_PARAMETERNAMES, "parameter names");
		createSelectButton(panel, SHOW_PARAMETERTYPES, "parameter types");

		createSelectButton(panel, SHOW_STATICATTRIBUTES, "static attributes");
		createSelectButton(panel, SHOW_STATICOPERATIONS, "static operations");

		createSelectButton(panel, SHOW_OVERRIDINGS, "overriding operations");

	}

}
