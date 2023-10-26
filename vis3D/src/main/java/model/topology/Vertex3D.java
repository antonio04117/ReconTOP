package model.topology;

import model.geometry.Point3D;

/**
 * simplex 0
 */
public class Vertex3D {

	private Point3D p;
	private boolean isBoundary;

	/**
	 * create a simplex 0 for the point
	 *
	 * @param p
	 */
	public Vertex3D(Point3D p, boolean isBoundary) {
		this.p = p;
		this.isBoundary = isBoundary;
	}

	/**
	 * get the point
	 *
	 * @return point
	 */
	public Point3D getP() {
		return p;
	}

	public boolean isBoundary() {
		return isBoundary;
	}

	public void setBoundary(boolean isBoundary) {
		this.isBoundary = isBoundary;
	}

}
