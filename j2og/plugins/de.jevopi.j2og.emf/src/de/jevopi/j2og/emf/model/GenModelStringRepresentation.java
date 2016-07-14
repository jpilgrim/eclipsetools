package de.jevopi.j2og.emf.model;

import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelSwitch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class GenModelStringRepresentation extends GenModelSwitch<String> {

	public static GenModelStringRepresentation INSTANCE = new GenModelStringRepresentation();

	@Override
	public String doSwitch(EObject eObject) {
		if (eObject == null) {
			return "";
		}
		if (eObject.eIsProxy()) {
			EcoreUtil.resolveAll(eObject);
		}
		return super.doSwitch(eObject);
	}

	@Override
	public String caseGenClassifier(GenClassifier object) {
		return object.getName();
	}

	@Override
	public String defaultCase(EObject object) {
		return object.toString();
	}
}
