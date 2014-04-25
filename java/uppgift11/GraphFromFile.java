package kth.csc.inda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Searches the input file for lines for a Graph
 * 
 * Requires Java 7 or later.
 *
 * @author Stefan Nilsson, Ibrahim Asfadai
 * @version 2014-04-21
 */
public final class GraphFromFile {
    private final static String NAME = GraphFromFile.class.getName();
    private Graph graph;
    
    public GraphFromFile(String filename) {
    	this.graph = new HashGraph(0);

        // This "try-with-resource" statement automatically calls file.close()
        // just before leaving the try block.
        try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
            populate(file);
        } catch (IOException e) {
            System.err.printf("%s: %s%n", NAME, e);
            System.exit(1); // Unix error handling
        }
    }
    
    public Graph getGraph() {
    	return graph;
    }
    
    /** 
     * Populates our graph
     */
    private void populate(BufferedReader in) throws IOException {
        String line;
        
        while ((line = in.readLine()) != null) {
        	line = line.trim();
        	
        	if(line.matches("^(\\d+)(\\D*)")) { //Match graph size
                graph = new HashGraph(Integer.parseInt(line.substring(0, 1))); //Create a hashGraph with the correct size
        	} else if (line.matches("^(\\d+\\s?){3}(\\D*)")) { //Match vertex with cost
            	String[] vertexMeta = line.split("\\s");
            	graph.addBi(Integer.parseInt(vertexMeta[0]), Integer.parseInt(vertexMeta[1]), Integer.parseInt(vertexMeta[2]));
            }
        }
    }
}