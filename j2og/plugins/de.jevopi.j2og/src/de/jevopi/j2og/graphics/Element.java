package de.jevopi.j2og.graphics;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import de.jevopi.plist.PLArray;
import de.jevopi.plist.PLDict;
import de.jevopi.plist.PListObject;

public abstract class Element {
	public static final String YES = "YES";
	public static final String NO = "NO";

	public PLDict toPLElement() {
		PLDict dict = new PLDict();
		addFields(dict);
		return dict;
	}

	protected void addFields(PLDict dict) {
		try {
			Class<? extends Element> clazz = this.getClass();
			SortedSet<Field> sortedFields = new TreeSet<>(new Comparator<Field>() {
				@Override
				public int compare(Field f1, Field f2) {
					return f1.getName().compareTo(f2.getName());
				}
			});
			for (Field field : clazz.getFields()) {
				if ( // getFields only returns public fields:
						// Modifier.isPublic(field.getModifiers()) &&
						!Modifier.isStatic(field.getModifiers()) && !(field.getName().startsWith("m_"))) {
					sortedFields.add(field);
				}
			}
			for (Field field : sortedFields) {
				String name = field.getName();
				String key = fieldNameToKey(name);
				Class<?> type = field.getType();
				Object value = field.get(this);
				if (value != null) {
					if (value instanceof Element) {
						dict.put(key, ((Element) value).toPLElement());
					} else if (type == int.class) {
						dict.put(key, (int) value);
					} else if (value instanceof Integer) {
						dict.put(key, ((Integer) value).intValue());
					} else if (type == double.class) {
						dict.put(key, (double) value);
					} else if (type == boolean.class) {
						dict.put(key, (boolean) value);
					} else if (value instanceof Boolean) {
						dict.put(key, ((Boolean) value).booleanValue());
					} else if (type.isArray()) {
						dict.put(key, toPLArray((Object[]) value));
					} else if (value instanceof Collection) {
						dict.put(key, toPLArray(((Collection<?>) value).toArray()));
					} else if (value instanceof TemporalAccessor) {
						dict.put(key, (TemporalAccessor) value);
					} else if (value instanceof PListObject) {
						dict.put(key, (PListObject) value);
					} else {
						dict.put(key, value.toString());
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private String fieldNameToKey(String name) {
		if (name.startsWith("_")) {
			return name.substring(1);
		}
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	protected static PLArray toPLArray(Object[] array) {
		PLArray plarray = new PLArray();
		for (Object value : array) {
			Class<?> type = value.getClass();
			if (value != null) {
				if (value instanceof Element) {
					plarray.add(((Element) value).toPLElement());
				} else if (type == int.class) {
					plarray.add((int) value);
				} else if (value instanceof Integer) {
					plarray.add(((Integer) value).intValue());
				} else if (type == double.class) {
					plarray.add((double) value);
				} else if (value instanceof Double) {
					plarray.add(((Double) value).doubleValue());
				} else if (type == boolean.class) {
					plarray.add((boolean) value);
				} else if (type.isArray()) {
					plarray.add(toPLArray((Object[]) value));
				} else if (value instanceof Collection) {
					plarray.add(toPLArray(((Collection<?>) value).toArray()));
				} else if (value instanceof TemporalAccessor) {
					plarray.add((TemporalAccessor) value);
				} else if (value instanceof PListObject) {
					plarray.add((PListObject) value);
				} else {
					plarray.add(value.toString());
				}
			}
		}
		return plarray;
	}
}