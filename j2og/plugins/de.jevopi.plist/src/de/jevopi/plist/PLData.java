package de.jevopi.plist;

import java.io.Writer;
import java.util.Arrays;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
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
		PLData other = (PLData) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		return true;
	}

}
