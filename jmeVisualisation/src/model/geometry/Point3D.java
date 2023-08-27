package model.geometry;

/**
 * Point in 2d
 */
public class Point3D {
	// coordinates
	private int x, y, z;

	/**
	 * create a point
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * get x-coordinate
	 *
	 * @return x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * get y-coordinate
	 *
	 * @return y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * get z-coordinate
	 *
	 * @return z-coordinate
	 */
	public int getZ() {
		return z;
	}

	/**
	 * get x-coordinate as double value
	 *
	 * @return
	 */
	public double getXDouble() {
		return x;
	}

	/**
	 * get y-coordinate as double value
	 *
	 * @return
	 */
	public double getYDouble() {
		return y;
	}

	/**
	 * get z-coordinate as double value
	 *
	 * @return
	 */
	public double getZDouble() {
		return z;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point3D) {
			Point3D other = (Point3D) obj;
			return other.x == x && other.y == y && other.z == z;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point " + x + "/" + y + "/" + z;
	}
}
