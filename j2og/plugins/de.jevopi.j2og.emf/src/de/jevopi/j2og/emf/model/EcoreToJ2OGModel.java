package de.jevopi.j2og.emf.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreSwitch;

import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Interface;
import de.jevopi.j2og.model.Member.Scope;
import de.jevopi.j2og.model.Model;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.model.TypeFactory;
import de.jevopi.j2og.model.TypeFactory.Kind;

public class EcoreToJ2OGModel extends EcoreSwitch<Object> {

	final Model model;

	public EcoreToJ2OGModel(Model model) {
		this.model = model;
	}

	@Override
	public Object caseEClass(EClass object) {
		Type type = getType(fqn(object), object.isInterface() ? Kind.INTERFACE : Kind.CLASS);

		if (object.isInterface()) {
		} else { // class
			Class clazz = (Class) type;
			clazz.setAbstract(object.isAbstract());
		}

		for (EClass eSuperType : object.getESuperTypes()) {
			Type superType = getType(fqn(eSuperType), eSuperType.isInterface() ? Kind.INTERFACE : Kind.CLASS);
			if (eSuperType.isInterface()) {
				type.addInterface((Interface) superType);
			} else {
				type.addSuperClass((Class) superType);
			}
		}

		for (EStructuralFeature eFeature : object.getEStructuralFeatures()) {
			type.addAttribute((Attribute) doSwitch(eFeature));
		}
		return type;
	}

	@Override
	public Object caseEAttribute(EAttribute object) {
		Attribute attrib = new Attribute(object.getName());
		attrib.type = getType(fqn(object.getEType()), null);
		attrib.setBounds(object.getLowerBound(), object.getUpperBound());
		attrib.setStatic(false);
		attrib.setScope(Scope.PUBLIC);
		return attrib;
	}

	@Override
	public Object caseEReference(EReference object) {
		Attribute attrib = new Attribute(object.getName());
		attrib.type = getType(fqn(object.getEType()), null);
		attrib.setBounds(object.getLowerBound(), object.getUpperBound());
		attrib.setStatic(false);
		attrib.setScope(Scope.PUBLIC);
		attrib.setContainment(object.isContainment());
		attrib.setDerived(object.isDerived());
		return attrib;
	}

	private Type getType(String fqn, Kind kind) {
		Type type = model.get(fqn);
		int p = fqn.lastIndexOf('.');
		String name, packageName;
		if (p == -1) {
			name = fqn;
			packageName = "";
		} else {
			name = fqn.substring(p + 1);
			packageName = fqn.substring(0, p);
		}
		if (kind != null) {
			switch (kind) {
			case CLASS:
				if (!(type instanceof Class)) {
					type = TypeFactory.create(name, packageName, kind);
					model.add(type);
				}
				break;
			case INTERFACE:
				if (!(type instanceof Interface)) {
					type = TypeFactory.create(name, packageName, kind);
					model.add(type);
				}
				break;
			case ENUM:
				if (!(type instanceof de.jevopi.j2og.model.Enum)) {
					type = TypeFactory.create(name, packageName, kind);
					model.add(type);
				}
				break;
			}
		}
		if (type == null) {
			type = TypeFactory.create(name, packageName, Kind.CLASS);
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
