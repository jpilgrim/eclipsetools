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
			print(w, "<array/>");
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PLArray other = (PLArray) obj;
		if (elements == null) {
			if (other.elements != null) {
				return false;
			}
		} else if (!elements.equals(other.elements)) {
			return false;
		}
		return true;
	}

}
