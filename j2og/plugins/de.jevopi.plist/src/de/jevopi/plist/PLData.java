package de.jevopi.plist;

import java.io.Writer;
import java.util.Base64;

public class PLData extends PLElement {

	private final byte[] data;

	public PLData(byte[] data) {
		this.data = data;
	}

	@Override
	protected void serialize(Writer w, int indent) {
		print(w, "<data>");
		print(w, Base64.getEncoder().encodeToString(data));
		print(w, "</data>");
	}

}
