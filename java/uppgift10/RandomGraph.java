package kth.csc.inda;

import java.util.Random;
import java.util.HashMap;

public class RandomGraph extends GraphComponents {
	Graph graph;
	Random randomizer;
	
	RandomGraph(Graph graph) {
		super();
		
		randomizer = new Random();
		this.graph = graph;
	}
	
	/* 
	 * Populate the graph with random data.
	 */
	public void populate() {
		for(int i = 0; i < graph.numVertices(); i++) {
			graph.add(randomizer.nextInt(graph.numVertices()), randomizer.nextInt(graph.numVertices()));
		}
	}
	
	/* 
	 * Calculates the amount of counters and the size of these containers.
	 */
	private HashMap<String, Integer> calculateComponents() {
		return calculateComponents(graph);
	}
	
	public static void main(String[] args) {
		int graphSize = 1000;
		RandomGraph randomGraph = new RandomGraph(new HashGraph(graphSize));
		
		randomGraph.populate();
		HashMap<String, Integer> componentsMeta = randomGraph.calculateComponents();

		System.out.format("n : %d\n", graphSize);
		System.out.format("Largest component size: %d\n", componentsMeta.get("largestComponentSize"));
		System.out.format("Amount of components: %d\n", componentsMeta.get("componentAmount"));
	}
}
