package model.topology;

import java.util.ArrayList;

/**
 * a triangle consisting of three edges
 */
public class Triangle3D {

	private boolean isBoundary = false;
	private ArrayList<Vertex3D> vertices = new ArrayList<>(3);

	/**
	 * create a new simplex 2 that is ccw
	 *
	 * @param p0 point 1
	 * @param p1 point 2
	 * @param p2 point 3
	 */
	public Triangle3D(Vertex3D p0, Vertex3D p1, Vertex3D p2) {

		vertices.add(p0);
		vertices.add(p1);
		vertices.add(p2);

	}

	/**
	 * get point 1
	 *
	 * @return point 1
	 */
	public Vertex3D getP0() {
		return vertices.get(0);
	}

	/**
	 * get point 2
	 *
	 * @return point 2
	 */
	public Vertex3D getP1() {
		return vertices.get(1);
	}

	/**
	 * get point 3
	 *
	 * @return point 3
	 */
	public Vertex3D getP2() {
		return vertices.get(2);
	}

	public boolean isBoundary() {
		return isBoundary;
	}

	public void setBoundary(boolean boundary) {
		this.isBoundary = boundary;
	}

}
