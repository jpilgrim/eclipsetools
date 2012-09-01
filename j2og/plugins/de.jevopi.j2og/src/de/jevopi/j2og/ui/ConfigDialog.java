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

import de.jevopi.j2og.Plugin;
import de.jevopi.j2og.config.Config;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class ConfigDialog extends Dialog {
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

	/**
	 * @return the config
	 */
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

		config.convertAttributesToAssociations =
			buttonAnalyzeAssociations.getSelection();
		config.forceAllAssociations = buttonForceAssociations.getSelection();

		config.recursive = buttonRecursive.getSelection();
		config.omitCommonPackagePrefix = buttonOmitCommonPackagePrefix.getSelection();
		config.showContext = buttonShowContext.getSelection();
		
		super.okPressed();
	}

	/**
	 * @param i_parentShell
	 */
	protected ConfigDialog(Shell i_parentShell) {
		super(i_parentShell);

		setShellStyle(getShellStyle() & SWT.SHEET);
	}

	/**
	 * @param i_shell
	 * @return
	 * @since Aug 19, 2011
	 */
	public static Config show(Shell i_shell) {
		ConfigDialog dialog = new ConfigDialog(i_shell);
		int code = dialog.open();
		if (code != 0) return null;
		return dialog.getConfig();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 * @since Aug 19, 2011
	 */
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

	/**
	 * 
	 * @since Aug 21, 2011
	 */
	private void initSettings() {
		IPreferenceStore store = Plugin.getDefault().getPreferenceStore();

		buttonAttributes.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_ATTRIBUTES));
		buttonOperations.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_OPERATIONS));
		buttonAttributeTypes.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_ATTRIBUTTYPES));
		buttonParameterNames.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_PARAMETERNAMES));
		buttonParameterTypes.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_PARAMETERTYPES));
		buttonPackage.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_PACKAGE));
		buttonPrivate.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_PRIVATE));
		buttonProtected.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_PROTECTED));
		buttonPublic.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_PUBLIC));
		buttonStaticAttributes.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_STATICATTRIBUTES));
		buttonStaticOperations.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_STATICOPERATIONS));

		buttonOverridingMethods.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_OVERRIDINGS));
		buttonDependencies.setSelection(store
			.getBoolean(PreferenceInitializer.SHOW_DEPENDENCIES));

		buttonAnalyzeAssociations.setSelection(store
			.getBoolean(PreferenceInitializer.CONVERTATTRIBUTESTOASSOCIATIONS));
		buttonForceAssociations.setSelection(store
			.getBoolean(PreferenceInitializer.FORCEALLASSOCIATIONS));
		
		buttonRecursive.setSelection(store
				.getBoolean(PreferenceInitializer.RECURSIVE));
		buttonOmitCommonPackagePrefix.setSelection(store
				.getBoolean(PreferenceInitializer.OMIT_COMMON_PACKAGEPREFIX));
		buttonShowContext.setSelection(store
				.getBoolean(PreferenceInitializer.SHOW_CONTEXT));

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

	/**
	 * @param i_panel
	 * @since Aug 19, 2011
	 */
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

	/**
	 * @param i_panel
	 * @since Aug 19, 2011
	 */
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
		buttonForceAssociations
			.setText("Ignore Scope Settings for Associations");
		buttonDependencies = new Button(panel, SWT.CHECK);
		buttonDependencies.setText("Show Depencencies");

	}

	/**
	 * @param i_panel
	 * @since Aug 19, 2011
	 */
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
