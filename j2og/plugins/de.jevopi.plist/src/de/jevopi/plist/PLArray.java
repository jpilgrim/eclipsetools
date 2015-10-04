package de.jevopi.plist;

import java.io.Writer;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class PLArray extends PLCollection {
	final List<PListObject> elements = new ArrayList<>();

	@Override
	public void serialize(Writer w, int indent) {
		if (elements.isEmpty()) {
			println(w, "<array/>");
		} else {
			println(w, "<array>");
			elements.forEach(e -> {
				indent(w, indent + 1, "");
				e.serialize(w, indent + 1);
				println(w);
			});
			indent(w, indent, "</array>");
		}
	}

	public void add(PListObject element) {
		elements.add(element);
	}

	public void add(String value) {
		elements.add(new PLPrimitive(value));
	}

	public void add(int value) {
		elements.add(new PLPrimitive(value));
	}

	public void add(double value) {
		elements.add(new PLPrimitive(value));
	}

	public void add(boolean value) {
		elements.add(new PLPrimitive(value));
	}

	public void add(TemporalAccessor value) {
		elements.add(new PLPrimitive(value));
	}
}
