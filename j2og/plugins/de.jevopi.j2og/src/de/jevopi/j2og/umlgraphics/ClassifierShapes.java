package de.jevopi.j2og.umlgraphics;

import java.util.List;

import de.jevopi.j2og.graphics.ShapedGraphic;
import de.jevopi.j2og.graphics.TableGroup;
import de.jevopi.j2og.graphics.Text;
import de.jevopi.j2og.graphics.geometry.Bounds;
import de.jevopi.j2og.graphics.geometry.Magnets;
import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;

public class ClassifierShapes {

	public final static double WIDTH = 99;
	public final static double HEIGHT = 13;
	public final static double MARGIN = 10;

	private static ShapedGraphic createHeader(String name, double x, double y) {
		ShapedGraphic box = new ShapedGraphic();
		box.text = new Text();
		box.text.appendBold(name);
		box.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + box.text.lines() * HEIGHT));
		return box;
	}

	public static ShapedGraphic createClassifierSimple(Type classifier, double x, double y) {
		ShapedGraphic compartement = createHeader(classifier.displayName, x, y);
		compartement.magnets = Magnets.PER_SIDE_3;
		return compartement;

	}

	public static TableGroup createClassifier(Type type, double x, double y, boolean showAttributes,
			boolean showOperations) {
		TableGroup group = new TableGroup();
		group.magnets = Magnets.PER_SIDE_3;
		ShapedGraphic header = createHeader(type.displayName, x, y);
		group.add(header);
		y += header.bounds.size.y;
		ShapedGraphic compartement;
		compartement = new ShapedGraphic();
		compartement.fitText = ShapedGraphic.FIT_TEXT_VERTICAL;
		compartement.text = new Text();
		compartement.text.align = Text.ALIGN_LEFT;
		compartement.textPlacement = ShapedGraphic.PLACEMENT_TOP;
		List<Attribute> attributes = type.attributes();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			if (i != 0) {
				compartement.text.appendNL();
			}
			compartement.text.append(attribute.displayName);
		}
		int lines = compartement.text.lines();
		compartement.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + lines * HEIGHT));
		group.add(compartement);
		y += compartement.bounds.size.y;
		compartement = new ShapedGraphic();
		compartement.fitText = ShapedGraphic.FIT_TEXT_VERTICAL;
		compartement.text = new Text();
		compartement.text.align = Text.ALIGN_LEFT;
		compartement.textPlacement = ShapedGraphic.PLACEMENT_TOP;
		List<Operation> operations = type.operations();
		for (int i = 0; i < operations.size(); i++) {
			Operation operation = operations.get(i);
			if (i != 0) {
				compartement.text.appendNL();
			}
			compartement.text.append(operation.displayName);
		}
		lines = compartement.text.lines();
		compartement.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + lines * HEIGHT));
		group.add(compartement);
		return group;
	}
}
