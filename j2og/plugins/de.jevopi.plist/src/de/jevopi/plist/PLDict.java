package de.jevopi.plist;

import java.io.Writer;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class PLDict extends PLCollection {
	final Map<String, PListObject> elements = new TreeMap<>();

	@Override
	public void serialize(Writer w, int indent) {
		if (elements.isEmpty()) {
			print(w, "<dict/>");
		} else {
			println(w, "<dict>");
			elements.forEach((k, v) -> {
				indent(w, indent + 1, "<key>");
				print(w, esc(k));
				print(w, "</key>");
				println(w);
				indent(w, indent + 1, "");
				v.serialize(w, indent + 1);
				println(w);
			});
			indent(w, indent, "</dict>");
		}
	}

	public PListObject get(String key) {
		return elements.get(key);
	}

	public PLDict put(String key, PListObject value) {
		elements.put(key, value);
		return this;
	}

	public PLDict put(String key, String value) {
		elements.put(key, new PLPrimitive(value));
		return this;
	}

	public PLDict put(String key, int value) {
		elements.put(key, new PLPrimitive(value));
		return this;
	}

	public PLDict put(String key, double value) {
		elements.put(key, new PLPrimitive(value));
		return this;
	}

	public PLDict put(String key, boolean value) {
		elements.put(key, new PLPrimitive(value));
		return this;
	}

	public void put(String key, TemporalAccessor value) {
		elements.put(key, new PLPrimitive(value));
	}

	public void remove(String key) {
		elements.remove(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : Arrays.hashCode(elements.values().toArray()));
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
		PLDict other = (PLDict) obj;
		if (elements == null) {
			if (other.elements != null) {
				return false;
			}
		} else {
			if (elements.size() != other.elements.size()) {
				return false;
			}
			for (Entry<String, PListObject> entry : elements.entrySet()) {
				PListObject otherObj = other.elements.get(entry.getKey());
				if (!entry.getValue().equals(otherObj)) {
					return false;
				}
			}
		}
		return true;
	}

}
