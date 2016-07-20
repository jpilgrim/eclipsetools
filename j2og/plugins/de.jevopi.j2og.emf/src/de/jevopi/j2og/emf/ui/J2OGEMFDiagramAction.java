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
import static de.jevopi.j2og.config.Config.*;
import de.jevopi.j2og.emf.model.EMFModelCreator;
import de.jevopi.j2og.graphics.GraphDocument;
import de.jevopi.j2og.model.Model;
import de.jevopi.j2og.model.ModelRewriter;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.umlgraphics.GraffleCreator;

public class J2OGEMFDiagramAction implements IObjectActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		EMFModelCreator creator = new EMFModelCreator((IStructuredSelection) selection);

		if (!creator.selectionFound()) {
			return;
		}

		Config config = J2OGEMFConfigDialog.show(getShell());
		if (config == null) {
			return;
		}
		Model model;
		try {
			model = creator.createModel(config.is(RECURSIVE));
		} catch (ModelException ex) {
			MessageDialog.openError(getShell(), "Error loading model", ex.toString());
			return;
		}

		final boolean filterEcore = ! model.basePackageNames.contains("ecore");
		ModelRewriter modelRewriter = new ModelRewriter(config, model) {
			@Override
			protected boolean isOmitted(Type type) {
				if (super.isOmitted(type)) {
					return true;
				}
				return filterEcore && "ecore".equals(type.packageName);
			}
		};
		modelRewriter.rewrite();

		try {
			GraffleCreator graffleCreator = new GraffleCreator(config, model);

			GraphDocument graphDocument = new GraphDocument();
			graphDocument.graphicsList.addAll(graffleCreator.getGraphics());
			String fileName = null;
			if (! model.basePackageNames.isEmpty()) {
				fileName = model.basePackageNames.iterator().next();
				int p = fileName.lastIndexOf('.');
				if (p>=0) {
					fileName = fileName.substring(0, p-1);
				}
			}
			if (fileName==null || fileName.isEmpty()) {
				fileName = "drawing";
			}


			String destFile = saveTo(fileName);
			if (destFile == null) {
				return;
			}
			File f = new File(destFile);
			if (f.exists()) {
				if (!MessageDialog.openQuestion(getShell(), "Replace?", "File\n\n" + f.toString()
						+ "\n\nalready exists.\nReplace the file?")) {
					return;
				}
			}
			graphDocument.write(f);
			if (config.is(SHOW_CONFIRMATION)) {
				MessageDialog
						.openInformation(
								getShell(),
								"OmniGraffle Diagram Created",
								"OmniGraffle drawing\n\n"
										+ f.toString()
										+ "\n\nsuccessfully created. "
										+ "Diagram is not layouted, use OmniGraffles auto layout feature or "
										+ "manually layout diagram."
										+ "\n\n"
										+ "If you like this little tool, visit http://jevopisdeveloperblog.blogspot.com, "
										+ "drop me a note, flattr me or donate to support the development of this tool. "
										+ "Source code is available at GitHub: Feel free to improve it and create a pull request!");
			}

		} catch (Exception ex) {
			MessageDialog.openError(getShell(), "Error creating OmniGraffle Diagram", ex.toString());
			return;
		}

	}

	String saveTo(String name) {
		FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		String[] filterNames = new String[] { "OmniGraffle Files" };
		String[] filterExtensions = new String[] { "*.graffle" };
		dialog.setFilterNames(filterNames);
		dialog.setFilterExtensions(filterExtensions);
		dialog.setFileName(name+".graffle");
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
