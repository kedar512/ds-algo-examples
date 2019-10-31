package com.ds.impl;

import java.util.List;
import java.util.PriorityQueue;

import com.ds.exception.NegativeWeightCycleException;

public class MyGraphAlgos {
	
	public void breadthFirstSearch(MyLinkedListGraph graph) {
		MyLinkedListQueue<Vertex> q = new MyLinkedListQueue<>();
		
		q.add(graph.getVertices().get(0).get(0));
		
		for (List<Vertex> temp : graph.getVertices()) {
			if (!temp.get(0).isVisited()) {
				while (!q.isEmpty()) {
					Vertex vertex = q.poll();
					
					if (!vertex.isVisited()) {
						System.out.println(vertex.getName());
						vertex.setVisited(true);
						List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
						
						for (int i = 1; i < adjacentVertices.size(); i++) {
							if (!adjacentVertices.get(i).isVisited()) {
								q.add(adjacentVertices.get(i));
							}
						}
					}
				}
			}
		}
	}
	
	public void depthFirstSearch(MyLinkedListGraph graph) {
		MyLinkedListStack<Vertex> stack = new MyLinkedListStack<>();
		
		stack.push(graph.getVertices().get(0).get(0));
		
		for (List<Vertex> temp : graph.getVertices()) {
			if (!temp.get(0).isVisited()) {
				while (!stack.isEmpty()) {
					Vertex vertex = stack.pop();
					
					if (!vertex.isVisited()) {
						System.out.println(vertex.getName());
						vertex.setVisited(true);
						List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
						
						for (int i = 1; i < adjacentVertices.size(); i++) {
							if (!adjacentVertices.get(i).isVisited()) {
								stack.push(adjacentVertices.get(i));
							}
						}
					}
				}
			}
		}
	}
	
	public void topologicalSort(MyLinkedListGraph graph) {
		MyLinkedListStack<Vertex> stack = new MyLinkedListStack<>();
		
		for (List<Vertex> temp : graph.getVertices()) {
			if (!temp.get(0).isVisited()) {
				topologicalVisit(graph, temp.get(0), stack);
			}
		}
		
		while (!stack.isEmpty()) {
			System.out.println(stack.pop().getName());
		}
	}
	
	private void topologicalVisit(MyLinkedListGraph graph, Vertex vertex, MyLinkedListStack<Vertex> stack) {
		List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
		
		for (int i = 1; i < adjacentVertices.size(); i++) {
			if (!adjacentVertices.get(i).isVisited()) {
				//stack.push(adjacentVertices.get(i));
				topologicalVisit(graph, adjacentVertices.get(i), stack);
			}
		}
		vertex.setVisited(true);
		stack.push(vertex);
	}
	
	public void singleSourceShortestPathUsingBFS(MyLinkedListGraph graph) {
		MyLinkedListQueue<Vertex> q = new MyLinkedListQueue<>();
		
		q.add(graph.getVertices().get(0).get(0));
		
		while (!q.isEmpty()) {
			Vertex vertex = q.poll();
			
			List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
			
			for (int i = 1; i < adjacentVertices.size(); i++) {
				if (!adjacentVertices.get(i).isVisited()) {
					q.add(adjacentVertices.get(i));
					adjacentVertices.get(i).setParent(vertex);
					vertex.setVisited(true);
				}
			}
		}
	}
	
	public void singleSourceShortestPathUsingDijkstra(MyLinkedListGraph graph) {
		graph.getVertices().get(0).get(0).setDistance(0);
		
		PriorityQueue<Vertex> q = addAllVerticesInPriorityQueue(graph);
		
		while (!q.isEmpty()) {
			Vertex vertex = q.poll();
			
			List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
			
			for (int i = 1; i < adjacentVertices.size(); i++) {
				int newDistance = vertex.getDistance() + adjacentVertices.get(i).getEdge();
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
	
	private PriorityQueue<Vertex> addAllVerticesInPriorityQueue(MyLinkedListGraph graph) {
		PriorityQueue<Vertex> q = new PriorityQueue<>(graph.getVertices().size(), (v1, v2) -> {
				return v1.getDistance() - v2.getDistance();
		});
		for (List<Vertex> temp : graph.getVertices()) {
			q.add(temp.get(0));
		}
		return q;
	}
	
	public void singleSourceShortestPathUsingBellmanFord(MyLinkedListGraph graph, String source) {
		for (List<Vertex> temp : graph.getVertices()) {
			if (source.equals(temp.get(0).getName())) {
				temp.get(0).setDistance(0);
				break;
			}
		}
		
		for (int i = 1; i < graph.getVertices().size(); i++) {
			for (List<Vertex> tempList : graph.getVertices()) {
				Vertex firstVertex = tempList.get(0);
				for (int j = 1; j < tempList.size(); j++) {
					Vertex tempVertex = tempList.get(j);
					int mainVertexListIndex = tempList.get(j).getMainVertexListIndex();
					Vertex secondVertex = graph.getVertices().get(mainVertexListIndex).get(0);
					
					if (Integer.MAX_VALUE != firstVertex.getDistance()) {
						int newDistance = firstVertex.getDistance() + tempVertex.getEdge();
						
						if (newDistance < secondVertex.getDistance()) {
							secondVertex.setDistance(newDistance);
							secondVertex.setParent(firstVertex);
						}
					}
				}
			}
		}
		
		for (List<Vertex> tempList : graph.getVertices()) {
			Vertex firstVertex = tempList.get(0);
			for (int j = 1; j < tempList.size(); j++) {
				Vertex tempVertex = tempList.get(j);
				int mainVertexListIndex = tempList.get(j).getMainVertexListIndex();
				Vertex secondVertex = graph.getVertices().get(mainVertexListIndex).get(0);
				
				int newDistance = firstVertex.getDistance() + tempVertex.getEdge();
				
				if (newDistance < secondVertex.getDistance()) {
					// Negative cycle found
					throw new NegativeWeightCycleException("Negative weight cycle found");
				}
			}
		}
	}
	
	public void printPathAndDistanceForGivenVertices(MyLinkedListGraph graph, String source, String destination) {
		for (List<Vertex> temp : graph.getVertices()) {
			if (destination.equals(temp.get(0).getName())) {
				printPathAndDistance(temp.get(0), source);
			}
		}
	}
	
	private void printPathAndDistance(Vertex vertex, String source) {
		System.out.print("Path:");
		StringBuilder path = new StringBuilder();
		boolean isCompleted = false;
		Vertex temp = vertex;
		while (!isCompleted) {
			path.append(temp.getName()).append(" ");
			temp = temp.getParent();
			if (source.equals(temp.getName())) {
				path.append(temp.getName()).append(" ");
				isCompleted = true;
			}
		}
		
		System.out.println(path.reverse());
		System.out.println("Distance: " + vertex.getDistance());
	}
}
