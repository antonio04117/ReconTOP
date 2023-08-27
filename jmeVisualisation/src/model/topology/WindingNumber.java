package model.topology;

/**
 * winding number
 */
public abstract class WindingNumber {

	/**
	 * Tests, whether a point is left, right or on the line between p0, p1.
	 *
	 * @param p0
	 * @param p1
	 * @param point
	 * @return
	 */
	public static EOnLine isLeft(Vertex2D p0, Vertex2D p1, Vertex2D point) {

		double v0 = p1.getP().getXDouble() - p0.getP().getXDouble();
		double v1 = p1.getP().getYDouble() - p0.getP().getYDouble();

		double w0 = point.getP().getXDouble() - p0.getP().getXDouble();
		double w1 = point.getP().getYDouble() - p0.getP().getYDouble();

		// cross product turns 90 ° clockwise
		double n = v1 * w0 - v0 * w1;

		if (n > 0) {
			return EOnLine.RIGHT;
		} else if (n < 0) {
			return EOnLine.LEFT;
		} else {
			return EOnLine.ON;
		}
	}

	/**
	 * calculates if a point is on edge between p0 and p1
	 *
	 * @param p0
	 * @param p1
	 * @param point
	 * @return
	 */
	public static boolean isOnEdge(Vertex2D p0, Vertex2D p1, Vertex2D point) {
		EOnLine on = WindingNumber.isLeft(p0, p1, point);

		double minX = Math.min(p0.getP().getXDouble(), p1.getP().getXDouble());
		double maxX = Math.max(p0.getP().getXDouble(), p1.getP().getXDouble());
		double minY = Math.min(p0.getP().getYDouble(), p1.getP().getYDouble());
		double maxY = Math.max(p0.getP().getYDouble(), p1.getP().getYDouble());

		double x = point.getP().getXDouble();
		double y = point.getP().getYDouble();

		double epsilon = 0.01;

		if (x >= minX - epsilon && x <= maxX + epsilon && y >= minY - epsilon && y <= maxY + epsilon) {
			if (on == EOnLine.ON) {
				return true;
			}
		}

		return false;
	}

}
