package de.jevopi.j2og.graphics;

import java.util.Collection;

import de.jevopi.j2og.graphics.geometry.Bounds;
import de.jevopi.j2og.graphics.geometry.LineLabelPosition;
import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.graphics.properties.Flow;
import de.jevopi.j2og.graphics.properties.FontInfo;
import de.jevopi.j2og.graphics.properties.ShapeType;
import de.jevopi.j2og.graphics.properties.Style;

public class ShapedGraphic extends Graphic {

	public static final Integer PLACEMENT_TOP = 0;
	public static final Integer PLACEMENT__MIDDLE = 1;
	public static final Integer PLACEMENT_BOTTOM = 2;

	public static final String FIT_TEXT_VERTICAL = "Vertical";

	public Bounds bounds;
	public Collection<Point> magnets = null;
	public ShapeType shape = ShapeType.Rectangle;
	public Style style = new Style();
	public Text text = new Text("Class");

	public LineLabelPosition line = null;
	public FontInfo fontInfo = null;
	public String fitText = null;
	public Flow flow = Flow.Resize;
	public String wrap = null;

	/** Vertical alignment: top, middle (default), bottom */
	public Integer textPlacement = null;

	@Override
	protected String className() {
		return ShapedGraphic.class.getSimpleName();
	}

}
