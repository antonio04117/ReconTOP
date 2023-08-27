package model.topology;

import model.geometry.Point3D;

/**
 * simplex 0
 */
public class Vertex2D {

	private Point3D p;

	/**
	 * create a simplex 0 for the point
	 *
	 * @param p
	 */
	public Vertex2D(Point3D p) {
		this.p = p;
	}

	/**
	 * get the point
	 *
	 * @return point
	 */
	public Point3D getP() {
		return p;
	}

}
