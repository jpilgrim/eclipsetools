package de.jevopi.plist;

import java.io.Writer;
import java.time.temporal.ChronoField;
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
			printValue(w, "real", value.toString());
		} else if (value instanceof TemporalAccessor) {
			// YYYY '-' MM '-' DD 'T' HH ':' MM ':' SS 'Z'
			TemporalAccessor ta = (TemporalAccessor) value;
			StringBuilder strb = new StringBuilder();
			if (ta.isSupported(ChronoField.YEAR)) {
				strb.append(ta.get(ChronoField.YEAR));
				if (ta.isSupported(ChronoField.MONTH_OF_YEAR)) {
					strb.append("-");
					strb.append(ta.get(ChronoField.MONTH_OF_YEAR));
					if (ta.isSupported(ChronoField.DAY_OF_MONTH)) {
						strb.append("-");
						strb.append(ta.get(ChronoField.DAY_OF_MONTH));
						if (ta.isSupported(ChronoField.HOUR_OF_DAY)) {
							strb.append("T");
							strb.append(ta.get(ChronoField.HOUR_OF_DAY));
							if (ta.isSupported(ChronoField.MINUTE_OF_HOUR)) {
								strb.append(":");
								strb.append(ta.get(ChronoField.MINUTE_OF_HOUR));
								if (ta.isSupported(ChronoField.SECOND_OF_MINUTE)) {
									strb.append(":");
									strb.append(ta.get(ChronoField.SECOND_OF_MINUTE));
								}
							}
						}
					}
				}
			}
			printValue(w, "date", strb.toString());
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

}
