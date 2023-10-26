package model;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.topology.Edge3D;
import model.topology.Tetrahedron3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;

public class Mesh {

	// map with index as key and objects as value
	private Map<Integer, Tetrahedron3D> mapTet;
	private Map<Integer, LinkedList<Triangle3D>> mapTri;
	private Map<Integer, LinkedList<Edge3D>> mapEdg;
	private Map<Integer, LinkedList<Vertex3D>> mapVer;

	// tetCount to keep track of tets
	private Integer tetCount = 0;

	// map to create different cells
	private Map<Integer, Set<Integer>> cellIDs;

	/**
	 * mesh for all objects
	 */
	public Mesh() {
		// Linked HashMap to keep order
		mapTet = new LinkedHashMap<>();
		mapTri = new LinkedHashMap<>();
		mapEdg = new LinkedHashMap<>();
		mapVer = new LinkedHashMap<>();

		cellIDs = new LinkedHashMap<>();

	}

	/**
	 * adds tetrahedron and its triangles, edges and vertices to mesh
	 * 
	 * @param tet
	 */
	public void addTet(Tetrahedron3D tet) {
		// add tet to tet map with id of tet
		mapTet.put(tetCount, tet);

		// create ccw triangles
		Triangle3D tri0 = new Triangle3D(tet.getP0(), tet.getP1(), tet.getP2());
		Triangle3D tri1 = new Triangle3D(tet.getP0(), tet.getP1(), tet.getP3());
		Triangle3D tri2 = new Triangle3D(tet.getP0(), tet.getP2(), tet.getP3());
		Triangle3D tri3 = new Triangle3D(tet.getP1(), tet.getP2(), tet.getP3());
		// set of triangles that belong to tetrahedron
		LinkedList<Triangle3D> listTri = new LinkedList<>();
		listTri.add(tri0);
		listTri.add(tri1);
		listTri.add(tri2);
		listTri.add(tri3);
		// add tri to tri map with id of according tet
		mapTri.put(tetCount, listTri);

		// create edges of tet
		LinkedList<Edge3D> listEdg = new LinkedList<>();
		listEdg.add(new Edge3D(tet.getP0(), tet.getP1(), false));
		listEdg.add(new Edge3D(tet.getP0(), tet.getP2(), false));
		listEdg.add(new Edge3D(tet.getP0(), tet.getP3(), false));
		listEdg.add(new Edge3D(tet.getP1(), tet.getP2(), false));
		listEdg.add(new Edge3D(tet.getP1(), tet.getP3(), false));
		listEdg.add(new Edge3D(tet.getP2(), tet.getP3(), false));
		// add edges to edg map with id of according tet
		mapEdg.put(tetCount, listEdg);

		// create vertices of tet
		LinkedList<Vertex3D> listVer = new LinkedList<>();
		listVer.add(tet.getP0());
		listVer.add(tet.getP1());
		listVer.add(tet.getP2());
		listVer.add(tet.getP3());
		// add vertices to ver map with id of according tet
		mapVer.put(tetCount, listVer);

		// update tetCount
		tetCount++;
	}

	/**
	 * adds tetrahedron and its triangles, edges and vertices to mesh and to a cell
	 * 
	 * @param tet
	 * @param cellID
	 */
	public void addTet(Tetrahedron3D tet, int cellID) {
		addTet(tet);

		addExistingTetToCell(tet, cellID);
	}

	public void addExistingTetToCell(Tetrahedron3D existingTet, int cellID) {

		// check if cell already exists
		if (cellIDs.containsKey(Integer.valueOf(cellID))) {
			// add tet to cell
			Set<Integer> setOfCellID = cellIDs.get(Integer.valueOf(cellID));
			setOfCellID.add(getIdOfTet(existingTet));
			cellIDs.put(Integer.valueOf(cellID), setOfCellID);
		} else {
			// create new cell and add tet
			Set<Integer> setOfCellID = new HashSet<>();
			setOfCellID.add(getIdOfTet(existingTet));
			cellIDs.put(Integer.valueOf(cellID), setOfCellID);
		}

	}

	/**
	 * returns id of given tetrahedron
	 * 
	 * @param tet
	 * @return
	 */
	public Integer getIdOfTet(Tetrahedron3D tet) {
		Iterator<Entry<Integer, Tetrahedron3D>> it = mapTet.entrySet().iterator();

		while (it.hasNext()) {
			Entry<Integer, Tetrahedron3D> currentEntry = it.next();
			if (currentEntry.getValue().equals(tet)) {
				return currentEntry.getKey();
			}
		}

		return Integer.valueOf(-1);
	}

	/**
	 * mark boundary for cube: HAS TO BE ADAPTED FOR EVERY GIVEN EXAMPLE
	 */
	public void markBoundary() {
		for (int i = 0; i < mapTri.size(); i++) {
			for (Triangle3D triangle : mapTri.get(i)) {

				// check if triangle is boundary (is on same plane)
				if (triangleIsBoundary(triangle)) {
					triangle.setBoundary(true);
				}

			}
		}

		for (int i = 0; i < mapEdg.size(); i++) {
			for (Edge3D edge : mapEdg.get(i)) {

				// check if edge is boundary (is on same plane)
				if (edgeIsBoundary(edge)) {
					edge.setBoundary(true);
				}
			}
		}

		for (int i = 0; i < mapVer.size(); i++) {
			for (Vertex3D vertex : mapVer.get(i)) {

				// no check if vertex is boundary needed
				vertex.setBoundary(true);

			}
		}

	}

	private boolean edgeIsBoundary(Edge3D edge) {
		return (edge.getStart().getP().getX() == edge.getEnd().getP().getX()
				|| edge.getStart().getP().getY() == edge.getEnd().getP().getY()
				|| edge.getStart().getP().getZ() == edge.getEnd().getP().getZ());
	}

	private boolean triangleIsBoundary(Triangle3D triangle) {
		return (triangle.getP0().getP().getX() == triangle.getP1().getP().getX()
				&& triangle.getP0().getP().getX() == triangle.getP2().getP().getX()
				&& triangle.getP1().getP().getX() == triangle.getP2().getP().getX()
				|| triangle.getP0().getP().getY() == triangle.getP1().getP().getY()
						&& triangle.getP0().getP().getY() == triangle.getP2().getP().getY()
						&& triangle.getP1().getP().getY() == triangle.getP2().getP().getY()
				|| triangle.getP0().getP().getZ() == triangle.getP1().getP().getZ()
						&& triangle.getP0().getP().getZ() == triangle.getP2().getP().getZ()
						&& triangle.getP1().getP().getZ() == triangle.getP2().getP().getZ());
	
	}

	public Map<Integer, Tetrahedron3D> getMapTet() {
		return mapTet;
	}

	public Map<Integer, LinkedList<Triangle3D>> getMapTri() {
		return mapTri;
	}

	public Map<Integer, LinkedList<Edge3D>> getMapEdg() {
		return mapEdg;
	}

	public Map<Integer, LinkedList<Vertex3D>> getMapVer() {
		return mapVer;
	}

	public Integer getTetCount() {
		return tetCount;
	}

	public Map<Integer, Set<Integer>> getCellIDs() {
		return cellIDs;
	}

}
