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

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.jevopi.j2og.ModelException;
import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.graphics.GraphDocument;
import de.jevopi.j2og.jdt.model.JDTModelCreator;
import de.jevopi.j2og.model.Model;
import de.jevopi.j2og.model.ModelRewriter;
import de.jevopi.j2og.umlgraphics.GraffleCreator;

public class J2OGJDTDiagramAction implements IObjectActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		JDTModelCreator creator = new JDTModelCreator((IStructuredSelection) selection);

		if (!creator.selectionFound()) {
			return;
		}

		Config config = J2OGJDTConfigDialog.show(getShell());
		if (config == null) {
			return;
		}
		Model model;
		try {
			model = creator.createModel(config.recursive);
		} catch (ModelException ex) {
			MessageDialog.openError(getShell(), "Error loading model", ex.toString());
			return;
		}

		ModelRewriter modelRewriter = new ModelRewriter(config, model);
		modelRewriter.rewrite();

		try {
			GraffleCreator graffleCreator = new GraffleCreator(config, model);

			GraphDocument graphDocument = new GraphDocument();
			graphDocument.graphicsList.addAll(graffleCreator.getGraphics());
			String destFile = saveTo();
			File f = new File(destFile);
			graphDocument.write(f);

		} catch (Exception ex) {
			MessageDialog.openError(getShell(), "Error creating OmniGraffle Diagram", ex.toString());
			return;
		}

		MessageDialog.openInformation(getShell(), "OmniGraffle Diagram Created",
				"New classes should be visible in your OmniGraffle drawing.\n\n" + "If you like this little tool, "
						+ "visit http://jevopisdeveloperblog.blogspot.com and "
						+ "drop me a note, flattr me or donate to support the development of this tool.\n"
						+ "Software development requires time, and time is money ;-)");

	}

	String saveTo() {
		FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		String[] filterNames = new String[] { "OmniGraffle Files" };
		String[] filterExtensions = new String[] { "*.graffle" };
		dialog.setFilterNames(filterNames);
		dialog.setFilterExtensions(filterExtensions);
		dialog.setFileName("drawing.graffle");
		return dialog.open();

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	private Shell getShell() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		return window.getShell();
	}
}
