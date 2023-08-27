/**
 *
 */
package model.geometry;

import java.math.BigDecimal;

/**
 * orientation in 2D
 */
public abstract class Orient2D {

	/**
	 * Computes by double arithmetic (not exact) the determinant, whether the
	 * triangle a,b,c is in on CW or CCW oriented or coplanar
	 *
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @param cx
	 * @param cy
	 * @return negative -> CW orientation, positive -> CCW orientation, near zero ->
	 *         coplanar
	 */
	public static double orientDet(double ax, double ay, double bx, double by, double cx, double cy) {

		double a = ax - cx;
		double b = by - cy;
		double c = bx - cx;
		double d = ay - cy;

		return a * b - c * d;
	}

	/**
	 * Computes by BigDecimal (multiprecision) arithmetic the determinant, whether
	 * the triangle a,b,c is in on CW or CCW oriented or coplanar. The result is
	 * always exact, but the computation might be slow
	 *
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @param cx
	 * @param cy
	 *
	 * @return negative -> CW orientation, positive -> CCW orientation, near zero ->
	 *         coplanar
	 */
	private static BigDecimal orientExactDet(BigDecimal ax, BigDecimal ay, BigDecimal bx, BigDecimal by, BigDecimal cx,
			BigDecimal cy) {

		BigDecimal a = ax.subtract(cx);
		BigDecimal b = by.subtract(cy);
		BigDecimal c = bx.subtract(cx);
		BigDecimal d = ay.subtract(cy);

		return a.multiply(b).subtract(c.multiply(d));
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
	public static OrientOrientation orientExact(BigDecimal ax, BigDecimal ay, BigDecimal bx, BigDecimal by,
			BigDecimal cx, BigDecimal cy) {

		BigDecimal detBD = Orient2D.orientExactDet(ax, ay, bx, by, cx, cy);

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
	public static OrientOrientation orientExact(double ax, double ay, double bx, double by, double cx, double cy) {

		return Orient2D.orientExact(BigDecimal.valueOf(ax), BigDecimal.valueOf(ay), BigDecimal.valueOf(bx),
				BigDecimal.valueOf(by), BigDecimal.valueOf(cx), BigDecimal.valueOf(cy));

	}

}
