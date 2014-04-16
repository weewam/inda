package kth.csc.inda;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.lang.StringBuilder;

/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 * 
 * @author [Name]
 * @version [Date]
 */
public class HashGraph implements Graph {
	/**
	 * The map edges[v] contains the key-value pair (w, c) if there is an edge
	 * from v to w; c is the cost assigned to this edge. The maps may be null
	 * and are allocated only when needed.
	 */
	private final Map<Integer, Integer>[] edges;
	private final static int INITIAL_MAP_SIZE = 4;

	/** Number of edges in the graph. */
	private int numEdges;

	/**
	 * Constructs a HashGraph with n vertices and no edges. Time complexity:
	 * O(n)
	 * 
	 * @throws IllegalArgumentException
	 *             if n < 0
	 */
	public HashGraph(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n = " + n);

		// The array will contain only Map<Integer, Integer> instances created
		// in addEdge(). This is sufficient to ensure type safety.
		@SuppressWarnings("unchecked")
		Map<Integer, Integer>[] a = new HashMap[n];
		edges = a;
	}

	/**
	 * Add an edge without checking parameters.
	 */
	private void addEdge(int from, int to, int cost) {
		rangeValidifier(from);
		rangeValidifier(to);
		
		if (edges[from] == null) {
			edges[from] = new HashMap<Integer, Integer>(INITIAL_MAP_SIZE);
		}
		
		if (edges[from].put(to, cost) == null) {
			numEdges++;
		}
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numVertices() {
		return edges.length;
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numEdges() {
		return numEdges;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int degree(int v) throws IllegalArgumentException {
		rangeValidifier(v);
		
		return (edges[v] == null) ? 0 : edges[v].size();
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public VertexIterator neighbors(int v) throws IllegalArgumentException {
		rangeValidifier(v);

		return new NeighborIterator(edges[v]);
	}

	private class NeighborIterator implements VertexIterator {
		Iterator<Integer> freshIterator;

		NeighborIterator(Map<Integer, Integer> vertex) {
			if(vertex != null) {
				freshIterator = vertex.keySet().iterator();
			}
		}

		@Override
		public boolean hasNext() {
			return (freshIterator != null && freshIterator.hasNext());	
		}

		@Override
		public int next() {
			if (hasNext()) {
				return freshIterator.next();
			}
			
			throw new NoSuchElementException("This iterator has no more elements.");
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public boolean hasEdge(int v, int w) throws IllegalArgumentException {
		rangeValidifier(v);
		rangeValidifier(w);
		
		return (edges[v] != null && edges[v].containsKey(w));
	}

	/**
	 * {@inheritDoc Graph}
	 */
	
	@Override
	public int cost(int v, int w) throws IllegalArgumentException {
		rangeValidifier(v);
		rangeValidifier(w);
		
		if(hasEdge(v, w))
			return edges[v].get(w);
		else
			return NO_COST;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to) throws IllegalArgumentException {
		addEdge(from, to, NO_COST);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to, int c) throws IllegalArgumentException {
		if(c < NO_COST) {
			throw new IllegalArgumentException();	
		}
		
		addEdge(from, to, c);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w) throws IllegalArgumentException {
		addEdge(v, w, NO_COST);
		addEdge(w, v, NO_COST);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w, int c) throws IllegalArgumentException {
		add(v, w, c);
		add(w, v, c);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void remove(int from, int to) throws IllegalArgumentException {
		if(hasEdge(from, to)) {
			edges[from].remove(to);
			numEdges--;
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void removeBi(int v, int w) throws IllegalArgumentException {
		remove(v, w);
		remove(w, v);
	}

	/**
	 * Returns a string representation of this graph.
	 * 
	 * @return a String representation of this graph
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < numVertices(); i++) {
			if(edges[i] != null) {
				for(Integer j : edges[i].keySet()) {
					Integer k = edges[i].get(j);
					
					if(k == NO_COST) {
						sb.append("(" + i + "," + j + "), ");
					} else {
						sb.append("(" + i + "," + j + "," + k + "), ");
					}
				}
			}
		}
		
		if(sb.length() > 0) {
			sb.setLength(sb.length() - 2);
		}
		
		return String.format("{%s}", sb.toString());
	}

	/**
	 * Checks whether the edge is in range or not
	 */
	public void rangeValidifier(int v) {
		if(v < 0 || v > numVertices() - 1) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void main(String [ ] args) {
	}
}