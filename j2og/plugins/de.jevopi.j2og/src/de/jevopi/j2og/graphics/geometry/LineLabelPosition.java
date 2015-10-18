package de.jevopi.j2og.graphics.geometry;

import de.jevopi.j2og.graphics.Element;
import de.jevopi.j2og.graphics.LineGraphic;

public class LineLabelPosition extends Element {

	/** referenced line */
	public int ID;
	public double offset;
	public double position;
	public int rotationType = 0;

	public LineLabelPosition(LineGraphic lineGraphic, double offset, double position) {
		ID = lineGraphic.ID;
		this.offset = offset;
		this.position = position;
	}

}
