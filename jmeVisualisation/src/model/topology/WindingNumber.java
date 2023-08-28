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
	public static EOnLine isLeft(Vertex3D p0, Vertex3D p1, Vertex3D point) {
		double v0 = p1.getP().getX() - p0.getP().getX();
		double v1 = p1.getP().getY() - p0.getP().getY();
		double v2 = p1.getP().getZ() - p0.getP().getZ();

		double w0 = point.getP().getX() - p0.getP().getX();
		double w1 = point.getP().getY() - p0.getP().getY();
		double w2 = point.getP().getZ() - p0.getP().getZ();

		// Calculate the cross product of vectors v0 and w0
		double n0 = v1 * w2 - v2 * w1;
		double n1 = v2 * w0 - v0 * w2;
		double n2 = v0 * w1 - v1 * w0;

		// Calculate the dot product of the cross product and vector v0
		double dotProduct = n0 * v0 + n1 * v1 + n2 * v2;

		if (dotProduct > 0) {
			return EOnLine.RIGHT;
		} else if (dotProduct < 0) {
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
	public static boolean isOnEdge(Vertex3D p0, Vertex3D p1, Vertex3D point) {
		EOnLine on = WindingNumber.isLeft(p0, p1, point);

		double minX = Math.min(p0.getP().getXDouble(), p1.getP().getXDouble());
		double maxX = Math.max(p0.getP().getXDouble(), p1.getP().getXDouble());
		double minY = Math.min(p0.getP().getYDouble(), p1.getP().getYDouble());
		double maxY = Math.max(p0.getP().getYDouble(), p1.getP().getYDouble());
		double minZ = Math.min(p0.getP().getZDouble(), p1.getP().getZDouble());
		double maxZ = Math.max(p0.getP().getZDouble(), p1.getP().getZDouble());

		double x = point.getP().getXDouble();
		double y = point.getP().getYDouble();
		double z = point.getP().getZDouble();

		double epsilon = 0.01;

		if (x >= minX - epsilon && x <= maxX + epsilon && y >= minY - epsilon && y <= maxY + epsilon
				&& z >= minZ - epsilon && z <= maxZ + epsilon) {
			if (on == EOnLine.ON) {
				return true;
			}
		}

		return false;
	}

}
