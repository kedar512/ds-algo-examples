package com.ds.hackerrank;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.ds.impl.MyLinkedListGraph;
import com.ds.impl.Vertex;

public class AllPairShortestPath {
	
	public static void FloydWarshallMain() throws IOException {

		String filePath = "src/hk_all_pair_shortest_path_floyd_warshall.txt";
		boolean isCompleted = false;
		File file = new File(filePath);
		
		try (InputStream in = new FileInputStream(file)) {
			FastReader reader = new FastReader(in);
			while (!isCompleted) {
				int roadNodes = reader.nextInt();
		        int roadEdges = reader.nextInt();
		        
	    		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);

	    		for (int i = 1; i <= roadNodes; i++) {
	    			Vertex v = new Vertex();

	    			v.setName(String.valueOf(i));
	    			graph.addForAllPairShortestPath(v);
	    			
	    			for (int j = 1; j <= roadNodes; j++) {
	    				List<Vertex> lastAddedList = graph.getVertices().get(i - 1);
	    				Vertex dest = new Vertex();
	    				
	    				dest.setName(String.valueOf(j));
	    				
	    				if (i == j) {
	    					dest.setEdge(0);
	    					dest.setDistance(0);
	    					dest.setMainVertexListIndex(j - 1);
	    					dest.setArrayIndex(j);
	    				} else {
	    					dest.setEdge(Integer.MAX_VALUE);
	    					dest.setDistance(Integer.MAX_VALUE);
	    					dest.setMainVertexListIndex(j - 1);
	    					dest.setArrayIndex(j);
	    				}
	    				lastAddedList.add(dest);
	    			}
	    		}

		        for (int i = 0; i < roadEdges; i++) {
	    			int sourceNode = reader.nextInt();
	    			int destNode = reader.nextInt();
	    			int edge = reader.nextInt();
	    			
	    			Vertex destVertex = graph.getVertices().get(sourceNode - 1).get(destNode);
	    			
	    			if (Integer.MAX_VALUE != destVertex.getDistance()) {
	    				destVertex.setEdge(edge);
	    				destVertex.setDistance(edge);
	    			} else {
		    			List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
		    			
		    			destVertex.setEdge(edge);
		    			destVertex.setParent(sourceNodeList.get(0));
		    			destVertex.setDistance(edge);
	    			}
		        }
		        
		        allPairShortestPathUsingFloydWarshall(graph);

		        int q = reader.nextInt();
		        int[] queries = new int[q];

		        for (int qItr = 0; qItr < queries.length; qItr++) {
		            int x = reader.nextInt();
		            int y = reader.nextInt();
		            
		            int distance = graph.getVertices().get(x - 1).get(y).getDistance();
		            
		            if (Integer.MAX_VALUE != distance) {
		            	System.out.println(distance);
		            } else {
		            	System.out.println(-1);
		            }
		        }
				isCompleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void allPairShortestPathUsingFloydWarshall(MyLinkedListGraph graph) {
		for (int i = 0; i < graph.getVertices().size(); i++) {
			for (int j = 0; j < graph.getVertices().size(); j++) {
				for (int k = 0; k < graph.getVertices().size(); k++) {
					int viaVertexIndex = i + 1;
					int destVertexIndex = k + 1;
					int viaVertexDistance = graph.getVertices().get(j).get(viaVertexIndex).getDistance();
					int destVertexDistance = graph.getVertices().get(i).get(destVertexIndex).getDistance();
					if (!(viaVertexDistance == Integer.MAX_VALUE || destVertexDistance == Integer.MAX_VALUE)) {
						int directDistance = graph.getVertices().get(j).get(destVertexIndex).getDistance();
						if (directDistance > viaVertexDistance + destVertexDistance) {
							Vertex temp = graph.getVertices().get(i).get(destVertexIndex).getParent();
							Vertex newParent = graph.getVertices().get(j).get(temp.getMainVertexListIndex() + 1);
							graph.getVertices().get(j).get(destVertexIndex).setParent(newParent);
							graph.getVertices().get(j).get(destVertexIndex).setDistance(viaVertexDistance + destVertexDistance);
						}
					}
				}
			}
		}
	}
}
