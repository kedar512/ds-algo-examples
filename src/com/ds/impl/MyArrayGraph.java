package com.ds.impl;

import java.util.ArrayList;
import java.util.List;

public class MyArrayGraph {
	int[][] arr;
	private boolean isDirectional;
	private boolean isWeighted;
	private List<Vertex> vertices;

	public MyArrayGraph(int size, boolean isDirectional, boolean isWeighted) {
		arr = new int[size][size];
		setDirectional(isDirectional);
		setWeighted(isWeighted);
	}
	
	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
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
	
	public boolean add(Vertex vertex) {
		if (null == vertices) {
			vertices = new ArrayList<>();
		}
		vertices.add(vertex);
		return true;
	}
	
	public boolean connectVertices(String vertex, List<String> adjacentVertices) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertex.equals(vertices.get(i).getName())) {
				connectAdjacentVertices(i, adjacentVertices);
				return true;
			}
		}
		return false;
	}
	
	private void connectAdjacentVertices(int index, List<String> adjacentVertices) {
		for (String str: adjacentVertices) {
			for (int i = 0; i < vertices.size(); i++) {
				if (str.equals(vertices.get(i).getName())) {
					arr[index][i] = 1;
				}
			}
		}
	}
	
}
