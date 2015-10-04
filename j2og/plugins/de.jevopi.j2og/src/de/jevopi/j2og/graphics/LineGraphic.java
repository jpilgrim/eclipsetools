package de.jevopi.j2og.graphics;

import java.util.ArrayList;

import de.jevopi.j2og.graphics.geometry.Anchor;
import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.graphics.properties.Stroke;

public class LineGraphic extends Graphic {

	public static class Style extends Element {
		public Stroke _stroke = new Stroke();
	}

	// boolean orthogonalBarAutomatic = true;
	// Point orthogonalBarPoint = new Point(0, 0);
	// double orthogonalBarPosition = -1;
	public ArrayList<Point> points = null;
	public Style style = new Style();
	public Anchor tail = null;
	public Anchor head = null;

	public LineGraphic() {
	}

	public LineGraphic(Point from, Point to) {
		points = new ArrayList<>(2);
		points.add(from);
		points.add(to);
	}

	public LineGraphic(Graphic from, Graphic to) {
		head = new Anchor(from);
		tail = new Anchor(to);
	}

}
