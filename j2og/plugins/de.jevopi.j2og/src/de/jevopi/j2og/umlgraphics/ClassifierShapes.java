package de.jevopi.j2og.umlgraphics;

import java.util.List;

import de.jevopi.j2og.graphics.ShapedGraphic;
import de.jevopi.j2og.graphics.TableGroup;
import de.jevopi.j2og.graphics.Text;
import de.jevopi.j2og.graphics.geometry.Bounds;
import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;

public class ClassifierShapes {

	public final static double WIDTH = 99;
	public final static double HEIGHT = 26;
	public final static double MARGIN = 10;

	private static ShapedGraphic createHeader(String name, double x, double y) {
		ShapedGraphic box = new ShapedGraphic();
		box.text = new Text();
		box.text.appendBold(name);
		box.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + box.text.getLineNumber() * HEIGHT));
		return box;
	}

	public static ShapedGraphic createClassifierSimple(Type classifier, double x, double y) {
		return createHeader(classifier.displayName, x, y);
	}

	public static TableGroup createClassifier(Type type, double x, double y, boolean showAttributes,
			boolean showOperations) {
		TableGroup group = new TableGroup();
		ShapedGraphic header = createHeader(type.displayName, x, y);
		group.add(header);
		y += header.bounds.p2.y;
		ShapedGraphic compartement;
		compartement = new ShapedGraphic();
		compartement.text = new Text();
		List<Attribute> attributes = type.attributes();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			if (i != 0) {
				compartement.text.appendNL();
			}
			compartement.text.append(attribute.displayName);
		}
		compartement.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + compartement.text.getLineNumber()
				* HEIGHT));
		group.add(compartement);
		y += compartement.bounds.p2.y;
		compartement = new ShapedGraphic();
		compartement.text = new Text();
		List<Operation> operations = type.operations();
		for (int i = 0; i < operations.size(); i++) {
			Operation operation = operations.get(i);
			if (i != 0) {
				compartement.text.appendNL();
			}
			compartement.text.append(operation.displayName);
		}
		compartement.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + compartement.text.getLineNumber()
				* HEIGHT));
		group.add(compartement);
		return group;
	}
}
