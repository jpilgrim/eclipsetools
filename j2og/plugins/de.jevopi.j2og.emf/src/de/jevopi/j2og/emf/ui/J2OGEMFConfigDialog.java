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

import org.eclipse.jface.preference.IPreferenceStore;
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

}
