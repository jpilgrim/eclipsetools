package de.jevopi.j2og.graphics;

import java.util.Collection;

import de.jevopi.j2og.graphics.geometry.Bounds;
import de.jevopi.j2og.graphics.geometry.LineLabelPosition;
import de.jevopi.j2og.graphics.geometry.Magnets;
import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.graphics.properties.Fill;
import de.jevopi.j2og.graphics.properties.Flow;
import de.jevopi.j2og.graphics.properties.FontInfo;
import de.jevopi.j2og.graphics.properties.Shadow;
import de.jevopi.j2og.graphics.properties.ShapeType;
import de.jevopi.j2og.graphics.properties.Stroke;

public class ShapedGraphic extends Graphic {

	public static class Style extends Element {
		public Shadow _shadow = Shadow.NO;
		public Fill fill = Fill.NO;
		public Stroke stroke = Stroke.NO;
	}

	public Bounds bounds;
	public Collection<Point> magnets = Magnets.PER_SIDE_3;
	public ShapeType shape = ShapeType.Rectangle;
	public Style style = new Style();
	public Text text = new Text("Class");

	public LineLabelPosition line = null;
	public FontInfo fontInfo = null;
	public String fitText = null;
	public Flow flow = Flow.Overflow;
	public String wrap = null;

	@Override
	protected String className() {
		return ShapedGraphic.class.getSimpleName();
	}

}
