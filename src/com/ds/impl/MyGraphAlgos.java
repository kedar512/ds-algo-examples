package com.ds.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.ds.exception.NegativeWeightCycleException;

public class MyGraphAlgos<E> {
	
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
	
	public void allPairShortestPathUsingFloydWarshall(MyLinkedListGraph graph) {
		for (int i = 0; i < graph.getVertices().size(); i++) {
			for (int j = 0; j < graph.getVertices().size(); j++) {
				for (int k = 0; k < graph.getVertices().size(); k++) {
					int viaVertexIndex = calculateViaVertexIndex(i, j);
					int destVertexIndex = calculateDestinationVertexIndex(k, j, i);
					int viaVertexDistance = graph.getVertices().get(j).get(viaVertexIndex).getDistance();
					int destVertexDistance = graph.getVertices().get(i).get(destVertexIndex).getDistance();
					if (!(viaVertexDistance == Integer.MAX_VALUE || destVertexDistance == Integer.MAX_VALUE)) {
						int directDistance = graph.getVertices().get(j).get(k).getDistance();
						if (directDistance > viaVertexDistance + destVertexDistance) {
							graph.getVertices().get(j).get(k).setDistance(viaVertexDistance + destVertexDistance);
						}
					}
					
				}
			}
		}
	}
	
	public void kruskalAlgoForMinimumSpanningTree(MyLinkedListGraph graph, MyDisjointSet<String> ds) {
		List<Vertex> edgeList = addAllEdgesInList(graph);
		
		for (int i = 0; i < edgeList.size(); i++) {
			Vertex parent = edgeList.get(i).getParent();
			
			if (!ds.findSet(edgeList.get(i).getName()).getElements().get(0).equals(ds.findSet(parent.getName()).getElements().get(0))) {
				ds.union(edgeList.get(i).getName(), parent.getName());
				int mainVertexListIndex = parent.getArrayIndex();
				int index = edgeList.get(i).getArrayIndex();
				graph.getVertices().get(mainVertexListIndex).get(index).setDistance(edgeList.get(i).getEdge());
				graph.getVertices().get(edgeList.get(i).getMainVertexListIndex()).get(edgeList.get(i + 1).getArrayIndex())
						.setDistance(edgeList.get(i).getEdge());
			}
		}
	}
	
	private List<Vertex> addAllEdgesInList(MyLinkedListGraph graph) {
		List<Vertex> edgeList = new ArrayList<>();
		for (List<Vertex> temp : graph.getVertices()) {
			for (int i = 1; i < temp.size(); i++) {
				edgeList.add(temp.get(i));
			}
		}
		Collections.sort(edgeList, (v1, v2) -> {
				return v1.getEdge() - v2.getEdge();
		});
		return edgeList;
	}
	
	private int calculateViaVertexIndex(int viaVertexIndex, int currVertexIndex) {
		if (viaVertexIndex < currVertexIndex) {
			return viaVertexIndex + 1;
		} else if (viaVertexIndex > currVertexIndex) {
			return viaVertexIndex;
		}
		return 0;
	}
	
