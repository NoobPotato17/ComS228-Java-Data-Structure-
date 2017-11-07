package edu.iastate.cs228.hw5;

/**
 * @author Ram Luitel
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import edu.iastate.cs228.hw5.Graph.Vertex;

public class Dijkstra {

	/**
	 * First, computes a shortest path from a source vertex to a destination
	 * vertex in a graph by using Dijkstra's algorithm. Second, visits and saves
	 * (in a stack) each vertex in the path, in reverse order starting from the
	 * destination vertex, by using the map object pred. Third, uses a
	 * StringBuilder object to generate the return String object by poping up
	 * the vertices from the stack; the vertices in the String object are in the
	 * right order. Note that the get_index() method is called from a
	 * Graph.Vertex object to get its oringinal integer name.
	 *
	 * @param G
	 *            - The graph in which a shortest path is to be computed
	 * @param source
	 *            - The first vertex of the shortest path
	 * @param dest
	 *            - The last vertex of the shortest path
	 * @return a String object with three lines (separated by a newline
	 *         character) such that line 1 shows the length of the shortest
	 *         path, line 2 shows the cost of the path, and line 3 gives a list
	 *         of the vertices (in the path) with a space between adjacent
	 *         vertices.
	 *
	 *         The contents of an example String object: Path Length: 5 Path
	 *         Cost: 4 Path: 0 4 2 5 7 9
	 *
	 * @throws NullPointerException
	 *             - If any arugment is null
	 *
	 * @throws RuntimeException
	 *             - If the given source or dest vertex is not in the graph
	 *
	 */
	public static String Dijkstra(Graph G, Graph.Vertex source, Graph.Vertex dest) {

		if (G == null || source == null || dest == null)
			throw new NullPointerException();
		if (!G.check_vertex(source) || !G.check_vertex(source))
			throw new RuntimeException();

		HashMap<Vertex, Integer> distance = new HashMap<Vertex, Integer>();
		HashMap<Graph.Vertex, Graph.Vertex> predecessor = new HashMap<Vertex, Vertex>();
		Heap<Vpair<Graph.Vertex, Integer>> priqueue = new Heap<Vpair<Graph.Vertex, Integer>>();
		HashSet<Graph.Vertex> vset = new HashSet<Graph.Vertex>();

		distance.put(source, 0);
		priqueue.add(new Vpair<Vertex, Integer>(source, 0));
		while (!priqueue.isEmpty()) {
			Vpair<Graph.Vertex, Integer> pair = (Vpair<Vertex, Integer>) priqueue.removeMin();
			Graph.Vertex u = pair.getVertex();
			if (!vset.contains(u)) {
				vset.add(u);
				for (Iterator<Graph.Edge> iter = u.get_edges().iterator(); iter.hasNext();) {
					Graph.Edge edge = iter.next();
					Vpair<Graph.Vertex, Integer> tmpVpair = new Vpair<Graph.Vertex, Integer>
					(edge.to, edge.get_weight());
					Graph.Vertex v = tmpVpair.getVertex();
					Integer altdist = distance.get(u) + tmpVpair.getCost();
					Integer vdist = distance.get(v);
					if (vdist == null || vdist > altdist) {
						distance.put(v, altdist);
						predecessor.put(v, u);
						priqueue.add(new Vpair<Graph.Vertex, Integer>(v, altdist));
					}
				}
			}
		}
		// if distance does not contain destinaiton return null
		if (!distance.containsKey(dest))
			return null;
		int pathCost = distance.get(dest);
		LinkedStack<Graph.Vertex> stack = new LinkedStack<Graph.Vertex>();
		Graph.Vertex curr = dest;
		while (curr != source && curr != null) {
			stack.push(curr);
			curr = predecessor.get(curr);
		}
		stack.push(source);
		StringBuilder sb = new StringBuilder();
		String path = new String();
		int pathLength = -1;
		while (!stack.isEmpty()) {
			path += stack.pop().get_index() + " ";
			pathLength++;
		}
		sb.append("Path Length: " + pathLength + "\nPath Cost: " + pathCost + "\nPath: " + path);
		return sb.toString();
	}

	/**
	 * A pair class with two components of types V and C, where V is a vertex
	 * type and C is a cost type.
	 */

	private static class Vpair<V, C extends Comparable<? super C>> implements Comparable<Vpair<V, C>> {
		private V node;
		private C cost;

		/**
		 * Constructor
		 * 
		 * @param n
		 *            node representing vertex
		 * @param c
		 *            cost of that vertex
		 */
		Vpair(V n, C c) {
			node = n;
			cost = c;
		}

		/**
		 * return the vertex
		 * 
		 * @return node the vertex
		 */
		public V getVertex() {
			return node;
		}

		/**
		 * return the cost of the vertex
		 * 
		 * @return the cost of the vertex
		 */
		public C getCost() {
			return cost;
		}

		/**
		 * compare this Vpari with another
		 * 
		 * @return a negative integer, zero, or a positive integer as this Vpair
		 *         is less than, equal to, or greater than the other Vpair.
		 */
		public int compareTo(Vpair<V, C> other) {
			return cost.compareTo(other.getCost());
		}

		/**
		 * @return The string representation
		 */
		public String toString() {
			return "<" + node.toString() + ", " + cost.toString() + ">";
		}

		/**
		 * Returns a hash code value for the object.
		 * 
		 * @return a hash code value for this object.
		 */
		public int hashCode() {
			return node.hashCode();
		}

		/**
		 * Indicates whether some other object is "equal to" this one.
		 * 
		 * @return true if this object is the same as the obj argument; false
		 *         otherwise.
		 */
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if ((obj == null) || (obj.getClass() != this.getClass()))
				return false;
			// object must be Vpair at this point
			Vpair<?, ?> test = (Vpair<?, ?>) obj;
			return (node == test.node || (node != null && node.equals(test.node)));
		}
	}

}
