package de.jevopi.j2og.graphics;

import java.util.ArrayList;

public class TableGroup extends Graphic {

	public final ArrayList<Graphic> graphics = new ArrayList<>();
	public String groupConnect = "YES";
	public final ArrayList<Integer> gridH = new ArrayList<>();

	public void add(Graphic graphic) {
		graphics.add(graphic);
		gridH.add(graphic.ID);
	}
}
