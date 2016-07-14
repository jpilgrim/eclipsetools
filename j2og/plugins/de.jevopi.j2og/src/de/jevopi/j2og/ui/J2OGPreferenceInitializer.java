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
		store.setDefault(SHOW_PRIVATE, false);
		store.setDefault(SHOW_PRIVATE, false);
		store.setDefault(SHOW_PACKAGE, false);
		store.setDefault(SHOW_PROTECTED, false);
		store.setDefault(SHOW_PUBLIC, true);

		store.setDefault(SHOW_GETTERSETTER, false);
		store.setDefault(SHOW_ATTRIBUTTYPES, true);
		store.setDefault(SHOW_PARAMETERTYPES, true);
		store.setDefault(SHOW_PARAMETERNAMES, false);

		store.setDefault(SHOW_ATTRIBUTES, true);
		store.setDefault(SHOW_OPERATIONS, true);
		store.setDefault(SHOW_OVERRIDINGS, false);

		store.setDefault(ENUMS_AS_ATTRIBUTES, true);

		store.setDefault(SHOW_STATICATTRIBUTES, false);
		store.setDefault(SHOW_STATICOPERATIONS, true);

		store.setDefault(CONVERTATTRIBUTESTOASSOCIATIONS, true);
		store.setDefault(FORCEALLASSOCIATIONS, true);
		store.setDefault(SHOW_DEPENDENCIES, true);

		store.setDefault(RECURSIVE, false);
		store.setDefault(OMIT_COMMON_PACKAGEPREFIX, true);
		store.setDefault(SHOW_CONTEXT, false);

		store.setDefault(SHOW_CONFIRMATION, true);

	}

}
