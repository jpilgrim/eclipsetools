package de.jevopi.j2og.graphics.properties;

import de.jevopi.j2og.graphics.Element;

public class Color extends Element {

	public final static Color CONCRETE = new Color("0.647059", "0.647059", "0.647059");

	public final String _w;
	public final String _r;
	public final String _g;
	public final String _b;

	public Color() {
		_w = "0";
		_r = null;
		_g = null;
		_b = null;
	}

	public Color(String r, String g, String b) {
		_w = null;
		this._r = r;
		this._g = g;
		this._b = b;
	}

	/**
	 * Returns color table containing white (cf1) and the given color (cf2).
	 */
	public String toFontString() {
		return "{\\colortbl;\\red255\\green255\\blue255;" //
				+ "\\red" + ((int) (Float.parseFloat(_r) * 255)) //
				+ "\\green" + ((int) (Float.parseFloat(_g) * 255)) //
				+ "\\blue" + ((int) (Float.parseFloat(_b) * 255)) //
				+ ";}";
	}

}
