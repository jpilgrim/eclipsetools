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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class J2OGPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
		Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		// text.setForeground(JFaceColors.getBannerForeground(text.getDisplay()));
		// text.setBackground(JFaceColors.getBannerBackground(text.getDisplay()));
		text.setText("Preferences for creating OmniGraffle Diagrams,\nconfigure for each language separately.");
		return text;
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
