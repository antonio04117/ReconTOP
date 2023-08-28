package model.topology;

/**
 * an edge in 2D
 */
public class Edge3D {

	private Vertex3D start, end;

	private boolean boundary = true;

	/**
	 * create an edge
	 *
	 * @param p1       start
	 * @param p2       end
	 * @param boundary
	 */
	public Edge3D(Vertex3D p1, Vertex3D p2, boolean boundary) {
		// set the points
		start = p1;
		end = p2;
		this.boundary = boundary;
	}

	/**
	 * get start
	 *
	 * @return start
	 */
	public Vertex3D getStart() {
		return start;
	}

	/**
	 * get end
	 *
	 * @return end
	 */
	public Vertex3D getEnd() {
		return end;
	}

	/**
	 * is a boundary?
	 *
	 * @return true, if it is a boundary of the network
	 */
	public boolean isBoundary() {
		return boundary;
	}

	/**
	 * set boundary
	 *
	 * @param boundary the boundary to set
	 */
	public void setBoundary(boolean boundary) {
		this.boundary = boundary;
	}

	/**
	 * flip edge
	 *
	 * @param p1
	 * @param p2
	 */
	public void flip(Vertex3D p1, Vertex3D p2) {
		start = p1;
		end = p2;
	}
}
