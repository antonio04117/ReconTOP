package model.topology;

import java.util.ArrayList;

public class Tetrahedron3D {

	private ArrayList<Vertex3D> vertices = new ArrayList<Vertex3D>(4);

	/**
	 * create a new simplex 3
	 * 
	 * @param p0
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Tetrahedron3D(Vertex3D p0, Vertex3D p1, Vertex3D p2, Vertex3D p3) {
		vertices.add(p0);
		vertices.add(p1);
		vertices.add(p2);
		vertices.add(p3);
	}

	/**
	 * @return point 0
	 */
	public Vertex3D getP0() {
		return vertices.get(0);
	}

	/**
	 * @return point 1
	 */
	public Vertex3D getP1() {
		return vertices.get(1);
	}

	/**
	 * @return point 2
	 */
	public Vertex3D getP2() {
		return vertices.get(2);
	}

	/**
	 * @return point 3
	 */
	public Vertex3D getP3() {
		return vertices.get(3);
	}

	// method can be used if points of two Tetrahedron3D have same values means
	// equal
	// without this method the two Tetrahedron3D have to have the exact id -> same
	// values of the points is not enough to be equal
//	public boolean equals(Tetrahedron3D tet) {
//		if (getP0().getP().equals(tet.getP0().getP()) && getP1().getP().equals(tet.getP1().getP())
//				&& getP2().getP().equals(tet.getP2().getP()) && getP3().getP().equals(tet.getP3().getP())) {
//			return true;
//		}
//		return false;
//	}

}
