package de.jevopi.j2og.umlgraphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.graphics.LineGraphic;
import de.jevopi.j2og.graphics.decorators.Arrow;
import de.jevopi.j2og.graphics.properties.Color;
import de.jevopi.j2og.graphics.properties.Stroke;
import de.jevopi.j2og.model.Attribute;

public class Lines {

	public static Collection<Graphic> createImplementation(Graphic srcShape, Graphic destShape, boolean gray) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.UMLInheritance;
		line.style._stroke.tailArrow = Arrow.None;
		line.style._stroke.lineType = Stroke.TYPE_ORTHOGONAL;
		line.style._stroke.pattern = Stroke.PATTERN_DASHED;
		if (gray) {
			line.style._stroke.color = Color.CONCRETE;
		}
		return Collections.singleton(line);
	}

	public static Collection<Graphic> createGeneralization(Graphic srcShape, Graphic destShape, boolean gray) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.UMLInheritance;
		line.style._stroke.tailArrow = Arrow.None;
		line.style._stroke.lineType = Stroke.TYPE_ORTHOGONAL;
		line.style._stroke.pattern = Stroke.PATTERN_SOLID;
		if (gray) {
			line.style._stroke.color = Color.CONCRETE;
		}
		return Collections.singleton(line);
	}

	public static Collection<Graphic> createDependency(Graphic srcShape, Graphic destShape, boolean gray) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.StickArrow;
		line.style._stroke.tailArrow = Arrow.None;
		line.style._stroke.lineType = Stroke.TYPE_STRAIGHT;
		line.style._stroke.pattern = Stroke.PATTERN_DASHED;
		if (gray) {
			line.style._stroke.color = Color.CONCRETE;
		}
		return Collections.singleton(line);
	}

	public static Collection<Graphic> createAssociation(Attribute attribute, Graphic srcShape, Graphic destShape, boolean gray) {
		ArrayList<Graphic> assoc = new ArrayList<>(3);
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.StickArrow;
		if (attribute.isContainment()) {
			line.style._stroke.tailArrow = Arrow.FilledDiamond;
		} else {
			line.style._stroke.tailArrow = Arrow.None;
		}
		line.style._stroke.lineType = Stroke.TYPE_STRAIGHT;
		assoc.add(line);
		assoc.add(Labels.createAttributeNameLabel((attribute.isDerived() ? "/" : "") + attribute.displayName, line));
		if (!attribute.getBoundString().isEmpty()) {
			assoc.add(Labels.createCardinalityLabel(attribute.getBoundString(), line));
		}
		if (gray) {
			line.style._stroke.color = Color.CONCRETE;
		}
		return assoc;

	}

}
