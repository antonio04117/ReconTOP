package model.topology;

import java.math.BigDecimal;
import java.util.ArrayList;

import model.geometry.Orient3D;
import model.geometry.OrientOrientation;
import model.geometry.Point3D;

/**
 * a triangle consisting of three edges
 */
public class Triangle3D {

	private ArrayList<Vertex3D> vertices = new ArrayList<>(3);
	private Triangle3D[] neighbors = new Triangle3D[3];

	/**
	 * create a new simplex 2
	 *
	 * @param p1 point 1
	 * @param p2 point 2
	 * @param p3 point 3
	 */
	public Triangle3D(Vertex3D p1, Vertex3D p2, Vertex3D p3) {

		vertices.add(p1);
		vertices.add(p2);
		vertices.add(p3);
	}

	// TODO adapt
	/**
	 * orientates triangle ccw
	 */
	public void orientateCCW() {
		Triangle3D newTriangle = this;

		OrientOrientation orientation = Orient3D.orientExact(newTriangle.getP0().getP().getX(),
				newTriangle.getP0().getP().getY(), newTriangle.getP1().getP().getX(), newTriangle.getP1().getP().getY(),
				newTriangle.getP2().getP().getX(), newTriangle.getP2().getP().getY());

		if (orientation != OrientOrientation.CCW) {
			if (orientation == OrientOrientation.COPLANAR) {
				throw new IllegalArgumentException("Point is not added! The resulting triangle would be zero");
			}

			// if newTriangle not oriented correctly -> reorganize triangle
			newTriangle = new Triangle3D(newTriangle.getP0(), newTriangle.getP2(), newTriangle.getP1());
		}

		vertices.set(0, newTriangle.getP0());
		vertices.set(1, newTriangle.getP1());
		vertices.set(2, newTriangle.getP2());
	}

	// TODO adapt
	/**
	 * tells if triangle is ccw
	 *
	 * @return boolean
	 */
	public boolean isCCW() {

		if (Orient3D.orientExact(getP0().getP().getX(), getP0().getP().getY(), getP1().getP().getX(),
				getP1().getP().getY(), getP2().getP().getX(), getP2().getP().getY()) == OrientOrientation.CCW) {
			return true;
		}

		return false;
	}

	/**
	 * modify two triangles
	 *
	 * @param s1    first simplex
	 * @param s2    second simplex
	 * @param p1    the third point of the first triangle
	 * @param p2    the third point of the second triangle
	 * @param start the first point of the common edge
	 * @param end   the second point of the common edge
	 */
	public static void flip(Triangle3D s1, Triangle3D s2, Vertex3D p1, Vertex3D p2, Vertex3D start, Vertex3D end) {

		s1.vertices.set(0, p1);
		s1.vertices.set(1, p2);
		s1.vertices.set(2, end);

		s2.vertices.set(0, p2);
		s2.vertices.set(1, p1);
		s2.vertices.set(2, start);

	}

	// TODO adapt
	/**
	 * Computes, whether a point is inside a triangle despite of its orientation
	 * (does not use exact arithmetic)
	 *
	 * @param coordinate
	 * @return true if the coordinate is inside (not on border)
	 */
	public boolean isCoordinateInside(double[] coordinate) {
		double signA = Orient3D.orientDet(coordinate[0], coordinate[1], getP0().getP().getXDouble(),
				getP0().getP().getYDouble(), getP1().getP().getXDouble(), getP1().getP().getYDouble());
		double signB = Orient3D.orientDet(coordinate[0], coordinate[1], getP1().getP().getXDouble(),
				getP1().getP().getYDouble(), getP2().getP().getXDouble(), getP2().getP().getYDouble());

		if ((signA >= 0l && signB <= 0l) || (signA <= 0l && signB >= 0l)) {
			return false;
		}

		double signC = Orient3D.orientDet(coordinate[0], coordinate[1], getP2().getP().getXDouble(),
				getP2().getP().getYDouble(), getP0().getP().getXDouble(), getP0().getP().getYDouble());

		if ((signA >= 0l && signC <= 0l) || (signA <= 0l && signC >= 0l)) {
			return false;
		}

		return true;
	}

