package de.jevopi.j2og.graphics.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Magnets {

	public static final Collection<Point> NONE = null;

	public static final Collection<Point> PER_SIDE_1 = Collections.unmodifiableCollection(new ArrayList<>(Arrays
			.asList(//
			new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0)//
			)));

	public static final Collection<Point> PER_SIDE_3 = Collections.unmodifiableCollection(new ArrayList<>(Arrays
			.asList(//
			new Point(-0.59628479784302701, -1.1925696134567261),
					new Point(1.9868215517249155e-08, -1.3333333730697632), new Point(0.59628487781105122,
							-1.1925696134567261), new Point(1.1925696134567261, -0.59628480672836304), new Point(
							1.3333333730697632, 1.5894572413799324e-07), new Point(1.1925696134567261,
							0.59628473564567486), new Point(0.59628465308492196, 1.1925697326660156), new Point(0,
							1.3333333730697632), new Point(-0.5962849488937394, 1.1925696134567261), new Point(
							-1.1925697326660156, 0.5962844398368361), new Point(-1.3333333730697632,
							-6.3578289655197295e-07), new Point(-1.1925696134567261, -0.59628480672836304))));

}
