package de.jevopi.plist;

import java.io.Writer;
import java.time.temporal.TemporalAccessor;
import java.util.LinkedHashMap;
import java.util.Map;

public class PLDict extends PLCollection {
	final Map<String, PListObject> elements = new LinkedHashMap<>();

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
}
