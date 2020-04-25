package com.ds.hackerrank;

import java.util.LinkedList;
import java.util.List;
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
}
