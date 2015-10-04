package de.jevopi.plist;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class PLElement {

	@Override
	public String toString() {
		StringWriter w = new StringWriter();
		serialize(w, 0);
		return w.toString();
	}

	protected abstract void serialize(Writer w, int indent);

	protected void indent(Writer w, int indent, String s) {
		try {
			for (int i = indent; i > 0; i--) {
				w.append("    ");
			}
			w.append(s);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	protected void println(Writer w) {
		println(w, null);
	}

	protected void println(Writer w, Object o) {
		try {
			if (o != null) {
				w.append(o.toString());
			}
			w.append("\n");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	protected void print(Writer w, Object o) {
		try {
			if (o != null) {
				w.append(o.toString());
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	protected String esc(String v) {
		StringBuilder strb = new StringBuilder(v.length() + 5);
		for (int i = 0; i < v.length(); i++) {
			char c = v.charAt(i);
			switch (c) {
			case '<':
				strb.append("&lt;");
			default:
				strb.append(c);
			}
		}
		return strb.toString();
	}
}
