package de.jevopi.j2og.graphics.properties;

public class ColorTbl {

	public final static ColorTbl BLACK = new ColorTbl();

	public final static String[] COLORS = { "red", "green", "blue" };

	public int[] back = { 255, 255, 255 };
	public int[] front = null;

	ColorTbl() {
	}

	ColorTbl(int r, int g, int b) {
		front = new int[] { r, g, b };
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder("{\\colortbl;");
		append(strb, back);
		if (front != null) {
			append(strb, front);
		}
		strb.append("}");
		return strb.toString();
	}

	private void append(StringBuilder strb, int[] rgb) {
		for (int i = 0; i < 3; i++) {
			strb.append("\\");
			strb.append(COLORS[i]);
			strb.append(rgb[i]);
		}
		strb.append(";");
	}
}
