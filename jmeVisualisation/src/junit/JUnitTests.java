package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.Mesh;
import model.geometry.Point3D;
import model.topology.Tetrahedron3D;
import model.topology.Vertex3D;

class JUnitTests {

	@Test
	void testGetIdOfTet() {

		Mesh mesh = new Mesh();

		Vertex3D[] cube = new Vertex3D[] { new Vertex3D(new Point3D(0, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 0), false), new Vertex3D(new Point3D(10, 10, 0), false),
				new Vertex3D(new Point3D(0, 0, 10), false), new Vertex3D(new Point3D(10, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 10), false), new Vertex3D(new Point3D(10, 10, 10), false),
				new Vertex3D(new Point3D(10, 0, 10), false) };

		// a square of tetrahedrons
		Tetrahedron3D tet0 = new Tetrahedron3D(cube[0], cube[1], cube[2], cube[3]);
		mesh.addTet(tet0);
		Tetrahedron3D tet1 = new Tetrahedron3D(cube[0], cube[4], cube[2], cube[3]);
		mesh.addTet(tet1);
		Tetrahedron3D tet2 = new Tetrahedron3D(cube[1], cube[3], cube[5], cube[6]);
		mesh.addTet(tet2);
		Tetrahedron3D tet3 = new Tetrahedron3D(cube[1], cube[3], cube[2], cube[6]);
		mesh.addTet(tet3);
		Tetrahedron3D tet4 = new Tetrahedron3D(cube[3], cube[7], cube[2], cube[6]);
		mesh.addTet(tet4);
		Tetrahedron3D tet5 = new Tetrahedron3D(cube[3], cube[4], cube[2], cube[7]);
		mesh.addTet(tet5);

		// mark triangles that are boundary
		mesh.markBoundary();

		System.out.println(mesh.getIdOfTet(tet0));
		System.out.println(mesh.getIdOfTet(tet1));
		System.out.println(mesh.getIdOfTet(tet2));
		System.out.println(mesh.getIdOfTet(tet3));
		System.out.println(mesh.getIdOfTet(tet4));
		System.out.println(mesh.getIdOfTet(tet5));

		assertTrue(Integer.valueOf(0) == mesh.getIdOfTet(tet0));
		assertTrue(Integer.valueOf(1) == mesh.getIdOfTet(tet1));
		assertTrue(Integer.valueOf(2) == mesh.getIdOfTet(tet2));
		assertTrue(Integer.valueOf(3) == mesh.getIdOfTet(tet3));
		assertTrue(Integer.valueOf(4) == mesh.getIdOfTet(tet4));
		assertTrue(Integer.valueOf(5) == mesh.getIdOfTet(tet5));

	}

	@Test
	void testContainsKey() {
		// test if newly generated Object counts as conatainsKey

		Map<Integer, Tetrahedron3D> tetMap = new LinkedHashMap<Integer, Tetrahedron3D>();

		Vertex3D[] cube = new Vertex3D[] { new Vertex3D(new Point3D(0, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 0), false), new Vertex3D(new Point3D(10, 10, 0), false),
				new Vertex3D(new Point3D(0, 0, 10), false), new Vertex3D(new Point3D(10, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 10), false), new Vertex3D(new Point3D(10, 10, 10), false),
				new Vertex3D(new Point3D(10, 0, 10), false) };

		tetMap.put(Integer.valueOf(1), new Tetrahedron3D(cube[0], cube[1], cube[2], cube[3]));

		assertTrue(tetMap.containsKey(Integer.valueOf(1)));

	}

}
