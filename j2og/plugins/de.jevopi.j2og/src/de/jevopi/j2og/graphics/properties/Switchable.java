package de.jevopi.j2og.graphics.properties;

import de.jevopi.j2og.graphics.Element;
import de.jevopi.plist.PLDict;

public class Switchable extends Element {

	public static <T extends Switchable> T NO(T switchable) {
		switchable.m_enabled = false;
		return switchable;
	}

	boolean m_enabled = true;

	@Override
	public PLDict toPLElement() {
		if (!m_enabled) {
			PLDict dict = new PLDict();
			dict.put("Draws", "NO");
			return dict;
		}

		return super.toPLElement();
	}

}
