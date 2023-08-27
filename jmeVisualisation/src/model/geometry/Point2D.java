package model.geometry;

/**
 * Point in 2d
 */
public class Point2D {
	// coordinates
	private int x, y;

	/**
	 * create a point
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point2D) {
			Point2D other = (Point2D) obj;
			return other.x == x && other.y == y;
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
		return "Point " + x + "/" + y;
	}
}
