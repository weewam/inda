package kth.csc.inda;

import java.util.LinkedList;

public class GraphRoute {
    private final static String NAME = GraphRoute.class.getName();
    
    /*
     * Used to store the node and its parent.
     * Will be used to trace the node from its current position
     * to the starting node.
     */
	private static class TraceNode {
		private TraceNode parent;
		private Integer value = null;
		
		private TraceNode(TraceNode parent, Integer value) {
			this.parent = parent;
			this.value = value;
		}
	}
    
	/*
	 * Calculates the shortest path between two nodes using bfs.
	 * @param graph the graph in question
	 * @param from	starting node
	 * @param to 	ending node
	 */
    public static void route(Graph graph, int from, int to) {
		if (from < 0 || from >= graph.numVertices() || to < 0 || to >= graph.numVertices()) {
			throw new IllegalArgumentException("Out of range: from = " + from + ", to = " + to + ".");
		}
		
		//Initiate list
		LinkedList<TraceNode> list = new LinkedList<TraceNode>();
		boolean[] visited = new boolean[graph.numVertices()];
		
		//Set currentNode to from value
		TraceNode currentNode = new TraceNode(null, from);
		list.add(currentNode);
		visited[from] = true;
		
		while(!list.isEmpty() && currentNode.value != to) { //Continue until currentNode.value = to or list becomes empty.
			currentNode = list.pop();
			
			for (VertexIterator it = graph.neighbors(currentNode.value); it.hasNext();) {
				int nextValue = it.next();
				
				if (!visited[nextValue]) {
					visited[nextValue] = true;
					list.add(new TraceNode(currentNode, nextValue));
				}
			}
		}
		
		//Checks whether there is a path
		if(currentNode.value == to) {
			printRoute(currentNode);
		} else {
			System.err.println();
		}
    }
    
    /*
     * Prints the route
     * @param currentNode the node whose route will be printed.
     */
    public static void printRoute(TraceNode currentNode) {
		System.out.format("%s ", currentNode.value);
		
    	if(currentNode.parent != null) {
    		printRoute(currentNode.parent);
    	}
    }
    
    /**
     * Searches the input file (args[1]) for lines containing the
     * given pattern (args[0]) and prints these lines.
     * Leaves program with System.exit(n), where n is 0 if successful.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Usage: java %s FROM TO%n", NAME);
            System.exit(1); // Unix error handling
        }
        
        route(new GraphFromFile("Distances.txt").getGraph(), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
