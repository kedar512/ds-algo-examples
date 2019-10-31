package com.ds.impl;

public class Vertex {
	private String name;
	private boolean isVisited;
	private int distance;
	private int arrayIndex;
	private Vertex parent;
	private int edge;
	private int mainVertexListIndex;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arrayIndex;
		result = prime * result + distance;
		result = prime * result + edge;
		result = prime * result + (isVisited ? 1231 : 1237);
		result = prime * result + mainVertexListIndex;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (arrayIndex != other.arrayIndex)
			return false;
		if (distance != other.distance)
			return false;
		if (edge != other.edge)
			return false;
		if (isVisited != other.isVisited)
			return false;
		if (mainVertexListIndex != other.mainVertexListIndex)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	public int getMainVertexListIndex() {
		return mainVertexListIndex;
	}
	public void setMainVertexListIndex(int mainVertexListIndex) {
		this.mainVertexListIndex = mainVertexListIndex;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getEdge() {
		return edge;
	}
	public void setEdge(int edge) {
		this.edge = edge;
	}
	public Vertex getParent() {
		return parent;
	}
	public void setParent(Vertex parent) {
		this.parent = parent;
	}
	public int getArrayIndex() {
		return arrayIndex;
	}
	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isVisited() {
		return isVisited;
	}
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
}
