package de.jevopi.j2og.emf.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
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

	private final static Map<String, String> PRIMITIVETYPEMAP;
	public final static String[] NAME_ECORE = { "EString", "EBoolean", "EInt", "EFloat", "EDouble", "EByte", "EShort",
			"ELong", "EChar" };
	public final static String[] NAME_PRIM = { "String", "boolean", "int", "float", "double", "byte", "short", "long",
			"char" };

	static {
		PRIMITIVETYPEMAP = new HashMap<String, String>();
		for (int i = 0; i < NAME_ECORE.length; i++) {
			PRIMITIVETYPEMAP.put(NAME_ECORE[i], NAME_PRIM[i]);
		}
	}

	final Model model;

	public EcoreToJ2OGModel(Model model) {
		this.model = model;
	}

	@Override
	public Object caseEClass(EClass object) {
		Type type = getType(object);

		if (object.isInterface()) {
		} else { // class
			Class clazz = (Class) type;
			clazz.setAbstract(object.isAbstract());
		}

		for (EClass eSuperType : object.getESuperTypes()) {
			Type superType = getType(eSuperType);
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
	public Object caseEEnum(EEnum object) {
		Type type = getType(object);
		return type;
	}

	@Override
	public Object caseEAttribute(EAttribute object) {
		Attribute attrib = new Attribute(object.getName());

		attrib.type = getType(object.getEType());
		attrib.setBounds(object.getLowerBound(), object.getUpperBound());
		attrib.setStatic(false);
		attrib.setScope(Scope.PUBLIC);
		return attrib;
	}

	@Override
	public Object caseEReference(EReference object) {
		Attribute attrib = new Attribute(object.getName());
		attrib.type = getType(object.getEType());
		attrib.setBounds(object.getLowerBound(), object.getUpperBound());
		attrib.setStatic(false);
		attrib.setScope(Scope.PUBLIC);
		attrib.setContainment(object.isContainment());
		attrib.setDerived(object.isDerived());
		return attrib;
	}

	private Type getType(EClassifier eClassifier) {
		String fqn = fqn(eClassifier);
		Type type = model.get(fqn);
		if (type != null) {
			return type;
		}

		Kind kind = null;
		if (eClassifier instanceof EClass) {
			kind = ((EClass) eClassifier).isInterface() ? Kind.INTERFACE : Kind.CLASS;
		} else if (eClassifier instanceof EEnum) {
			kind = Kind.ENUM;
		} else {
			kind = Kind.CLASS;
		}
		String name = eClassifier.getName();
		String packageName = "";
		if (eClassifier.getEPackage() != null) {
			packageName = eClassifier.getEPackage().getName();
		}
		type = TypeFactory.create(name, packageName, kind);
		type.displayName = displayName(name, packageName);

		type.setContext(! model.isBasePackage(packageName));

		model.add(type);
		return type;
	}

	private String displayName(String name, String packageName) {
		if ("ecore".equals(packageName)) {
			String displayName = PRIMITIVETYPEMAP.get(name);
			if (displayName != null) {
				return displayName;
			}
		}
		return name;
	}

	private String fqn(EClassifier eClassifier) {
		String fqn = (eClassifier.getEPackage() != null)
				? eClassifier.getEPackage().getName() + "." + eClassifier.getName() : eClassifier.getName();
		return fqn;
	}

}
