package de.jevopi.j2og.umlgraphics;

import java.util.List;

import de.jevopi.j2og.config.Config;
import static de.jevopi.j2og.config.Config.*;
import de.jevopi.j2og.graphics.ShapedGraphic;
import de.jevopi.j2og.graphics.TableGroup;
import de.jevopi.j2og.graphics.Text;
import de.jevopi.j2og.graphics.geometry.Bounds;
import de.jevopi.j2og.graphics.geometry.Magnets;
import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.graphics.properties.Color;
import de.jevopi.j2og.graphics.properties.Stroke;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Interface;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.model.Enum;

public class ClassifierShapes {

	public final static double WIDTH = 99;
	public final static double HEIGHT = 13;
	public final static double MARGIN = 10;

	final Config config;

	public ClassifierShapes(Config config) {
		this.config = config;
	}

	private ShapedGraphic createHeader(Type classifier, double x, double y) {
		ShapedGraphic box = new ShapedGraphic();

		box.text = new Text();
		box.text.setSize(12);

		if (config.is(CONTEXT_GRAY) && classifier.isContext()) {
			box.text.setColor(Color.CONCRETE);

			if (config.is(CONTEXT_GRAY) && classifier.isContext()) {
				box.style._stroke = new Stroke();
				box.style._stroke.color = Color.CONCRETE;
			}

		}

		if (classifier instanceof Interface) {
			box.text.appendSmall(Text.LAQUO + "interface" + Text.RAQUO + Text.NL);
		} else if (classifier instanceof Enum) {
			box.text.appendSmall(Text.LAQUO + "enum" + Text.RAQUO + Text.NL);
		}
		if (config.is(SHOW_PACKAGE_NAME) || (classifier.isContext() && config.is(SHOW_PACKAGE_NAME_CONTEXT))) {
			if (classifier.displayPackageName != null && !classifier.displayPackageName.isEmpty()) {
				box.text.appendSmallBold(classifier.displayPackageName + "." + Text.NL);
			}
		}
		box.text.appendBold(classifier.displayName);
		box.bounds = new Bounds(new Point(x, y), new Point(WIDTH, 1 + MARGIN + box.text.lines() * HEIGHT));
		box.fitText = ShapedGraphic.FIT_TEXT_VERTICAL;
		return box;
	}

	public ShapedGraphic createClassifierSimple(Type classifier, double x, double y) {
		ShapedGraphic compartement = createHeader(classifier, x, y);
		compartement.magnets = Magnets.PER_SIDE_3;
		return compartement;

	}

	public TableGroup createClassifier(Type type, double x, double y) {
		TableGroup group = new TableGroup();
		group.magnets = Magnets.PER_SIDE_3;
		ShapedGraphic header = createHeader(type, x, y);
		group.add(header);
		y += header.bounds.size.y;
		ShapedGraphic compartement;
		if (config.is(SHOW_ATTRIBUTES)) {
			compartement = createMemberCompartement();
			if (config.is(CONTEXT_GRAY) && type.isContext()) {
				if (config.is(CONTEXT_GRAY) && type.isContext()) {
					compartement.style._stroke = new Stroke();
					compartement.style._stroke.color = Color.CONCRETE;
				}
			}
			List<Attribute> attributes = type.attributes();
			if (attributes.size() > 0) {
				for (int i = 0; i < attributes.size(); i++) {
					Attribute attribute = attributes.get(i);
					if (i != 0) {
						compartement.text.appendNL();
					}
					compartement.text.append(attribute.toUML(config));
				}
			} else {
				compartement.text.append(" ");
			}
			autosizeCompartement(x, y, compartement);
			y += compartement.bounds.size.y;
			group.add(compartement);
		}
		if (config.is(SHOW_OPERATIONS)) {
			compartement = createMemberCompartement();
			if (config.is(CONTEXT_GRAY) && type.isContext()) {
				if (config.is(CONTEXT_GRAY) && type.isContext()) {
					compartement.style._stroke = new Stroke();
					compartement.style._stroke.color = Color.CONCRETE;
				}
			}
			List<Operation> operations = type.operations();
			if (operations.size() > 0) {
				for (int i = 0; i < operations.size(); i++) {
					Operation operation = operations.get(i);
					if (i != 0) {
						compartement.text.appendNL();
					}
					compartement.text.append(operation.toUML(config));
				}
			} else {
				compartement.text.append(" ");
			}
			autosizeCompartement(x, y, compartement);
			group.add(compartement);
		}
		return group;
	}

	private static void autosizeCompartement(double x, double y, ShapedGraphic compartement) {
		int lines = compartement.text.lines();
		compartement.bounds = new Bounds(new Point(x, y), new Point(WIDTH, MARGIN + lines * HEIGHT));
	}

	private static ShapedGraphic createMemberCompartement() {
		ShapedGraphic compartement;
		compartement = new ShapedGraphic();
		compartement.fitText = ShapedGraphic.FIT_TEXT_VERTICAL;
		compartement.text = new Text();
		compartement.text.setSize(11);
		compartement.text.setAlign(Text.ALIGN_LEFT);
		compartement.textPlacement = ShapedGraphic.PLACEMENT_TOP;
		return compartement;
	}
}
