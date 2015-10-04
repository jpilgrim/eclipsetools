package de.jevopi.j2og.umlgraphics;

import de.jevopi.j2og.graphics.Defaults;
import de.jevopi.j2og.graphics.LineGraphic;
import de.jevopi.j2og.graphics.ShapedGraphic;
import de.jevopi.j2og.graphics.Text;
import de.jevopi.j2og.graphics.geometry.LineLabelPosition;
import de.jevopi.j2og.graphics.properties.Flow;

public class Labels {

	public final static ShapedGraphic createCardinalityLabel(String text, LineGraphic lineGraphic) {
		ShapedGraphic graphic = new ShapedGraphic();
		graphic.fontInfo = Defaults.LINE_LABEL_FONT;
		graphic.line = new LineLabelPosition(lineGraphic, Defaults.CARDINALITY_OFFSET, Defaults.CARDINALITY_POSITION);
		graphic.text = new Text(text);
		graphic.style = Defaults.NO_DRAW;
		graphic.fitText = "YES";
		graphic.flow = Flow.Resize;
		graphic.wrap = "NO";
		graphic.bounds = null;
		// new Bounds(lineGraphic.getAbsPosition(Defaults.CARDINALITY_POSITION),
		// new Point(
		// 8 * text.length(), 21));
		return graphic;
	}

	public final static ShapedGraphic createAttributeNameLabel(String text, LineGraphic lineGraphic) {
		ShapedGraphic graphic = new ShapedGraphic();
		graphic.fontInfo = Defaults.LINE_LABEL_FONT;
		graphic.line = new LineLabelPosition(lineGraphic, Defaults.ASSOC_NAME_OFFSET, Defaults.ASSOC_NAME_OFFSET);
		graphic.text = new Text(text);
		graphic.style = Defaults.NO_DRAW;
		graphic.fitText = "YES";
		graphic.flow = Flow.Resize;
		graphic.wrap = "NO";
		graphic.bounds = null;
		return graphic;
	}
}
