package de.jevopi.j2og.emf.model;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.part.FileEditorInput;

import de.jevopi.j2og.ModelException;
import de.jevopi.j2og.model.Model;

public class EMFModelCreator {

	Resource res;

	public EMFModelCreator(IStructuredSelection selection) {
		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object next = it.next();
			System.out.println(next);

			if (next instanceof FileEditorInput) {
				FileEditorInput fileEditorInput = (FileEditorInput) next;
				String extension = fileEditorInput.getFile().getFileExtension();
				Object factoryOrDescr = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get(extension);
				if (factoryOrDescr != null) {
					URI uri = URI.createFileURI(fileEditorInput.getPath().toString());
					ResourceSet rs = new ResourceSetImpl();
					res = rs.createResource(uri);
				}
			}
		}
	}

	public boolean selectionFound() {
		return res != null;
	}

	public Model createModel(boolean recursive) {
		try {
			res.load(Collections.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ModelException("Error loading " + res.getURI(), e);
		}
		try {
			List<EObject> content = res.getContents();
			Optional<EObject> optPackage = content.stream().filter(e -> e instanceof EPackage).findFirst();
			if (!optPackage.isPresent()) {
				throw new ModelException("No content found");
			}
			EPackage ePackage = (EPackage) optPackage.get();
			Model model = new Model();
			model.basePackageNames.add(ePackage.getName());
			EcoreToJ2OGModel transformer = new EcoreToJ2OGModel(model);
			for (EClassifier classifier : ePackage.getEClassifiers()) {
				transformer.doSwitch(classifier);
			}
			return model;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new ModelException("Error loading " + res.getURI(), e);
		}

	}

}
