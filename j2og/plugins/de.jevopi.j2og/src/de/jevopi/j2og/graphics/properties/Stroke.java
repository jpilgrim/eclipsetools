package de.jevopi.j2og.graphics.properties;

import de.jevopi.j2og.graphics.decorators.Arrow;

public class Stroke extends Switchable {
	public final static Stroke NO = Switchable.NO(new Stroke());

	public static final Integer TYPE_STRAIGHT = null;
	public static final int TYPE_ORTHOGONAL = 2;

	public static final Integer PATTERN_SOLID = null;
	public static final int PATTERN_DASHED = 1;

	public final static String NONE = "0";

	public Arrow headArrow = Arrow.None;
	public Arrow tailArrow = Arrow.None;
	/** single */
	public Integer lineType = TYPE_STRAIGHT;
	public Integer pattern;
	public Boolean legacy = true;

	public Color color = null;

}
