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

import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.CONVERTATTRIBUTESTOASSOCIATIONS;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.FORCEALLASSOCIATIONS;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.OMIT_COMMON_PACKAGEPREFIX;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.RECURSIVE;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_ATTRIBUTES;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_ATTRIBUTTYPES;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_CONFIRMATION;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_CONTEXT;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_DEPENDENCIES;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_OPERATIONS;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_OVERRIDINGS;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_PACKAGE;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_PARAMETERNAMES;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_PARAMETERTYPES;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_PRIVATE;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_PROTECTED;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_PUBLIC;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_STATICATTRIBUTES;
import static de.jevopi.j2og.ui.J2OGPreferenceInitializer.SHOW_STATICOPERATIONS;

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

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public abstract class J2OGConfigDialog extends Dialog {
	Config config = null;

	private Button buttonPrivate;
	private Button buttonPackage;
	private Button buttonProtected;
	private Button buttonPublic;
	private Button buttonGetterSetter;
	private Button buttonAttributeTypes;
	private Button buttonParameterNames;
	private Button buttonParameterTypes;
	private Button buttonAttributes;
	private Button buttonOperations;
	private Button buttonStaticAttributes;
	private Button buttonStaticOperations;

	private Button buttonForceAssociations;
	private Button buttonAnalyzeAssociations;

	private Button buttonDependencies;
	private Button buttonOverridingMethods;

	private Button buttonRecursive;
	private Button buttonOmitCommonPackagePrefix;
	private Button buttonShowContext;

	private boolean showConfirmation;

	protected J2OGConfigDialog(Shell i_parentShell) {
		super(i_parentShell);
		setShellStyle(getShellStyle() & SWT.SHEET);
	}

	protected abstract IPreferenceStore getPreferenceStore();

	public Config getConfig() {
		return config;
	}

	@Override
	protected void okPressed() {
		config = new Config();
		config.showAttributes = buttonAttributes.getSelection();
		config.showOperations = buttonOperations.getSelection();
		config.showAttributTypes = buttonAttributeTypes.getSelection();
		config.showParameterNames = buttonParameterNames.getSelection();
		config.showParameterTypes = buttonParameterTypes.getSelection();
		config.showPackage = buttonPackage.getSelection();
		config.showPrivate = buttonPrivate.getSelection();
		config.showProtected = buttonProtected.getSelection();
		config.showPublic = buttonPublic.getSelection();
		config.showStaticAttributes = buttonStaticAttributes.getSelection();
		config.showStaticOperations = buttonStaticOperations.getSelection();

		config.showOverridings = buttonOverridingMethods.getSelection();
		config.showDependencies = buttonDependencies.getSelection();

		config.convertAttributesToAssociations = buttonAnalyzeAssociations.getSelection();
		config.forceAllAssociations = buttonForceAssociations.getSelection();

		config.recursive = buttonRecursive.getSelection();
		config.omitCommonPackagePrefix = buttonOmitCommonPackagePrefix.getSelection();
		config.showContext = buttonShowContext.getSelection();

		config.showConfirmation = showConfirmation;

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

		createCompartmentPanel(panel);
		createScopePanel(panel);
		createPackagePanel(panel);
		createMemberPanel(panel);
		createAssocPanel(panel);

		applyDialogFont(panel);
		buttonBar = createButtonBar(panel);

		initSettings();

		return panel;
	}

	protected void initSettings() {
		IPreferenceStore store = getPreferenceStore();

		buttonAttributes.setSelection(store.getBoolean(SHOW_ATTRIBUTES));
		buttonOperations.setSelection(store.getBoolean(SHOW_OPERATIONS));
		buttonAttributeTypes.setSelection(store.getBoolean(SHOW_ATTRIBUTTYPES));
		buttonParameterNames.setSelection(store.getBoolean(SHOW_PARAMETERNAMES));
		buttonParameterTypes.setSelection(store.getBoolean(SHOW_PARAMETERTYPES));
		buttonPackage.setSelection(store.getBoolean(SHOW_PACKAGE));
		buttonPrivate.setSelection(store.getBoolean(SHOW_PRIVATE));
		buttonProtected.setSelection(store.getBoolean(SHOW_PROTECTED));
		buttonPublic.setSelection(store.getBoolean(SHOW_PUBLIC));
		buttonStaticAttributes.setSelection(store.getBoolean(SHOW_STATICATTRIBUTES));
		buttonStaticOperations.setSelection(store.getBoolean(SHOW_STATICOPERATIONS));

		buttonOverridingMethods.setSelection(store.getBoolean(SHOW_OVERRIDINGS));
		buttonDependencies.setSelection(store.getBoolean(SHOW_DEPENDENCIES));

		buttonAnalyzeAssociations.setSelection(store.getBoolean(CONVERTATTRIBUTESTOASSOCIATIONS));
		buttonForceAssociations.setSelection(store.getBoolean(FORCEALLASSOCIATIONS));

		buttonRecursive.setSelection(store.getBoolean(RECURSIVE));
		buttonOmitCommonPackagePrefix.setSelection(store.getBoolean(OMIT_COMMON_PACKAGEPREFIX));
		buttonShowContext.setSelection(store.getBoolean(SHOW_CONTEXT));

		showConfirmation = store.getBoolean(SHOW_CONFIRMATION);

	}

	/**
	 * @param i_panel
	 * @since Aug 19, 2011
	 */
	private void createScopePanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Scope");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonPrivate = new Button(panel, SWT.CHECK);
		buttonPrivate.setText("private");
		buttonPackage = new Button(panel, SWT.CHECK);
		buttonPackage.setText("package");
		buttonProtected = new Button(panel, SWT.CHECK);
		buttonProtected.setText("protected");
		buttonPublic = new Button(panel, SWT.CHECK);
		buttonPublic.setText("public");
		buttonPublic.setEnabled(false);

	}

	private void createPackagePanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Package");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonRecursive = new Button(panel, SWT.CHECK);
		buttonRecursive.setText("sub packages");
		buttonOmitCommonPackagePrefix = new Button(panel, SWT.CHECK);
		buttonOmitCommonPackagePrefix.setText("omit common prefix");
		buttonShowContext = new Button(panel, SWT.CHECK);
		buttonShowContext.setText("show context");

	}

	private void createCompartmentPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Compartements");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonAttributes = new Button(panel, SWT.CHECK);
		buttonAttributes.setText("Show Attributes");
		buttonOperations = new Button(panel, SWT.CHECK);
		buttonOperations.setText("Show Operations");

	}

	private void createAssocPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Associations and Dependencies");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonAnalyzeAssociations = new Button(panel, SWT.CHECK);
		buttonAnalyzeAssociations.setText("Convert Attributes");
		buttonForceAssociations = new Button(panel, SWT.CHECK);
		buttonForceAssociations.setText("Ignore Scope Settings for Associations");
		buttonDependencies = new Button(panel, SWT.CHECK);
		buttonDependencies.setText("Show Depencencies");

	}

	private void createMemberPanel(Composite parent) {
		Group panel = new Group(parent, SWT.NULL);
		panel.setText("Member Settings");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonGetterSetter = new Button(panel, SWT.CHECK);
		buttonGetterSetter.setText("getter/setter");
		buttonAttributeTypes = new Button(panel, SWT.CHECK);
		buttonAttributeTypes.setText("Attribute Types");
		buttonParameterNames = new Button(panel, SWT.CHECK);
		buttonParameterNames.setText("Parameter Names");
		buttonParameterTypes = new Button(panel, SWT.CHECK);
		buttonParameterTypes.setText("Parameter Types");

		buttonStaticAttributes = new Button(panel, SWT.CHECK);
		buttonStaticAttributes.setText("Static Attributes");
		buttonStaticOperations = new Button(panel, SWT.CHECK);
		buttonStaticOperations.setText("Static Operations");

		buttonOverridingMethods = new Button(panel, SWT.CHECK);
		buttonOverridingMethods.setText("Overriding Operations");

	}

}
