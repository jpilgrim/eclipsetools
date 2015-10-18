package de.jevopi.j2og.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.model.Member.Scope;

/**
 * Rewrites model according to configuration, e.g., converts attributes to
 * associations, remove types etc.
 *
 * @author jpilgrim
 */
public class ModelRewriter {

	final static List<String> OBJECTMETHODS = Arrays.asList("clone", "finalize", "hashCode", "toString");
	private static final Set<String> primitiveTypes = new HashSet<String>(Arrays.asList("int", "long", "byte", "float",
			"double", "boolean", "char", "void"));

	private Config config;
	private Model model;
	private String[] prefix = new String[0];

	public ModelRewriter(Config config, Model model) {
		this.config = config;
		this.model = model;
	}

	public void rewrite() {

		rewriteCommonPackagePrefix();
		rewriteOmittedTypes();

		model.modelTypes.forEach(type -> {
			convertAttributesToAssoc(type);
			filterAttributes(type);
			filterOperations(type);
		});

	}

	protected void rewriteOmittedTypes() {
		model.modelTypes.removeIf(type -> isOmitted(type)
				|| !(model.basePackageNames.contains(type.packageName) || config.showContext));
	}

	protected void rewriteCommonPackagePrefix() {
		if (config.omitCommonPackagePrefix) {
			calcCommonPackagePrefix();
			model.allTypes.values().forEach(type -> {
				type.displayPackageName = getShortPackageName(type);
			});
		}
	}

	private void filterAttributes(Type type) {
		if (config.showAttributes) {
			type.attributes().removeIf(
					attribute -> (!showScope(attribute.getScope()) || (!config.showStaticAttributes && attribute
							.isStatic())));
		}
	}

	private void filterOperations(Type type) {
		if (config.showOperations) {
			type.operations().removeIf(operation -> ( //
					!showScope(operation.getScope()) //
							|| isOmitted(operation) //
					|| (!config.showStaticOperations && operation.isStatic())//
					));
		}

	}

	/**
	 * @param i_operation
	 * @return
	 * @since Aug 19, 2011
	 */
	private boolean isOmitted(Operation i_operation) {

		if (!config.showOverridings) {
			if (i_operation.sizeFormalParameters() == 0 && OBJECTMETHODS.contains(i_operation.name)) {
				return true;
			}

			if (isOverriding(i_operation, i_operation.getOwner())) {
				return true;
			}
		}
		if (!config.showGetterSetter) {
			if (isGetterOrSetter(i_operation)) {
				return true;
			}
		}

		return false;
	}

	private boolean isOverriding(Operation i_operation, Type i_owner) {
		Type superType = null;
		if (i_owner instanceof Class) {
			superType = ((Class) i_owner).getSuper();
		}
		if (superType != null) {
			if (superType.definesOperation(i_operation)) {
				return true;
			}
			if (isOverriding(i_operation, superType)) {
				return true;
			}
		}
		for (Type s : i_owner.interfaces()) {
			if (s.definesOperation(i_operation)) {
				return true;
			}
			if (isOverriding(i_operation, s)) {
				return true;
			}
		}
		return false;
	}

	private boolean isGetterOrSetter(Operation i_operation) {

		if (i_operation.getOwner() instanceof Class) {

			Class clazz = (Class) i_operation.getOwner();

			String name = i_operation.name;
			int pars = i_operation.sizeFormalParameters();

			if (pars == 0) {
				if (name.startsWith("is") && i_operation.type.name.equals("boolean")) {
					name = name.substring(2);
				} else if (name.startsWith("get")) {
					name = name.substring(3);
				} else {
					return false;
				}

				Attribute attribute = clazz.findAttributeByName(name);
				if (attribute != null) {
					return attribute.type == i_operation.type;
				}

			} else if (pars == 1 && name.startsWith("set")) {
				name = name.substring(3);
				Attribute attribute = clazz.findAttributeByName(name);
				if (attribute != null) {
					return attribute.type == i_operation.getFormalParameter(0).type;
				}
			}
		}
		return false;

	}

	private void convertAttributesToAssoc(Type type) {
		ArrayList<Attribute> attributesAsAssoc = new ArrayList<>();
		for (Attribute attribute : type.attributes()) {
			if (config.forceAllAssociations
					|| (showScope(attribute.getScope()) && (config.showStaticAttributes || !attribute.isStatic()))) {
				if (model.modelTypes.contains(attribute.type)) {
					model.assocs.add(attribute);
					attributesAsAssoc.add(attribute);
				}
			}
		}
		type.attributes.removeAll(attributesAsAssoc);

	}

	private boolean isOmitted(Type type) {
		if (type.packageName == null) {
			return true;
		}
		if (type.packageName.startsWith("java")) {
			return true;
		}
		if (type.packageName.equals("")) {
			return primitiveTypes.contains(type.name);
		}

		return false;

	}

	private String getShortPackageName(Type t) {
		if (prefix.length == 0) {
			return t.packageName;
		} else {
			String[] s = t.packageName.split("\\.");
			StringBuilder r = new StringBuilder();
			int l = Math.min(prefix.length, s.length);
			int i = 0;
			if (!(l < prefix.length && t.isContext())) {
				for (; i < l; i++) {
					if (!s[i].equals(prefix[i])) {
						break;
					}
				}
			}
			for (; i < s.length; i++) {
				r.append(s[i]);
				r.append('.');
			}
			return r.toString();
		}
	}

	private void calcCommonPackagePrefix() {
		if (model.basePackageNames.size() == 0) {
			prefix = new String[0];
			return;
		}
		prefix = model.basePackageNames.iterator().next().split("\\.");

		for (String p : model.basePackageNames) {
			String[] f = p.split("\\.");
			int l = Math.min(f.length, prefix.length);
			int i = 0;
			for (; i < l; i++) {
				if (!f[i].equals(prefix[i])) {
					break;
				}
			}
			if (i == 0) {
				prefix = new String[0];
				return;
			}
			if (i < prefix.length) {
				prefix = new String[i];
				System.arraycopy(f, 0, prefix, 0, i);
			}
		}
		if (prefix.length == 1 && prefix[0].equals("")) {
			prefix = new String[0];
		}
	}

	private boolean showScope(Scope i_scope) {
		switch (i_scope) {
		case PRIVATE:
			return config.showPrivate;
		case PACKAGE:
			return config.showPackage;
		case PROTECTED:
			return config.showProtected;
		case PUBLIC:
		default:
			return config.showPublic;
		}
	}
}
