package com.ds.impl;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedListGraph {
	private boolean isDirectional;
	private boolean isWeighted;
	private List<List<Vertex>> vertices;
	
	public MyLinkedListGraph(boolean isDirectional, boolean isWeighted) {
		setDirectional(isDirectional);
		setWeighted(isWeighted);
	}

	public boolean isDirectional() {
		return isDirectional;
	}

	public void setDirectional(boolean isDirectional) {
		this.isDirectional = isDirectional;
	}

	public boolean isWeighted() {
		return isWeighted;
	}

	public void setWeighted(boolean isWeighted) {
		this.isWeighted = isWeighted;
	}

	public List<List<Vertex>> getVertices() {
		return vertices;
	}
	
	public boolean add(Vertex vertex) {
		if (null == vertices) {
			vertices = new ArrayList<>();
		}
		
		vertex.setDistance(Integer.MAX_VALUE);
		List<Vertex> temp = new ArrayList<>();
		temp.add(vertex);
		vertices.add(temp);
		temp.get(0).setArrayIndex(vertices.size() - 1);
		return true;
	}
	
	public boolean addForAllPairShortestPath(Vertex vertex) {
		if (null == vertices) {
			vertices = new ArrayList<>();
		}
		
		vertex.setDistance(0);
		List<Vertex> temp = new ArrayList<>();
		temp.add(vertex);
		vertices.add(temp);
		temp.get(0).setArrayIndex(vertices.size() - 1);
		return true;
	}
	
	public boolean connectVertices(String vertex, List<String> adjacentVertices) {
		for (List<Vertex> temp: vertices) {
			if (vertex.equals(temp.get(0).getName())) {
				connectAdjacentVertices(temp, adjacentVertices);
				return true;
			}
		}
		return false;
	}
	
	private void connectAdjacentVertices(List<Vertex> temp, List<String> adjacentVertices) {
		
		for (String vertexName: adjacentVertices) {
			for (List<Vertex> list : vertices) {
				if (vertexName.equals(list.get(0).getName())) {
					temp.add(list.get(0));
				}
			}
		}
	}
	
	public boolean connectVerticesForShortestPath(String vertex, String adjacentVertex, int edge) {
		for (List<Vertex> temp: vertices) {
			if (vertex.equals(temp.get(0).getName())) {
				connectAdjacentVertexForShortestPath(temp, adjacentVertex, edge);
				return true;
			}
		}
		return false;
	}
	
	private void connectAdjacentVertexForShortestPath(List<Vertex> temp, String adjacentVertex, int edge) {
		Vertex vertex = new Vertex();
		
		vertex.setName(adjacentVertex);
		vertex.setEdge(edge);
		vertex.setParent(temp.get(0));
		vertex.setDistance(Integer.MAX_VALUE);
		
		for (int i = 0; i < vertices.size(); i++) {
			if (adjacentVertex.equals(vertices.get(i).get(0).getName())) {
				vertex.setMainVertexListIndex(i);
				break;
			}
		}
		
		temp.add(vertex);
	}
	
	public boolean connectVerticesForAllPairShortestPath(String vertex, String adjacentVertex, int edge) {
		for (List<Vertex> temp: vertices) {
			if (vertex.equals(temp.get(0).getName())) {
				connectAdjacentVertexForAllPairShortestPath(temp, adjacentVertex, edge);
				return true;
			}
		}
		return false;
	}
	
	private void connectAdjacentVertexForAllPairShortestPath(List<Vertex> temp, String adjacentVertex, int edge) {
		Vertex vertex = new Vertex();
		
		vertex.setName(adjacentVertex);
		vertex.setEdge(edge);
		vertex.setParent(temp.get(0));
		vertex.setDistance(edge);
		
		for (int i = 0; i < vertices.size(); i++) {
			if (adjacentVertex.equals(vertices.get(i).get(0).getName())) {
				vertex.setMainVertexListIndex(i);
				break;
			}
		}
		
		temp.add(vertex);
		vertex.setArrayIndex(temp.size() - 1);
	}
	
	public boolean connectVerticesForMinSpanningTree(String vertex, String adjacentVertex, int edge) {
		for (List<Vertex> temp: vertices) {
			if (vertex.equals(temp.get(0).getName())) {
				connectAdjacentVertexForMinSpanningTree(temp, adjacentVertex, edge);
				return true;
			}
		}
		return false;
	}
	
	private void connectAdjacentVertexForMinSpanningTree(List<Vertex> temp, String adjacentVertex, int edge) {
		Vertex vertex = new Vertex();
		
		vertex.setName(adjacentVertex);
		vertex.setEdge(edge);
		vertex.setParent(temp.get(0));
		vertex.setDistance(Integer.MAX_VALUE);
		
		for (int i = 0; i < vertices.size(); i++) {
			if (adjacentVertex.equals(vertices.get(i).get(0).getName())) {
				vertex.setMainVertexListIndex(i);
				break;
			}
		}
		
		temp.add(vertex);
		vertex.setArrayIndex(temp.size() - 1);
	}
	
}
