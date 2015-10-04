package de.jevopi.j2og.graphics;

import java.util.ArrayList;
import java.util.Collection;

import de.jevopi.j2og.graphics.geometry.Point;

public class TableGroup extends Graphic {

	public final ArrayList<Graphic> graphics = new ArrayList<>();
	public String groupConnect = "YES";
	public final ArrayList<Integer> gridH = new ArrayList<>();
	public Collection<Point> magnets;

	public void add(Graphic graphic) {
		graphics.add(graphic);
		gridH.add(graphic.ID);
	}

	@Override
	public String toString() {
		return super.toString() + " " + gridH;
	}
}
