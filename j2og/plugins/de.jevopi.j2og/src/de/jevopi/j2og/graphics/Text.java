package de.jevopi.j2og.graphics;

import de.jevopi.j2og.graphics.properties.ColorTbl;

public class Text extends Element {
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_JUSTIFY = 3;

	public String text;
	/** Horizontal alignment: left, center (default), right, justify */
	public Integer align = null;

	StringBuilder m_rawText;

	private static String NL = "\\\n";

	public Text() {
		this("");
	}

	public Text(String text) {
		m_rawText = new StringBuilder(text);
		updateText();

	}

	private void updateText() {
		this.text = "{\\rtf1\\ansi\\ansicpg1252\\cocoartf1348\\cocoasubrtf170\\cocoascreenfonts1{\\fonttbl\\f0\\fswiss\\fcharset0 Helvetica;}"
				+ ColorTbl.BLACK
				+ "\\pard\\tx560\\tx1120\\tx1680\\tx2240\\tx2800\\tx3360\\tx3920\\tx4480\\tx5040\\tx5600\\tx6160\\tx6720\\qc"
				+ "\\f0" + "\\fs24 \\cf0 " + m_rawText + "}";
	}

	public void append(String text) {
		m_rawText.append(text);
		updateText();
	}

	public void appendBold(String text) {
		m_rawText.append("\\b " + text + "\\b0 ");
		updateText();
	}

	public void appendItalic(String text) {
		m_rawText.append("\\i " + text + "\\i0 ");
		updateText();
	}

	public void appendNL() {
		m_rawText.append(NL);
	}

	public int lines() {
		int i = 0;
		int hits = 0;
		while (i < m_rawText.length() && i >= 0) {
			i++;
			i = m_rawText.indexOf(NL, i);
			hits++;
		}
		return hits;
	}

}
