package de.jevopi.plist;

import java.io.Writer;

public class PList extends PLElement {
	PListObject element;
	String version = "1.0";

	@Override
	protected void serialize(Writer w, int indent) {
		println(w, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		println(w,
				"<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
		println(w, "<plist version=\"" + version + "\">");
		if (element != null) {
			element.serialize(w, 0);
			println(w);
		}
		println(w, "</plist>");
	}

	public void setElement(PListObject element) {
		this.element = element;
	}

	public void write(Writer w) {
		serialize(w, 0);
	}
}
