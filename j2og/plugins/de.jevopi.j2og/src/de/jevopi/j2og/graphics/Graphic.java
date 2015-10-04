package de.jevopi.j2og.graphics;

import de.jevopi.plist.PLDict;

public abstract class Graphic extends Element {

	private static int COUNTER = 100;

	public int ID = COUNTER++;

	@Override
	public PLDict toPLElement() {
		PLDict dict = new PLDict();
		dict.put("Class", className());
		addFields(dict);
		return dict;
	}

	protected String className() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return className();
	}
}
