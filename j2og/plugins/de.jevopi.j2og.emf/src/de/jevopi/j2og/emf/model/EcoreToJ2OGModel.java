package de.jevopi.j2og.emf.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
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

	static Map<String, String> PRIMITIVETYPEMAP;
	private static String[] NAME_ECORE = {
			"EString", "EBoolean", "EInt", "EFloat", "EDouble", "EByte", "EShort", "ELong", "EChar"
	};
	private static String[] NAME_PRIM = {
			"String", "boolean", "int", "float", "double", "byte", "short", "long", "char"
	};

	static {
		PRIMITIVETYPEMAP = new HashMap<String, String>();
		for (int i=0; i<NAME_ECORE.length; i++) {
			PRIMITIVETYPEMAP.put(NAME_ECORE[i], NAME_PRIM[i]);
		}
	}


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
		EDataType atype = object.getEAttributeType();

		attrib.type = getType(fqn(object.getEType()), null);

		System.out.println(atype.getName());

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
					type.displayName = displayName(name,packageName);
					model.add(type);
				}
				break;
			case INTERFACE:
				if (!(type instanceof Interface)) {
					type = TypeFactory.create(name, packageName, kind);
					type.displayName = displayName(name,packageName);
					model.add(type);
				}
				break;
			case ENUM:
				if (!(type instanceof de.jevopi.j2og.model.Enum)) {
					type = TypeFactory.create(name, packageName, kind);
					type.displayName = displayName(name,packageName);
					model.add(type);
				}
				break;
			}
		}
		if (type == null) {
			type = TypeFactory.create(name, packageName, Kind.CLASS);
			type.displayName = displayName(name,packageName);
			model.add(type);
		}
		return type;
	}

	private String displayName(String name, String packageName) {
		if ("ecore".equals(packageName)) {
			String displayName = PRIMITIVETYPEMAP.get(name);
			if (displayName!=null) {
				return displayName;
			}
		}
		return name;
	}

	private String fqn(EClassifier eClassifier) {
		String fqn = (eClassifier.getEPackage() != null)
				? eClassifier.getEPackage().getName() + "." + eClassifier.getName() : eClassifier.getName();
		System.out.println(fqn);
		return fqn;
	}

}
