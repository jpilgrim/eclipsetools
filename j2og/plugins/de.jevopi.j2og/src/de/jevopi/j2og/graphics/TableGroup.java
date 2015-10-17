package de.jevopi.j2og.graphics;

import java.util.ArrayList;
import java.util.Collection;

import de.jevopi.j2og.graphics.geometry.Point;

public class TableGroup extends Graphic {

	public final static ArrayList<?> empty = new ArrayList<>();

	public final ArrayList<Graphic> graphics = new ArrayList<>();
	public String groupConnect = "YES";
	public final ArrayList<Object> gridH = new ArrayList<>();
	public Collection<Point> magnets;

	public TableGroup() {
		gridH.add(empty);
	}

	public void add(Graphic graphic) {
		graphics.add(graphic);
		gridH.remove(gridH.size() - 1);
		gridH.add(graphic.ID);
		gridH.add(empty);

	}

	@Override
	public String toString() {
		return super.toString() + " " + gridH;
	}
}
