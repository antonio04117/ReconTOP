/**
 *
 */
package model.geometry;

import java.math.BigDecimal;

/**
 * orientation in 2D
 */
public abstract class Orient3D {

	/**
	 * Computes the determinant to determine the orientation of a 3D triangle.
	 *
	 * @param ax
	 * @param ay
	 * @param az
	 * @param bx
	 * @param by
	 * @param bz
	 * @param cx
	 * @param cy
	 * @param cz
	 * @return negative -> CW orientation, positive -> CCW orientation, near zero ->
	 *         coplanar
	 */
	public static double orientDet(double ax, double ay, double az, double bx, double by, double bz, double cx,
			double cy, double cz) {

		double aX = ax - cx;
		double aY = ay - cy;
		double aZ = az - cz;
		double bX = bx - cx;
		double bY = by - cy;
		double bZ = bz - cz;

		// Calculate the cross product of vectors a and b
		double crossX = aY * bZ - aZ * bY;
		double crossY = aZ * bX - aX * bZ;
		double crossZ = aX * bY - aY * bX;

		return crossX + crossY + crossZ; // Return the sum of the cross product components
	}

	/**
	 * Computes the determinant using BigDecimal arithmetic to determine the
	 * orientation of a 3D triangle.
	 *
	 * @param ax
	 * @param ay
	 * @param az
	 * @param bx
	 * @param by
	 * @param bz
	 * @param cx
	 * @param cy
	 * @param cz
	 *
	 * @return negative -> CW orientation, positive -> CCW orientation, near zero ->
	 *         coplanar
	 */
	private static BigDecimal orientExactDet(BigDecimal ax, BigDecimal ay, BigDecimal az, BigDecimal bx, BigDecimal by,
			BigDecimal bz, BigDecimal cx, BigDecimal cy, BigDecimal cz) {

		BigDecimal aX = ax.subtract(cx);
		BigDecimal aY = ay.subtract(cy);
		BigDecimal aZ = az.subtract(cz);
		BigDecimal bX = bx.subtract(cx);
		BigDecimal bY = by.subtract(cy);
		BigDecimal bZ = bz.subtract(cz);

		// Calculate the cross product of vectors a and b
		BigDecimal crossX = aY.multiply(bZ).subtract(aZ.multiply(bY));
		BigDecimal crossY = aZ.multiply(bX).subtract(aX.multiply(bZ));
		BigDecimal crossZ = aX.multiply(bY).subtract(aY.multiply(bX));

		return crossX.add(crossY).add(crossZ); // Return the sum of the cross product components
	}

	/**
	 *
	 * Computes by BigDecimal (multiprecision), whether the triangle a,b,c is in on
	 * CW or CCW oriented or coplanar. The result ist always exact, but the
	 * computation might be slow
	 *
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @param cx
	 * @param cy
	 * @return
	 */
	public static OrientOrientation orientExact(BigDecimal ax, BigDecimal ay, BigDecimal az, BigDecimal bx,
			BigDecimal by, BigDecimal bz, BigDecimal cx, BigDecimal cy, BigDecimal cz) {

		BigDecimal detBD = Orient3D.orientExactDet(ax, ay, az, bx, by, bz, cx, cy, cz);

		int signum = detBD.signum();
		if (signum < 0) {
			return OrientOrientation.CW;
		} else if (signum > 0) {
			return OrientOrientation.CCW;
		} else {
			return OrientOrientation.COPLANAR;
		}
	}

	/**
	 *
	 * Computes by BigDecimal (multiprecision), whether the triangle a,b,c is in on
	 * CW or CCW oriented or coplanar. The result ist always exact, but the
	 * computation might be slow
	 *
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @param cx
	 * @param cy
	 * @return
	 */
	public static OrientOrientation orientExact(double ax, double ay, double az, double bx, double by, double bz,
			double cx, double cy, double cz) {

		return Orient3D.orientExact(BigDecimal.valueOf(ax), BigDecimal.valueOf(ay), BigDecimal.valueOf(az),
				BigDecimal.valueOf(bx), BigDecimal.valueOf(by), BigDecimal.valueOf(bz), BigDecimal.valueOf(cx),
				BigDecimal.valueOf(cy), BigDecimal.valueOf(cz));

	}

}
