package com.ds.hackerrank;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.ds.impl.MyLinkedListGraph;
import com.ds.impl.Vertex;

public class SingleSourceShortestPath {
	
	// n -> no of nodes
	// m -> nof of edges
	// edges -> 2D array showing connections of nodes
	// s -> source node index
	static int[] singleSourceShortestPathUsingbfs(int n, int m, int[][] edges, int s) {
		int[] distArr = new int[n - 1];
		
		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);

		for (int i = 1; i <= n; i++) {
			Vertex v = new Vertex();

			v.setName(String.valueOf(i));
			graph.add(v);
		}
		
		for (int i = 0; i < edges.length; i++) {
			int sourceNode = edges[i][0];
			int destNode = edges[i][1];

			List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
			Vertex destVertex = graph.getVertices().get(destNode - 1).get(0);

			List<Vertex> destNodeList = graph.getVertices().get(destNode - 1);
			Vertex sourceVertex = graph.getVertices().get(sourceNode - 1).get(0);

			sourceNodeList.add(destVertex);
			destNodeList.add(sourceVertex);
		}
		
		Queue<Vertex> q = new LinkedList<>();
		
		q.add(graph.getVertices().get(s - 1).get(0));
		
		for (List<Vertex> temp : graph.getVertices()) {
			if (!temp.get(0).isVisited()) {
				while (!q.isEmpty()) {
					Vertex vertex = q.poll();
					
					if (!vertex.isVisited()) {
						vertex.setVisited(true);
						List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
						
						for (int i = 1; i < adjacentVertices.size(); i++) {
							if (!adjacentVertices.get(i).isVisited()) {
								q.add(adjacentVertices.get(i));
								if (null == adjacentVertices.get(i).getParent()) {
									adjacentVertices.get(i).setParent(vertex);
								}
							}
						}
					}
				}
			}
		}
		int count = 0;
		for (int i = 0; i < graph.getVertices().size(); i++) {
			
			if (i == s - 1) {
				continue;
			}
			Vertex v = graph.getVertices().get(i).get(0);
			int dist = 0;
			
			if (null == v.getParent()) {
				dist = -1;
			} else {
				while (null != v.getParent()) {
					dist += 6;
					v = v.getParent();
				}
			}
			
			distArr[count] = dist;
			count++;
		}
		
