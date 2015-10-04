package de.jevopi.j2og.umlgraphics;

import java.util.Collection;
import java.util.Collections;

import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.graphics.LineGraphic;
import de.jevopi.j2og.graphics.decorators.Arrow;
import de.jevopi.j2og.graphics.properties.Stroke;
import de.jevopi.j2og.model.Attribute;

public class Lines {

	public static Collection<Graphic> createAssociation(Graphic from, Graphic to) {
		LineGraphic lineGraphic = new LineGraphic(from, to);
		return Collections.singleton(lineGraphic);
	}

	public static Collection<Graphic> createImplementation(Graphic srcShape, Graphic destShape) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.UMLInheritance;
		line.style._stroke.tailArrow = Arrow.None;
		line.style._stroke.lineType = Stroke.TYPE_ORTHOGONAL;
		line.style._stroke.pattern = Stroke.PATTERN_DASHED;
		return Collections.singleton(line);
	}

	public static Collection<Graphic> createGeneralization(Graphic srcShape, Graphic destShape) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.UMLInheritance;
		line.style._stroke.tailArrow = Arrow.None;
		line.style._stroke.lineType = Stroke.TYPE_ORTHOGONAL;
		line.style._stroke.pattern = Stroke.PATTERN_SOLID;
		return Collections.singleton(line);
	}

	public static Collection<Graphic> createDependency(Graphic srcShape, Graphic destShape) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.headArrow = Arrow.StickArrow;
		line.style._stroke.tailArrow = Arrow.None;
		line.style._stroke.lineType = Stroke.TYPE_STRAIGHT;
		line.style._stroke.pattern = Stroke.PATTERN_DASHED;
		return Collections.singleton(line);
	}

	public static Collection<Graphic> createAssociation(Attribute attribute, Graphic srcShape, Graphic destShape) {
		LineGraphic line = new LineGraphic(destShape, srcShape);
		line.style._stroke.lineType = Stroke.TYPE_STRAIGHT;
		return Collections.singleton(line);
	}

}
