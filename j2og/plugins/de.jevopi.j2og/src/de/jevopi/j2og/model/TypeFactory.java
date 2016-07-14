package de.jevopi.j2og.model;

public class TypeFactory {
	public enum Kind {
		CLASS, INTERFACE, ENUM
	}

	public static Type create(String name, String packageName, Kind kind) {
		switch (kind) {
		case CLASS: return new Class(name, packageName);
		case INTERFACE: return new Interface(name, packageName);
		case ENUM: return new Enum(name, packageName);
		}
		throw new IllegalArgumentException("Kind " + kind + " no supported.");
	}
}