		return distArr;
    }
	
	public static void singleSourceShortestPathUsingDijkstraMain() throws IOException {

		String filePath = "src/hk_dijkstra_sssp.txt";
		boolean isCompleted = false;
		File file = new File(filePath);
		
		try (InputStream in = new FileInputStream(file)) {
			FastReader reader = new FastReader(in);
			while (!isCompleted) {
				int t = reader.nextInt();

		        for (int tItr = 0; tItr < t; tItr++) {
		            int n = reader.nextInt();
		            int m = reader.nextInt();
		            int[][] indexArr = new int[n][n];
		            
		    		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);

		    		for (int i = 1; i <= n; i++) {
		    			Vertex v = new Vertex();

		    			v.setName(String.valueOf(i));
		    			graph.add(v);
		    		}

		            int[][] edges = new int[m][3];

		            for (int i = 0; i < m; i++) {
		            	edges[i][0] = reader.nextInt();
		                edges[i][1] = reader.nextInt();
		                edges[i][2] = reader.nextInt();
		                
		    			int sourceNode = edges[i][0];
		    			int destNode = edges[i][1];
		    			int edge = edges[i][2];
		    			
		    			int destNodeIndexInSourceNodeList = indexArr[sourceNode - 1][destNode - 1];
		    			
		    			if (destNodeIndexInSourceNodeList != 0) {
		    				List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
		    				Vertex destVertex = sourceNodeList.get(destNodeIndexInSourceNodeList);
		    				
		    				if (edge < destVertex.getEdge()) {
		    					destVertex.setEdge(edge);
		    					
		    					List<Vertex> destNodeList = graph.getVertices().get(destNode - 1);
		    					int sourceNodeIndexInSourceNodeList = indexArr[destNode - 1][sourceNode - 1];
			    				Vertex sourceVertex = destNodeList.get(sourceNodeIndexInSourceNodeList);
			    				
			    				sourceVertex.setEdge(edge);
		    				}
		    			} else {
			    			Vertex destVertex = new Vertex();
			    			Vertex sourceVertex = new Vertex();

			    			List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
			    			List<Vertex> destNodeList = graph.getVertices().get(destNode - 1);
			    			
			    			destVertex.setName(String.valueOf(destNode));
			    			destVertex.setEdge(edge);
			    			destVertex.setParent(sourceNodeList.get(0));
			    			destVertex.setDistance(Integer.MAX_VALUE);
			    			destVertex.setMainVertexListIndex(destNodeList.get(0).getArrayIndex());
			    			
			    			sourceNodeList.add(destVertex);
			    			destVertex.setArrayIndex(sourceNodeList.size() - 1);
			    			indexArr[sourceNode - 1][destNode - 1] = sourceNodeList.size() - 1;
			    			
			    			sourceVertex.setName(String.valueOf(sourceNode));
			    			sourceVertex.setEdge(edge);
			    			sourceVertex.setParent(destNodeList.get(0));
			    			sourceVertex.setDistance(Integer.MAX_VALUE);
			    			sourceVertex.setMainVertexListIndex(sourceNodeList.get(0).getArrayIndex());
			    			
			    			destNodeList.add(sourceVertex);
			    			sourceVertex.setArrayIndex(destNodeList.size() - 1);
			    			indexArr[destNode - 1][sourceNode - 1] = destNodeList.size() - 1;
		    			}
		            }

		            int s = reader.nextInt();

		            int[] result = shortestReach(n, edges, s, graph);

		            for (int i = 0; i < result.length; i++) {
		            	System.out.print(result[i]);

		                if (i != result.length - 1) {
		                    System.out.print(" ");
		                }
		            }

		            System.out.println();
		        }
				isCompleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static int[] shortestReach(int n, int[][] edges, int s, MyLinkedListGraph graph) {
		int[] distArr = new int[n - 1];
		
		singleSourceShortestPathUsingDijkstra(graph, s);
		
		int count = 0;
		for (int i = 0; i < graph.getVertices().size(); i++) {
			
			if (i == s - 1) {
				continue;
			}
			Vertex v = graph.getVertices().get(i).get(0);
			int dist = 0;
			
			if (null == v.getParent()) {
				dist = -1;
			} else {
				dist += v.getDistance();
			}
			
			distArr[count] = dist;
			count++;
		}
		
		return distArr;
    }
	
	static void singleSourceShortestPathUsingDijkstra(MyLinkedListGraph graph, int startIndex) {
		graph.getVertices().get(startIndex - 1).get(0).setDistance(0);
		
		PriorityQueue<Vertex> q = addAllVerticesInPriorityQueue(graph);
		
		while (!q.isEmpty()) {
			Vertex vertex = q.poll();
			
			List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
			
			for (int i = 1; i < adjacentVertices.size(); i++) {
				int newDistance = Integer.MAX_VALUE;
				if (Integer.MAX_VALUE != vertex.getDistance()) {
					newDistance = vertex.getDistance() + adjacentVertices.get(i).getEdge();
				}
				int mainVertexListIndex = adjacentVertices.get(i).getMainVertexListIndex();
				if (newDistance < graph.getVertices().get(mainVertexListIndex).get(0).getDistance()) {
					
					adjacentVertices.get(i).setDistance(newDistance);
					adjacentVertices.get(i).setParent(vertex);
					
					graph.getVertices().get(mainVertexListIndex).get(0).setDistance(newDistance);
					graph.getVertices().get(mainVertexListIndex).get(0).setParent(vertex);
					
					q.remove(graph.getVertices().get(mainVertexListIndex).get(0));
					q.add(graph.getVertices().get(mainVertexListIndex).get(0));
				}
			}
		}
	}
	
	static PriorityQueue<Vertex> addAllVerticesInPriorityQueue(MyLinkedListGraph graph) {
		PriorityQueue<Vertex> q = new PriorityQueue<>(graph.getVertices().size(), (v1, v2) -> {
				return v1.getDistance() - v2.getDistance();
		});
		for (List<Vertex> temp : graph.getVertices()) {
			q.add(temp.get(0));
		}
		return q;
	}
}
