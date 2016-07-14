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

import org.eclipse.jface.preference.IPreferenceStore;

import de.jevopi.j2og.emf.J2OGEMFPlugin;
import de.jevopi.j2og.ui.J2OGPreferenceInitializer;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class J2OGEMFPreferenceInitializer extends J2OGPreferenceInitializer {
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = J2OGEMFPlugin.getDefault().getPreferenceStore();
		setDefaults(store);
	}
}
