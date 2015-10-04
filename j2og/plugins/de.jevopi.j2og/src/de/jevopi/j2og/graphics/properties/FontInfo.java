package de.jevopi.j2og.graphics.properties;

import de.jevopi.j2og.graphics.Element;

public class FontInfo extends Element {
	public Color color = new Color();
	public String font;
	public double size;

	public FontInfo(String font, double size) {
		this.font = font;
		this.size = size;
	}

}
