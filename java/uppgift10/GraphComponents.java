package kth.csc.inda;

import java.util.HashMap;

public class GraphComponents {
	
	/* Calculate the components
	 * At the moment it only returns meta data(largestComponentSize and componentAmount)
	 * */
	public static HashMap<String, Integer> calculateComponents(Graph graph) {
		boolean[] visited = new boolean[graph.numVertices()];

		HashMap<String, Integer> componentsMeta = new HashMap<String, Integer>();
		
		//Reset the value
		int componentAmount = 0;
		int largestComponentSize = 0;
		
		for(int i = 0; i < graph.numVertices(); i++) {
			if(!visited[i]) {
				int currentComponentSize = dfs(graph, i, visited);
				
				if(currentComponentSize > largestComponentSize) {
					largestComponentSize = currentComponentSize;
				}
				
				componentAmount++;
			}
		}
		
		componentsMeta.put("componentAmount", componentAmount);
		componentsMeta.put("largestComponentSize", largestComponentSize);
		return componentsMeta;
	}
	
	/* 
	 * Uses DFS to iterate through the graph. 
	 */
	private static int dfs(Graph graph, int v, boolean[] visited) {
		if (visited[v]) {
			return 0;	
		}
		
		visited[v] = true;
		int componentSize = 1;
		
		for (VertexIterator it = graph.neighbors(v); it.hasNext();) {
			componentSize += dfs(graph, it.next(), visited);
		}
		
		return componentSize;
	}
}
