package de.jevopi.j2og.emf.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.EcoreSwitch;

import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Member.Scope;
import de.jevopi.j2og.model.Model;
import de.jevopi.j2og.model.Type;

public class EcoreToJ2OGModel extends EcoreSwitch<Object> {

	final Model model;

	public EcoreToJ2OGModel(Model model) {
		this.model = model;
	}

	@Override
	public Object caseEClass(EClass object) {
		Type type = model.get(fqn(object));
		if (!(type instanceof Class)) {
			type = new Class(object.getName(), object.getEPackage().getName());
			model.add(type);
		}
		Class clazz = (Class) type;
		clazz.setAbstract(object.isAbstract());

		for (EAttribute eAttrib : object.getEAttributes()) {
			clazz.addAttribute((Attribute) doSwitch(eAttrib));
		}
		return clazz;
	}

	@Override
	public Object caseEAttribute(EAttribute object) {
		Attribute attrib = new Attribute(object.getName());
		attrib.type = getType(fqn(object.getEType()));
		attrib.setBounds(object.getLowerBound(), object.getUpperBound());
		attrib.setStatic(false);
		attrib.setScope(Scope.PUBLIC);
		return attrib;
	}

	private Type getType(String fqn) {
		Type type = model.get(fqn);
		if (type == null) {
			int p = fqn.lastIndexOf('.');
			if (p == -1) {
				p = fqn.length();
			}
			type = new Class(fqn.substring(p), fqn.substring(0, p));
			model.add(type);
		}
		return type;
	}

	private String fqn(EClassifier eClassifier) {
		if (eClassifier.getEPackage() != null) {
			return eClassifier.getEPackage().getName() + "." + eClassifier.getName();
		}
		return eClassifier.getName();
	}

}
