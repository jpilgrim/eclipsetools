package de.jevopi.plist;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * string, boolean (true, false), real, integer, date
 *
 * @author jpilgrim
 *
 */
public class PLPrimitive extends PListObject {

	final Object value;

	public PLPrimitive(String value) {
		this.value = value;
	}

	public PLPrimitive(boolean value) {
		this.value = value;
	}

	public PLPrimitive(int value) {
		this.value = value;
	}

	public PLPrimitive(double value) {
		this.value = value;
	}

	public PLPrimitive(TemporalAccessor value) {
		this.value = value;
	}

	@Override
	protected void serialize(Writer w, int indent) {
		if (value instanceof String) {
			printValue(w, "string", esc((String) value));
		} else if (value instanceof Integer) {
			printValue(w, "integer", value.toString());
		} else if (value instanceof Double) {
			double d = ((Double) value).doubleValue();
			if (d == 0) {
				printValue(w, "real", "0.0");
			} else if (d == ((int) d)) {
				printValue(w, "real", Integer.valueOf((int) d).toString());
			} else {
				printValue(w, "real", value.toString());
			}
		} else if (value instanceof TemporalAccessor) {
			// YYYY '-' MM '-' DD 'T' HH ':' MM ':' SS 'Z'
			TemporalAccessor ta = (TemporalAccessor) value;

			String s = LocalDateTime.from(ta).format(DateTimeFormatter.ofPattern("yyyy[-MM[-dd[ HH[:mm[:ss[ x]]]]]]"));
			printValue(w, "date", s);
		} else if (value instanceof Boolean) {
			if ((Boolean) value) {
				print(w, "<true/>");
			} else {
				print(w, "<false/>");
			}
		}

	}

	private void printValue(Writer w, String tag, String strValue) {
		print(w, "<" + tag + ">");
		print(w, strValue);
		print(w, "</" + tag + ">");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		PLPrimitive other = (PLPrimitive) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
