package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import model.topology.Edge3D;
import model.topology.Tetrahedron3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;

public class Mesh {

	private Map<Integer, Tetrahedron3D> mapTet;
	private Map<Integer, HashSet<Triangle3D>> mapTri;
	private Map<Integer, HashSet<Edge3D>> mapEdg;
	private Map<Integer, HashSet<Vertex3D>> mapVer;

	private Integer tetCount = 0;

	public Mesh() {
		mapTet = new HashMap<Integer, Tetrahedron3D>();
		mapTri = new HashMap<Integer, HashSet<Triangle3D>>();
		mapEdg = new HashMap<Integer, HashSet<Edge3D>>();
		mapVer = new HashMap<Integer, HashSet<Vertex3D>>();
	}

	public void addTet(Tetrahedron3D tet) {
		// add tet to tet map with id of tet
		mapTet.put(tetCount, tet);

		// create ccw triangles
		Triangle3D tri0 = new Triangle3D(tet.getP0(), tet.getP1(), tet.getP2());
		Triangle3D tri1 = new Triangle3D(tet.getP0(), tet.getP1(), tet.getP3());
		Triangle3D tri2 = new Triangle3D(tet.getP0(), tet.getP2(), tet.getP3());
		Triangle3D tri3 = new Triangle3D(tet.getP1(), tet.getP2(), tet.getP3());
		// set of triangles that belong to tetrahedron
		HashSet<Triangle3D> setTri = new HashSet<Triangle3D>();
		setTri.add(tri0);
		setTri.add(tri1);
		setTri.add(tri2);
		setTri.add(tri3);
		// add tri to tri map with id of according tet
		mapTri.put(tetCount, setTri);

		// create edges of tet
		// TODO check if there are exactly 6 edges in Set
		HashSet<Edge3D> setEdg = new HashSet<Edge3D>();
		setEdg.add(new Edge3D(tri0.getP0(), tri0.getP1(), false));
		setEdg.add(new Edge3D(tri0.getP1(), tri0.getP2(), false));
		setEdg.add(new Edge3D(tri0.getP2(), tri0.getP0(), false));
		setEdg.add(new Edge3D(tri1.getP0(), tri1.getP1(), false));
		setEdg.add(new Edge3D(tri1.getP1(), tri1.getP2(), false));
		setEdg.add(new Edge3D(tri1.getP2(), tri1.getP0(), false));
		setEdg.add(new Edge3D(tri2.getP0(), tri2.getP1(), false));
		setEdg.add(new Edge3D(tri2.getP1(), tri2.getP2(), false));
		setEdg.add(new Edge3D(tri2.getP2(), tri2.getP0(), false));
		setEdg.add(new Edge3D(tri3.getP0(), tri3.getP1(), false));
		setEdg.add(new Edge3D(tri3.getP1(), tri3.getP2(), false));
		setEdg.add(new Edge3D(tri3.getP2(), tri3.getP0(), false));
		// add edges to edg map with id of according tet
		mapEdg.put(tetCount, setEdg);

		// create vertices of tet
		HashSet<Vertex3D> setVer = new HashSet<Vertex3D>();
		setVer.add(tet.getP0());
		setVer.add(tet.getP1());
		setVer.add(tet.getP2());
		setVer.add(tet.getP3());
		// add vertices to ver map with id of according tet
		mapVer.put(tetCount, setVer);

		tetCount++;
	}

}
