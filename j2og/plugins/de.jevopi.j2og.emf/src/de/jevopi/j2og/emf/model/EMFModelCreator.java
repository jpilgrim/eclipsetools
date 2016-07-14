package de.jevopi.j2og.emf.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.part.FileEditorInput;

import de.jevopi.j2og.ModelException;
import de.jevopi.j2og.model.Model;

public class EMFModelCreator {

	Resource res;
	EPackage ePackage;

	public EMFModelCreator(IStructuredSelection selection) {
		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object next = it.next();
			if (next instanceof IFile) {
				if (loadPackage((IFile) next)) {
					return;
				}

			} else if (next instanceof FileEditorInput) {
				FileEditorInput fileEditorInput = (FileEditorInput) next;

				IFile file = fileEditorInput.getFile();
				if (loadPackage(file)) {
					return;
				}
			}
		}
	}

	private boolean loadPackage(IFile file) {
		try {
			String fp = file.getFullPath().toPortableString();
			URI platformURI = URI.createPlatformResourceURI(fp, true);

			ResourceSet rs = new ResourceSetImpl();

			rs.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(true));

			res = rs.getResource(platformURI, true);
			res.load(Collections.emptyMap());
			EcoreUtil.resolveAll(res);

			List<EObject> content = res.getContents();
			Optional<EObject> optPackage = content.stream().filter(e -> e instanceof EPackage).findFirst();
			if (!optPackage.isPresent()) {
				throw new ModelException("No content found");
			}
			ePackage = (EPackage) optPackage.get();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean selectionFound() {
		return ePackage != null;
	}

	public Model createModel(boolean recursive) {
		try {
			Model model = new Model();
			model.basePackageNames.add(ePackage.getName());
			EcoreToJ2OGModel transformer = new EcoreToJ2OGModel(model);
			for (EClassifier classifier : ePackage.getEClassifiers()) {
				transformer.doSwitch(classifier);
			}
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModelException("Error loading " + res.getURI(), e);
		}

	}

}
