package de.jevopi.j2og.graphics.geometry;

public class Bounds {

	public Point p1;
	public Point p2;

	public Bounds(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public String toString() {
		return "{" + p1 + ", " + p2 + "}";
	}

}
