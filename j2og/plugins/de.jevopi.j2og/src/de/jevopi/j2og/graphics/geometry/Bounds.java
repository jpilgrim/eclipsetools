package de.jevopi.j2og.graphics.geometry;

public class Bounds {

	public Point pos;
	public Point size;

	public Bounds(Point pos, Point size) {
		this.pos = pos;
		this.size = size;
	}

	@Override
	public String toString() {
		return "{{" + (int) pos.x + ", " + (int) pos.y + "}, {" + (int) size.x + ", " + (int) size.y + "}}";
	}

}
