package de.jevopi.j2og.graphics;

import de.jevopi.j2og.graphics.properties.Color;
import de.jevopi.j2og.graphics.properties.ColorTbl;
import de.jevopi.plist.PLDict;

/**
 * Poor mans rtf implementation, just what we need for styling the classifiers.
 *
 * For more information about rtf, see
 * <a href="https://www.microsoft.com/en-us/download/confirmation.aspx?id=10725">Word2007RTFSpec9</a>
 * and <a href="https://developer.apple.com/library/mac/documentation/Cocoa/Conceptual/AttributedStrings/Tasks/RTFAndAttrStrings.html#//apple_ref/doc/uid/20000164-154922">Apple's extensions</a>.
 *
 */
public class Text extends Element {
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_JUSTIFY = 3;

	public String text;
	/** Horizontal alignment: left, center (default), right, justify */
	public Integer align = null;

	StringBuilder m_rawText;
	private int size = 12;
	private Color color;

	public static String NL = "\\\n";

	/** Left angle quotes -- Guillemets left */
	public static String LAQUO = "\\'ab";
	/** Right angle quotes -- Guillemets right */
	public static String RAQUO = "\\'bb";


	public Text() {
		this("");
	}

	public Text(String text) {
		m_rawText = new StringBuilder(text);
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public void setSize(int size) {
		this.size = size;
	}

	private void updateText() {
		String justifyFormat = "";
		if (align == null || align == ALIGN_CENTER) {
			justifyFormat = "\\qc";
		}
		this.text = "{\\rtf1\\ansi\\ansicpg1252\\cocoartf1348\\cocoasubrtf170\n\\cocoascreenfonts1{\\fonttbl\\f0\\fswiss\\fcharset0 Helvetica;}\n"
				+ (color==null ? ColorTbl.BLACK : color.toFontString())
				+ "\n"
				+ "\\pard\\tx560\\tx1120\\tx1680\\tx2240\\tx2800\\tx3360\\tx3920\\tx4480\\tx5040\\tx5600\\tx6160\\tx6720"
				+ justifyFormat + "\n\n" + "\\f0" + "\\fs" + (size * 2)
				+ (color==null ? " \\cf0 " : " \\cf2 ") + m_rawText + "}";
	}

	public void appendSmall(String text) {
		m_rawText.append("\\fs" + getSmallFont()*2 + text + "\\fs" + (size * 2) );
	}

	public void appendSmallBold(String text) {
		m_rawText.append("\\fs" + getSmallFont()*2 + " \\b " + text + "\\fs" + (size * 2) + " \\b0");
	}

	private int getSmallFont() {
		int small =(int) ( ((float)size) * 0.66);
		return small;
	}


	public void append(String text) {
		m_rawText.append(text);
	}

	public void appendBold(String text) {
		m_rawText.append("\\b " + text + "\\b0 ");
	}

	public void appendItalic(String text) {
		m_rawText.append("\\i " + text + "\\i0 ");
	}

	public void appendNL() {
		m_rawText.append(NL);
	}

	public void setColor(Color color) {
		this.color = color;
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

	@Override
	protected void addFields(PLDict dict) {
		updateText();
		super.addFields(dict);
	}




}
