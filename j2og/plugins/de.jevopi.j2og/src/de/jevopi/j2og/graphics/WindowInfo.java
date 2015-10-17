package de.jevopi.j2og.graphics;

import java.util.ArrayList;

public class WindowInfo extends Element {
	public int currentSheet = 0;
	public boolean listView = false;
	public boolean rightSidebar = false;
	public boolean sidebar = false;
	public double zoom = 1;
	public ArrayList<ArrayList<Object>> zoomValues;

	public WindowInfo() {
		zoomValues = new ArrayList<>();
		ArrayList<Object> firstValues = new ArrayList<>();
		firstValues.add("Canvas 1");
		firstValues.add((double) 1);
		firstValues.add((double) 1);
		zoomValues.add(firstValues);
	}

}
