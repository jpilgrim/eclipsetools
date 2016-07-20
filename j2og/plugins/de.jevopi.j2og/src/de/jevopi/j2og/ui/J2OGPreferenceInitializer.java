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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import static de.jevopi.j2og.config.Config.*;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public abstract class J2OGPreferenceInitializer extends AbstractPreferenceInitializer {

	// @Override
	// public void initializeDefaultPreferences() {
	// IPreferenceStore store = Plugin.getDefault().getPreferenceStore();
	// setDefaults(store);
	// }

	protected void setDefaults(IPreferenceStore store) {
		store.setDefault(SHOW_PRIVATE.name, SHOW_PRIVATE.defaultValue);
		store.setDefault(SHOW_PRIVATE.name, SHOW_PRIVATE.defaultValue);
		store.setDefault(SHOW_PACKAGE.name, SHOW_PACKAGE.defaultValue);
		store.setDefault(SHOW_PROTECTED.name, SHOW_PROTECTED.defaultValue);
		store.setDefault(SHOW_PUBLIC.name, SHOW_PUBLIC.defaultValue);

		store.setDefault(SHOW_GETTERSETTER.name, SHOW_GETTERSETTER.defaultValue);
		store.setDefault(SHOW_ATTRIBUTTYPES.name, SHOW_ATTRIBUTTYPES.defaultValue);
		store.setDefault(SHOW_PARAMETERTYPES.name, SHOW_PARAMETERTYPES.defaultValue);
		store.setDefault(SHOW_PARAMETERNAMES.name, SHOW_PARAMETERNAMES.defaultValue);

		store.setDefault(SHOW_ATTRIBUTES.name, SHOW_ATTRIBUTES.defaultValue);
		store.setDefault(SHOW_OPERATIONS.name, SHOW_OPERATIONS.defaultValue);
		store.setDefault(SHOW_OVERRIDINGS.name, SHOW_OVERRIDINGS.defaultValue);

		store.setDefault(ENUMS_AS_ATTRIBUTES.name, ENUMS_AS_ATTRIBUTES.defaultValue);

		store.setDefault(SHOW_STATICATTRIBUTES.name, SHOW_STATICATTRIBUTES.defaultValue);
		store.setDefault(SHOW_STATICOPERATIONS.name, SHOW_STATICOPERATIONS.defaultValue);

		store.setDefault(CONVERTATTRIBUTESTOASSOCIATIONS.name, CONVERTATTRIBUTESTOASSOCIATIONS.defaultValue);
		store.setDefault(FORCEALLASSOCIATIONS.name, FORCEALLASSOCIATIONS.defaultValue);
		store.setDefault(SHOW_DEPENDENCIES.name, SHOW_DEPENDENCIES.defaultValue);

		store.setDefault(RECURSIVE.name, RECURSIVE.defaultValue);
		store.setDefault(OMIT_COMMON_PACKAGEPREFIX.name, OMIT_COMMON_PACKAGEPREFIX.defaultValue);
		store.setDefault(SHOW_PACKAGE_NAME.name, SHOW_PACKAGE_NAME.defaultValue);
		store.setDefault(SHOW_PACKAGE_NAME_CONTEXT.name, SHOW_PACKAGE_NAME_CONTEXT.defaultValue);
		store.setDefault(CONTEXT_GRAY.name, CONTEXT_GRAY.defaultValue);
		store.setDefault(SHOW_CONTEXT.name, SHOW_CONTEXT.defaultValue);

		store.setDefault(SHOW_CONFIRMATION.name, SHOW_CONFIRMATION.defaultValue);

	}

}
