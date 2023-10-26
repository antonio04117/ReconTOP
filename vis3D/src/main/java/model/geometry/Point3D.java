package model.geometry;

/**
 * Point in 3d
 */
public class Point3D {
	// coordinates
	private int x;
	private int y;
	private int z;

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

	@Override
	public boolean equals(Object obj) {
		Point3D other = this;
		if (obj.getClass().equals(other.getClass())) {
			return other.x == x && other.y == y && other.z == z;
		}
		return false;
	}

	@Override
	public int hashCode() {

		String hashCodeString = "" + x + y + z;
		return Integer.parseInt(hashCodeString);
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
