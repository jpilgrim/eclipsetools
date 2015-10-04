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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.jdt.J2OGJDTPlugin;
import de.jevopi.j2og.ui.J2OGConfigDialog;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class J2OGJDTConfigDialog extends J2OGConfigDialog {

	protected J2OGJDTConfigDialog(Shell i_parentShell) {
		super(i_parentShell);
	}

	public static Config show(Shell i_shell) {
		J2OGJDTConfigDialog dialog = new J2OGJDTConfigDialog(i_shell);
		int code = dialog.open();
		if (code != 0) {
			return null;
		}
		return dialog.getConfig();
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		IPreferenceStore store = J2OGJDTPlugin.getDefault().getPreferenceStore();
		return store;
	}

}
