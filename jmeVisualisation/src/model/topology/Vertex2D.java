package model.topology;

import model.geometry.Point2D;

/**
 * simplex 0
 */
public class Vertex2D {

	private Point2D p;

	/**
	 * create a simplex 0 for the point
	 *
	 * @param p
	 */
	public Vertex2D(Point2D p) {
		this.p = p;
	}

	/**
	 * get the point
	 *
	 * @return point
	 */
	public Point2D getP() {
		return p;
	}

}
