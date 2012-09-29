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
package de.jevopi.j2og.okas;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Interface;
import de.jevopi.j2og.model.Member.Scope;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class OGAppleScriptCreator {

	final static String POS_MARKER = "--ELEMENTS_ARE_CREATED_HERE--";
	final static String TEMPLATE;
	final static int POS;

	static {
		InputStream is = OGAppleScriptCreator.class
				.getClassLoader()
				.getResourceAsStream("de/jevopi/j2og/okas/template.applescript");
		if (is == null)
			throw new NullPointerException("template not found");
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(is, "MacRoman");
		} catch (UnsupportedEncodingException ex1) {
			System.out.println("MacRoman not supported, use default encoding");
			reader = new InputStreamReader(is);
		}
		char[] buffer = new char[512];
		StringBuffer strb = new StringBuffer();
		try {
			int l;
			while ((l = reader.read(buffer)) > 0) {
				strb.append(buffer, 0, l);
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		TEMPLATE = strb.toString();
		// TEMPLATE = TemplateProvider.TEMPLATE;
		POS = TEMPLATE.indexOf(POS_MARKER);
	}

	Set<String> basePackages;

	StringBuffer out;

	Map<Type, String> typeToShapeMap = new HashMap<Type, String>();
	int shapeCounter = 1;

	List<Attribute> assocs = new ArrayList<Attribute>();
	private Config config;
	private Set<String> connections = new HashSet<String>();
	String[] prefix = new String[0];

	/**
	 * 
	 */
	public OGAppleScriptCreator() {

	}

	private String packagesString() {
		if (basePackages.size() == 0) {
			return "no packages selected";
		}
		if (basePackages.size() == 1) {
			return basePackages.iterator().next();
		}
		return basePackages.iterator().next() + " and others";

	}

	private void calcCommonPackagePrefix() {
		if (basePackages.size() == 0) {
			prefix = new String[0];
			return;
		}
		prefix = basePackages.iterator().next().split("\\.");

		for (String p : basePackages) {
			String[] f = p.split("\\.");
			int l = Math.min(f.length, prefix.length);
			int i = 0;
			for (; i < l; i++) {
				if (!f[i].equals(prefix[i]))
					break;
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

	private String getPackageInNameCompartement(Type t) {
		if (basePackages.size() == 0)
			return "";
		if (prefix.length == 0) {
			return t.getPackageName();
		} else {
			String[] s = t.getPackageName().split("\\.");
			StringBuilder r = new StringBuilder();
			int l = Math.min(prefix.length, s.length);
			int i = 0;
			if (!(l < prefix.length && t.isContext())) {
				for (; i < l; i++) {
					if (!s[i].equals(prefix[i]))
						break;
				}
			}
			for (; i < s.length; i++) {
				r.append(s[i]);
				r.append('.');
			}
			return r.toString();
		}
	}

	public String toAppleScript(Set<String> i_basePackages, Config i_config,
			Collection<Type> types) {
		basePackages = i_basePackages;
		out = new StringBuffer();
		config = i_config;

		if (config.omitCommonPackagePrefix) {
			calcCommonPackagePrefix();
		}

		// draw types
		for (Type type : types) {
			if (basePackages.contains(type.getPackageName())
					|| config.showContext) {
				toAppleScript(type);
			}
		}

		// draw generalizations and implementations
		for (Type classifier : types) {
			toAppleScriptImplements(classifier);
			if (classifier instanceof Class) {
				toAppleScriptSuper((Class) classifier);
			}
		}

		// draw associations
		for (Attribute attribute : assocs) {
			toAppleScriptAsAssociation(attribute);
		}

		// draw dependencies
		if (config.showDependencies) {
			for (Type classifier : types) {
				for (Type supplier : classifier.dependencies()) {
					toAppleScriptDependency(classifier, supplier);
				}
			}
		}

		toAppleScriptConfig();

		// select all
		out.append("set selection of front window to {");
		for (int i = 1; i < shapeCounter; i++) {
			if (i > 1)
				out.append(", ");
			out.append("shape").append(i);
		}
		out.append("}\n");

		String elements = out.toString();

		StringBuffer strb = new StringBuffer();
		strb.append(TEMPLATE.substring(0, POS));

		// hack
		String strDefaultApp = "\"OmniGraffle Professional 5\"";
		int pos = strb.indexOf(strDefaultApp);
		if (pos > 0) {
			strb.replace(pos, pos + strDefaultApp.length(), "\""
					+ config.omniGraffleAppName + "\"");
			pos = strb.indexOf(strDefaultApp);
			if (pos > 0) {
				strb.replace(pos, pos + strDefaultApp.length(), "\""
						+ config.omniGraffleAppName + "\"");
			}
		}

		strb.append(elements);
		strb.append(TEMPLATE.substring(POS + POS_MARKER.length()));

		return strb.toString();
	}

	/**
	 * 
	 * @since Aug 20, 2011
	 */
	private void toAppleScriptConfig() {
		String shapeName = newShapeName();
		out.append("set " + shapeName + " to ");

		out.append("my createDiagramNote(\"").append(packagesString())
				.append("\", \"");

		out.append("Scopes shown: ");
		if (config.showPrivate)
			out.append("private, ");
		if (config.showPackage)
			out.append("package, ");
		if (config.showProtected)
			out.append("protected, ");
		if (config.showPublic)
			out.append("public, ");
		out.append("\n");
		if (config.showOperations) {
			if (!(config.showGetterSetter && config.showOverridings)) {
				out.append("Omitted methods: ");

				if (!config.showGetterSetter)
					out.append("getter and setter, ");
				if (!config.showOverridings)
					out.append("overriding methods, ");
				out.append("\n");
			}
		}
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = DateFormat.getDateInstance();
		out.append("created on ").append(df.format(date));

		out.append("\")\n");
	}

	/**
	 * @param i_classifier
	 * @param i_supplier
	 * @since Aug 20, 2011
	 */
	private void toAppleScriptDependency(Type i_classifier, Type i_supplier) {
		String srcShape = typeToShapeMap.get(i_classifier);
		String destShape = typeToShapeMap.get(i_supplier);
		if (srcShape != null && destShape != null
				&& !connections.contains(srcShape + "__" + destShape)) {

			String shapeName = newShapeName();
			out.append("set " + shapeName + " to ");

			out.append("my createDependency(").append(srcShape).append(", ")
					.append(destShape).append(")\n");

			connections.add(srcShape + "__" + destShape);

		}
	}

	/**
	 * @param i_attribute
	 * @since Aug 19, 2011
	 */
	private void toAppleScriptAsAssociation(Attribute i_attribute) {
		String srcShape = typeToShapeMap.get(i_attribute.getOwner());
		String destShape = typeToShapeMap.get(i_attribute.getType());

		if (srcShape != null && destShape != null) {

			String bound = i_attribute.getBoundString();
			String name = i_attribute.getName();

			String shapeName = newShapeName();
			out.append("set " + shapeName + " to ");

			out.append("my createAssoc(");
			out.append("\"").append(i_attribute.getScope().umlSymbol())
					.append(name).append("\"");
			out.append(", ");
			out.append("\"").append(bound).append("\"");
			out.append(", ");
			out.append(srcShape).append(", ").append(destShape);
			out.append(")\n");

			connections.add(srcShape + "__" + destShape);

		} else {
			System.err.println("Warning, cannot draw association: "
					+ i_attribute);
		}

	}

	String newShapeName() {
		return "shape" + (shapeCounter++);
	}

	/**
	 * @param i_classifier
	 * @since Aug 19, 2011
	 */
	private void toAppleScriptImplements(Type type) {
		String srcShape = typeToShapeMap.get(type);
		String connectionType = type instanceof Interface ? "my createGeneralization"
				: "my createImplementation";

		for (Interface _interface : type.interfaces()) {
			String destShape = typeToShapeMap.get(_interface);

			if (srcShape != null && destShape != null) {
				String shapeName = newShapeName();
				out.append("set " + shapeName + " to ");

				out.append(connectionType).append("(").append(srcShape)
						.append(", ").append(destShape).append(")\n");

				connections.add(srcShape + "__" + destShape);
			}
		}
	}

	/**
	 * @param i_classifier
	 * @since Aug 19, 2011
	 */
	private void toAppleScriptSuper(Class clazz) {

		if (clazz.getSuper() != null) {
			String srcShape = typeToShapeMap.get(clazz);
			String destShape = typeToShapeMap.get(clazz.getSuper());

			if (srcShape != null && destShape != null) {
				String shapeName = newShapeName();
				out.append("set " + shapeName + " to ");

				out.append("my createGeneralization(").append(srcShape)
						.append(", ").append(destShape).append(")\n");

				connections.add(srcShape + "__" + destShape);
			}
		}
	}

	/**
	 * @param i_classifier
	 * @since Aug 18, 2011
	 */
	private void toAppleScript(Type classifier) {

		if (isOmitted(classifier)
				|| (classifier.isContext() && !config.showContext))
			return;

		boolean first = true;

		String shapeName = newShapeName();
		out.append("set " + shapeName + " to ");

		typeToShapeMap.put(classifier, shapeName);

		boolean simple = classifier.isContext()
				|| !(config.showAttributes || config.showOperations);
		String makesimple = simple ? "Simple" : "";

		String typeName = classifier.getName();

		if (classifier instanceof Class) {
			out.append("my createClass" + makesimple + "(\"" + typeName
					+ "\", \"" + getPackageInNameCompartement(classifier)
					+ "\", ");
			out.append(String.valueOf(((Class) classifier).isAbstract()));

		} else {
			out.append("my createInterface" + makesimple + "(\"" + typeName
					+ "\", \"" + getPackageInNameCompartement(classifier)
					+ "\"");
		}

		if (!simple)
			out.append(", ");
		else {
			out.append(",");
			out.append(String.valueOf(classifier.isContext()));
		}

		if (simple && config.forceAllAssociations) {
			for (Attribute attribute : classifier.attributes()) {

				if (!isOmitted(attribute.getType())) {
					assocs.add(attribute);
				}

			}
		}

		if (!(classifier.isContext() || simple)) {
			out.append("{");
			for (Attribute attribute : classifier.attributes()) {
				if (showScope(attribute.getScope())
						&& (config.showStaticAttributes || !attribute
								.isStatic())) {

					if (!isOmitted(attribute.getType())) {
						assocs.add(attribute);
					} else {
						if (config.showAttributes) {
							if (!first) {
								out.append(",");
								outFormattedNL();
								out.append(",");
							} else
								first = false;
							outFormattedText(attribute.toUML(config),
									attribute.isStatic(), false);
						}

					}

				} else if (config.forceAllAssociations) {
					if (!isOmitted(attribute.getType())) {
						assocs.add(attribute);
					}
				}
			}

			out.append("}");

			out.append(", ");

			first = true;
			out.append("{");
			if (config.showOperations) {
				for (Operation operation : classifier.operations()) {
					if (showScope(operation.getScope())
							&& (config.showStaticOperations || !operation
									.isStatic())) {

						// ignore getter and setter
						if (!(!config.showGetterSetter && isGetterOrSetter(operation))
								&& !isOmitted(operation)) {
							if (!first) {
								out.append(",");
								outFormattedNL();
								out.append(",");
							} else {

								first = false;
							}
							outFormattedText(operation.toUML(config),
									operation.isStatic(), false);
						}
					}

				}
			}
			out.append("}");
		}
		out.append(")\n");

	}

	/**
	 * @param i_scope
	 * @return
	 * @since Aug 19, 2011
	 */
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

	final static List<String> OBJECTMETHODS = Arrays.asList("clone",
			"finalize", "hashCode", "toString");
	private static final Set<String> primitiveTypes = new HashSet<String>(
			Arrays.asList("int", "long", "byte", "float", "double", "boolean",
					"char"));

	/**
	 * @param i_operation
	 * @return
	 * @since Aug 19, 2011
	 */
	private boolean isOmitted(Operation i_operation) {

		if (!config.showOverridings) {
			if (i_operation.sizeFormalParameters() == 0
					&& OBJECTMETHODS.contains(i_operation.getName()))
				return true;

			if (isOverriding(i_operation, i_operation.getOwner()))
				return true;
		}

		return false;
	}

	/**
	 * @param i_operation
	 * @param i_owner
	 * @return
	 * @since Aug 20, 2011
	 */
	private boolean isOverriding(Operation i_operation, Type i_owner) {
		Type superType = null;
		if (i_owner instanceof Class) {
			superType = ((Class) i_owner).getSuper();
		}
		if (superType != null) {
			if (superType.definesOperation(i_operation))
				return true;
			if (isOverriding(i_operation, superType))
				return true;
		}
		for (Type s : i_owner.interfaces()) {
			if (s.definesOperation(i_operation))
				return true;
			if (isOverriding(i_operation, s))
				return true;
		}
		return false;
	}

	/**
	 * @param i_operation
	 * @return
	 * @since Aug 19, 2011
	 */
	private boolean isGetterOrSetter(Operation i_operation) {

		if (i_operation.getOwner() instanceof Class) {

			Class clazz = (Class) i_operation.getOwner();

			String name = i_operation.getName();
			int pars = i_operation.sizeFormalParameters();

			if (pars == 0) {
				if (name.startsWith("is")
						&& i_operation.getType().getName().equals("boolean")) {
					name = name.substring(2);
				} else if (name.startsWith("get")) {
					name = name.substring(3);
				} else {
					return false;
				}

				Attribute attribute = clazz.findAttributeByName(name);
				if (attribute != null) {
					return attribute.getType() == i_operation.getType();
				}

			} else if (pars == 1 && name.startsWith("set")) {
				name = name.substring(3);
				Attribute attribute = clazz.findAttributeByName(name);
				if (attribute != null) {
					return attribute.getType() == i_operation
							.getFormalParameter(0).getType();
				}
			}
		}
		return false;

	}

	/**
	 * @param i_classifier
	 * @return
	 * @since Aug 19, 2011
	 */
	private boolean isOmitted(Type i_classifier) {

		String packageName = i_classifier.getPackageName();
		if (packageName == null)
			return true;

		if (packageName.startsWith("java"))
			return true;
		if (packageName.equals("")) {
			return primitiveTypes.contains(i_classifier.getName());
		}

		return false;

	}

	private void outFormattedText(String text, boolean underlined,
			boolean centered) {
		out.append("{text:\"").append(text).append("\",");
		out.append("underlined: ").append(String.valueOf(underlined))
				.append(",");
		out.append("alignment: ").append(centered ? "centered" : "left");
		out.append("}");
	}

	private void outFormattedNL() {
		out.append("{text:\"\n\"}");
	}

}