	// TODO adapt
	/**
	 * computes whether a point is inside a triangle despite of its orientation
	 * (using exact arithmetic)
	 *
	 * @param p
	 * @return true if p is inside of the triangle (not on border)
	 */
	public boolean isPointInside(Point3D p) {

		OrientOrientation orientationA = Orient3D.orientExact(new BigDecimal(p.getX()), new BigDecimal(p.getY()),
				new BigDecimal(getP0().getP().getX()), new BigDecimal(getP0().getP().getY()),
				new BigDecimal(getP1().getP().getX()), new BigDecimal(getP1().getP().getY()));

		if (orientationA == OrientOrientation.COPLANAR) {
			return false;
		}

		OrientOrientation orientationB = Orient3D.orientExact(new BigDecimal(p.getX()), new BigDecimal(p.getY()),
				new BigDecimal(getP1().getP().getX()), new BigDecimal(getP1().getP().getY()),
				new BigDecimal(getP2().getP().getX()), new BigDecimal(getP2().getP().getY()));

		if (orientationB == OrientOrientation.COPLANAR || orientationA != orientationB) {
			return false;
		}

		OrientOrientation orientationC = Orient3D.orientExact(new BigDecimal(p.getX()), new BigDecimal(p.getY()),
				new BigDecimal(getP2().getP().getX()), new BigDecimal(getP2().getP().getY()),
				new BigDecimal(getP0().getP().getX()), new BigDecimal(getP0().getP().getY()));

		if (orientationC == OrientOrientation.COPLANAR || orientationA != orientationC) {
			return false;
		}

		return true;
	}

	/**
	 * computes whether a point is on a vertex of a triangle
	 *
	 * @param p
	 * @return
	 */
	public boolean isPointOnVertex(Point3D p) {
		return getP0().getP().equals(p) || getP1().getP().equals(p) || getP2().getP().equals(p);
	}

	/**
	 * computes whether a point is on an edge of a triangle
	 *
	 * @param p
	 * @return
	 */
	public boolean isPointOnEdge(Point3D p) {
		return WindingNumber.isOnEdge(getP0(), getP1(), new Vertex3D(p))
				|| WindingNumber.isOnEdge(getP0(), getP2(), new Vertex3D(p))
				|| WindingNumber.isOnEdge(getP1(), getP2(), new Vertex3D(p));
	}

	/**
	 * checks if a given triangle has two points that equal the given edge's start
	 * and end
	 *
	 * @param triangle
	 * @param edge
	 * @return
	 */
	public boolean containsEdge(Edge3D e) {
		if (getPoints().contains(e.getStart().getP()) && getPoints().contains(e.getEnd().getP())) {
			return true;
		}
		return false;
	}

	/**
	 * calculate Center of the Triangle
	 *
	 * @return
	 */
	public double[] calculateCenter() {
		double centerX = (getP0().getP().getXDouble() + getP1().getP().getXDouble() + getP2().getP().getXDouble())
				/ 3.0;
		double centerY = (getP0().getP().getYDouble() + getP1().getP().getYDouble() + getP2().getP().getYDouble())
				/ 3.0;
		double centerZ = (getP0().getP().getZDouble() + getP1().getP().getZDouble() + getP2().getP().getZDouble())
				/ 3.0;
		return new double[] { centerX, centerY, centerZ };
	}

	/**
	 * get the third point
	 *
	 * @param p1
	 * @param p2
	 * @return the third point
	 */
	public Vertex3D getThirdPoint(Vertex3D p1, Vertex3D p2) {
		int i = getIndexOfThirdPoint(p1, p2);
		return vertices.get(i);

	}

	/**
	 * get the index of the third point, which is not laying on the given edge
	 *
	 * @param p1
	 * @param p2
	 * @return position of the third point
	 */
	public int getIndexOfThirdPoint(Vertex3D p1, Vertex3D p2) {
		if (getP0().equals(p1)) {
			if (getP1().equals(p2)) {
				return 2;
			} else {
				return 1;
			}
		} else {
			if (getP1().equals(p1)) {
				if (getP0().equals(p2)) {
					return 2;
				} else {
					return 0;
				}
			} else {
				if (getP0().equals(p2)) {
					return 1;
				} else {
					return 0;
				}
			}
		}

	}

	/**
	 * get points of triangle
	 *
	 * @return
	 */
	public ArrayList<Point3D> getPoints() {
		ArrayList<Point3D> points = new ArrayList<>();
		for (Vertex3D vertex3d : vertices) {
			points.add(vertex3d.getP());
		}

		return points;
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

	/**
	 * get neighbors
	 *
	 * @return
	 */
	public Triangle3D[] getNeighbors() {
		return neighbors;
	}

	/**
	 * set neighbor at index
	 *
	 * @param index
	 * @param t
	 */
	public void setNeighborI(int index, Triangle3D t) {
		neighbors[index] = t;
	}

}
