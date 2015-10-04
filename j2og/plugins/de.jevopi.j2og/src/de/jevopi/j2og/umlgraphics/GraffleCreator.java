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
package de.jevopi.j2og.umlgraphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Interface;
import de.jevopi.j2og.model.Model;
import de.jevopi.j2og.model.Type;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class GraffleCreator {

	Map<Type, Graphic> typeToShapeMap = new HashMap<>();
	List<Graphic> graphics = new ArrayList<>();

	private final Config config;

	public GraffleCreator(Config config, Model model) {
		this.config = config;

		// draw types
		for (Type type : model.modelTypes) {
			Graphic graphic = toGraphic(type);
			if (graphic != null) {
				graphics.add(graphic);
				typeToShapeMap.put(type, graphic);
			}
		}

		// draw generalizations, implementations
		for (Type classifier : model.modelTypes) {
			implementsToLine(classifier);
			if (classifier instanceof Class) {
				generalizationToLine((Class) classifier);
			}
		}

		// draw associations
		for (Attribute attribute : model.assocs) {
			assocToLine(attribute);
		}
		// draw dependencies
		if (config.showDependencies) {
			for (Type classifier : model.modelTypes) {
				for (Type supplier : classifier.dependencies()) {
					dependencyToLine(classifier, supplier);
				}
			}
		}

	}

	public List<Graphic> getGraphics() {
		return graphics;
	}

	// private void toAppleScriptConfig() {
	// String shapeName = newShapeName();
	// out.append("set " + shapeName + " to ");
	//
	// out.append("my createDiagramNote(\"").append(packagesString()).append("\", \"");
	//
	// out.append("Scopes shown: ");
	// if (config.showPrivate) {
	// out.append("private, ");
	// }
	// if (config.showPackage) {
	// out.append("package, ");
	// }
	// if (config.showProtected) {
	// out.append("protected, ");
	// }
	// if (config.showPublic) {
	// out.append("public, ");
	// }
	// out.append("\n");
	// if (config.showOperations) {
	// if (!(config.showGetterSetter && config.showOverridings)) {
	// out.append("Omitted methods: ");
	//
	// if (!config.showGetterSetter) {
	// out.append("getter and setter, ");
	// }
	// if (!config.showOverridings) {
	// out.append("overriding methods, ");
	// }
	// out.append("\n");
	// }
	// }
	// Date date = new Date(System.currentTimeMillis());
	// DateFormat df = DateFormat.getDateInstance();
	// out.append("created on ").append(df.format(date));
	//
	// out.append("\")\n");
	// }

	private void implementsToLine(Type type) {
		Graphic srcShape = typeToShapeMap.get(type);
		boolean generalization = type instanceof Interface;
		for (Interface _interface : type.interfaces()) {
			Graphic destShape = typeToShapeMap.get(_interface);

			if (srcShape != null && destShape != null) {
				if (generalization) {
					graphics.addAll(Lines.createGeneralization(srcShape, destShape));
				} else {
					graphics.addAll(Lines.createImplementation(srcShape, destShape));
				}
			}
		}
	}

	private void generalizationToLine(Class clazz) {

		if (clazz.getSuper() != null) {
			Graphic srcShape = typeToShapeMap.get(clazz);
			Graphic destShape = typeToShapeMap.get(clazz.getSuper());

			if (srcShape != null && destShape != null) {
				graphics.addAll(Lines.createGeneralization(srcShape, destShape));
			}
		}
	}

	private void dependencyToLine(Type classifier, Type supplier) {
		Graphic srcShape = typeToShapeMap.get(classifier);
		Graphic destShape = typeToShapeMap.get(supplier);

		if (srcShape != null && destShape != null) {
			graphics.addAll(Lines.createDependency(srcShape, destShape));
		}
	}

	private void assocToLine(Attribute attribute) {
		Graphic srcShape = typeToShapeMap.get(attribute.getOwner());
		Graphic destShape = typeToShapeMap.get(attribute.type);

		if (srcShape != null && destShape != null) {
			graphics.addAll(Lines.createAssociation(attribute, srcShape, destShape));
		}

	}

	private Graphic toGraphic(Type classifier) {
		boolean simple = classifier.isContext() || !(config.showAttributes || config.showOperations);

		int shapeSize = typeToShapeMap.size();

		double x = (shapeSize % 10) * 100;
		double y = (shapeSize / 10) * 100;

		if (simple) {
			return ClassifierShapes.createClassifierSimple(classifier, x, y);
		} else {
			return ClassifierShapes.createClassifier(classifier, x, y, config.showAttributes, config.showOperations);
		}

	}

}
