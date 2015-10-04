package de.jevopi.j2og.graphics.geometry;

public class Point {

	public double x;
	public double y;

	public Point() {
		this(0, 0);
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this(p.x, p.y);
	}

	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
	}

}