	private int calculateDestinationVertexIndex(int destVertexIndex, int currVertexIndex, int viaVertexIndex) {

		int seqIndex = 0;
		if (0 == destVertexIndex) {
			seqIndex = currVertexIndex;
		} else if (destVertexIndex <= currVertexIndex) {
			seqIndex = destVertexIndex - 1;
		} else {
			seqIndex = destVertexIndex;
		}
		
		if (viaVertexIndex == seqIndex) {
			return 0;
		} else if (seqIndex >= viaVertexIndex) {
			return seqIndex;
		} else {
			return seqIndex + 1;
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
	
	public void printDistancesForAllPairShortestPath(MyLinkedListGraph graph) {
		for (int i = 0; i < graph.getVertices().size(); i++) {
			String sourceNode = graph.getVertices().get(i).get(0).getName();
			for (int j = 0; j < graph.getVertices().get(i).size(); j++) {
				System.out.println(sourceNode + " to " + graph.getVertices().get(i).get(j).getName() + " --> "
						+ graph.getVertices().get(i).get(j).getDistance());
			}
		}
	}
	
	public void printDistancesForMinSpanningTree(MyLinkedListGraph graph) {
		StringBuilder path = new StringBuilder();
		boolean isSourceAdded = false;
		Set<String> vertices = new HashSet<>();
		int i = 0;
		int count = 0;
		while (count < graph.getVertices().size()) {
			if (i >= graph.getVertices().size()) {
				break;
			}
			String sourceNode = graph.getVertices().get(i).get(0).getName();
			boolean isFound = false;
			for (int j = 1; j < graph.getVertices().get(i).size(); j++) {
				Vertex temp = graph.getVertices().get(i).get(j);
				
				if (vertices.contains(temp.getName())) {
					continue;
				}
				if (Integer.MAX_VALUE != temp.getDistance()) {
					isFound = true;
					if (!isSourceAdded) {
						path.append(sourceNode).append("--").append(temp.getDistance()).append("-->").append(temp.getName());
						isSourceAdded = true;
						vertices.add(graph.getVertices().get(i).get(0).getName());
						vertices.add(temp.getName());
						i = temp.getMainVertexListIndex();
						count += 2;
					} else {
						path.append("--").append(temp.getDistance()).append("-->").append(temp.getName());
						vertices.add(temp.getName());
						i = temp.getMainVertexListIndex();
						count++;
					}
					break;
				}
			}
			if (!isFound) {
				i++;
			}
		}
		
		System.out.println(path);
	}
	
	public void primsAlgoForMinimumSpanningTree(MyLinkedListGraph graph) {
		graph.getVertices().get(0).get(0).setDistance(0);
		PriorityQueue<Vertex> q = addAllVerticesInPriorityQueue(graph);
		
		while (!q.isEmpty()) {
			Vertex vertex = q.poll();
			
			List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
			graph.getVertices().get(vertex.getArrayIndex()).get(0).setVisited(true);
			
			for (int i = 1; i < adjacentVertices.size(); i++) {
				int newDistance = adjacentVertices.get(i).getEdge();
				int mainVertexListIndex = adjacentVertices.get(i).getMainVertexListIndex();
				if (!graph.getVertices().get(mainVertexListIndex).get(0).isVisited()
						&& newDistance < graph.getVertices().get(mainVertexListIndex).get(0).getDistance()) {
					
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
	
	public void printDistancesForPrimsMinSpanningTree(MyLinkedListGraph graph) {
		StringBuilder path = new StringBuilder();
		boolean isSourceAdded = false;
		Set<String> vertices = new HashSet<>();
		int i = 0;
		int count = 0;
		while (count < graph.getVertices().size()) {
			if (i >= graph.getVertices().size()) {
				break;
			}
			String sourceNode = graph.getVertices().get(i).get(0).getName();
			boolean isFound = false;
			for (int j = 1; j < graph.getVertices().get(i).size(); j++) {
				Vertex temp = graph.getVertices().get(i).get(j);
				Vertex mainVertex = graph.getVertices().get(temp.getMainVertexListIndex()).get(0);
				Vertex currParent = graph.getVertices().get(i).get(0);
				if (vertices.contains(temp.getName())
						|| !mainVertex.getParent().getName().equals(currParent.getName())) {
					continue;
				}
				if (Integer.MAX_VALUE != temp.getDistance()) {
					isFound = true;
					if (!isSourceAdded) {
						path.append(sourceNode).append("--").append(temp.getDistance()).append("-->").append(temp.getName());
						isSourceAdded = true;
						vertices.add(graph.getVertices().get(i).get(0).getName());
						vertices.add(temp.getName());
						i = temp.getMainVertexListIndex();
						count += 2;
					} else {
						path.append("--").append(temp.getDistance()).append("-->").append(temp.getName());
						vertices.add(temp.getName());
						i = temp.getMainVertexListIndex();
						count++;
					}
					break;
				}
			}
			if (!isFound) {
				i++;
			}
		}
		
		System.out.println(path);
	}
}
