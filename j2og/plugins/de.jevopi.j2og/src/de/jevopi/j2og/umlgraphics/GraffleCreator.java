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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.jevopi.j2og.config.Config;
import static de.jevopi.j2og.config.Config.*;
import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Enum;
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

	private Map<Type, Set<Type>> dependencies = new HashMap<>();

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
		if (config.is(SHOW_DEPENDENCIES)) {
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

	private void implementsToLine(Type type) {
		Graphic srcShape = typeToShapeMap.get(type);
		boolean generalization = type instanceof Interface;
		for (Interface _interface : type.interfaces()) {
			Graphic destShape = typeToShapeMap.get(_interface);
			addDependency(type, _interface);

			if (srcShape != null && destShape != null) {
				if (generalization) {
					graphics.addAll(Lines.createGeneralization(srcShape, destShape));
				} else {
					graphics.addAll(Lines.createImplementation(srcShape, destShape));
				}
			}
		}
	}

	private void addDependency(Type client, Type supplier) {
		Set<Type> clientDeps = dependencies.get(client);
		if (clientDeps == null) {
			clientDeps = new HashSet<>();
			dependencies.put(client, clientDeps);
		}
		clientDeps.add(supplier);
	}

	private boolean hasDependency(Type client, Type supplier) {
		Set<Type> clientDeps = dependencies.get(client);
		if (clientDeps == null) {
			return false;
		}
		return clientDeps.contains(supplier);
	}

	private void generalizationToLine(Class clazz) {
		for (Class superClass : clazz.superClasses()) {
			addDependency(clazz, superClass);
			Graphic srcShape = typeToShapeMap.get(clazz);
			Graphic destShape = typeToShapeMap.get(superClass);

			if (srcShape != null && destShape != null) {
				graphics.addAll(Lines.createGeneralization(srcShape, destShape));
			}
		}
	}

	private void dependencyToLine(Type classifier, Type supplier) {
		if (classifier == supplier) {
			return;
		}
		if (hasDependency(classifier, supplier)) {
			return; // do not draw dependencies if there is a generalization,
			// implementation or association
		}

		Graphic srcShape = typeToShapeMap.get(classifier);
		Graphic destShape = typeToShapeMap.get(supplier);

		if (srcShape != null && destShape != null) {
			addDependency(classifier, supplier);
			graphics.addAll(Lines.createDependency(srcShape, destShape));
		}
	}

	private void assocToLine(Attribute attribute) {
		Graphic srcShape = typeToShapeMap.get(attribute.getOwner());
		Graphic destShape = typeToShapeMap.get(attribute.type);

		addDependency(attribute.getOwner(), attribute.type);

		if (srcShape != null && destShape != null) {
			graphics.addAll(Lines.createAssociation(attribute, srcShape, destShape));
		}

	}


	/**
	 * Returns true if given type is an enum and if enums are to be shown as attributes.
	 */
	private boolean enumAsAttribute(Type type) {
		return (type instanceof Enum) && (config.is(ENUMS_AS_ATTRIBUTES));
	}

	private Graphic toGraphic(Type classifier) {
		if (enumAsAttribute(classifier)) {
			return null;
		}


		boolean simple = classifier.isContext() || !(config.is(SHOW_ATTRIBUTES) || config.is(SHOW_OPERATIONS));

		int shapeSize = typeToShapeMap.size();

		double x = (shapeSize % 10) * 150 + 10;
		double y = (shapeSize / 10) * 150 + 10;

		ClassifierShapes cshapes = new ClassifierShapes(config);
		if (simple) {
			return cshapes.createClassifierSimple(classifier, x, y);
		} else {
			return cshapes.createClassifier(classifier, x, y);
		}

	}

}
