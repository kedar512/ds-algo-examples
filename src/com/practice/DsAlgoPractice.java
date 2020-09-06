package com.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ds.hackerrank.FastReader;
import com.ds.impl.MyIndexedArrayBinaryMinHeapTree;
import com.ds.impl.MyLinkedListGraph;
import com.ds.impl.MySet;
import com.ds.impl.Vertex;

class DisjointSet {
	List<MySet<Integer>> sets;

	public List<MySet<Integer>> getSets() {
		return sets;
	}

	public void setSets(List<MySet<Integer>> sets) {
		this.sets = sets;
	}
	
	public boolean makeSet(Integer noOfElem) {
		if (null == sets) {
			sets = new ArrayList<>();
		}
		
		for (int i = 1; i <= noOfElem; i++) {
			List<Integer> elemList = new ArrayList<>();
			elemList.add(i);
			
			MySet<Integer> set = new MySet<>();
			
			set.setElements(elemList);
			sets.add(set);
		}
		return true;
	}
	
	public boolean union(Integer firstSet, Integer secondSet) {
		if (firstSet == secondSet) {
			return true;
		}
		MySet<Integer> set1 = sets.get(firstSet - 1);
		MySet<Integer> set2 = sets.get(secondSet - 1);
		
		while (null != set1.getMovedToSet()) {
			set1 = set1.getMovedToSet();
		}
		
		while (null != set2.getMovedToSet()) {
			set2 = set2.getMovedToSet();
		}
		
		if (set1.getElements().get(0).equals(set2.getElements().get(0))) {
			return true;
		}
		
		if (set1.getElements().size() >= set2.getElements().size()) {
			for (Integer e : set2.getElements()) {
				if (!set1.getElements().contains(e)) {
					set1.getElements().add(e);
				}
			}
			set2.setMovedToSet(set1);
		} else {
			for (Integer e : set1.getElements()) {
				if (!set2.getElements().contains(e)) {
					set2.getElements().add(e);
				}
			}
			set1.setMovedToSet(set2);
		}
		return true;
	}
	
	public MySet<Integer> findSet(Integer setNo) {
		
		MySet<Integer> set = sets.get(setNo - 1);
		
		while (null != set.getMovedToSet()) {
			set = set.getMovedToSet();
		}
		return set;
	}
}

class Node {
	private int index;
	private int parentIndex;
	private int points;
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getParentIndex() {
		return parentIndex;
	}
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}
}

class MyCount {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}

class PathDetails {
	private int cost;
	private Set<Integer> fishTypes;
	private int destNode;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destNode;
		result = prime * result + ((fishTypes == null) ? 0 : fishTypes.hashCode());
		result = prime * result + cost;
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
		PathDetails other = (PathDetails) obj;
		if (destNode != other.destNode)
			return false;
		if (fishTypes == null) {
			if (other.fishTypes != null)
				return false;
		} else if (!fishTypes.equals(other.fishTypes))
			return false;
		if (cost != other.cost)
			return false;
		return true;
	}
	public int getDestNode() {
		return destNode;
	}
	public void setDestNode(int destNode) {
		this.destNode = destNode;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int time) {
		this.cost = time;
	}	
	public Set<Integer> getFishTypes() {
		if (null == fishTypes) {
			fishTypes = new HashSet<>();
		}
		return fishTypes;
	}
	public void setFishTypes(Set<Integer> fishTypes) {
		this.fishTypes = fishTypes;
	}
}

public class DsAlgoPractice {

	public static void main(String[] args) throws IOException {
		String filePath = "src/input.txt";
		boolean isCompleted = false;
		File file = new File(filePath);
		
		try (InputStream in = new FileInputStream(file)) {
			FastReader reader = new FastReader(in);
			while (!isCompleted) {
				int gNodes = reader.nextInt();
		        int gEdges = reader.nextInt();
		        
		        MyLinkedListGraph graph = new MyLinkedListGraph(true, false);

		        for (int i = 1; i <= gNodes; i++) {
	    			Vertex v = new Vertex();

	    			v.setName(String.valueOf(i));
	    			graph.add(v);
	    		}
		        
		        for (int i = 0; i < gEdges; i++) {
		        	int sourceNode = reader.nextInt();
	    			int destNode = reader.nextInt();
	    			int edge = reader.nextInt();
		        	
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
	    			
	    			sourceVertex.setName(String.valueOf(sourceNode));
	    			sourceVertex.setEdge(edge);
	    			sourceVertex.setParent(destNodeList.get(0));
	    			sourceVertex.setDistance(Integer.MAX_VALUE);
	    			sourceVertex.setMainVertexListIndex(sourceNodeList.get(0).getArrayIndex());
	    			
	    			destNodeList.add(sourceVertex);
	    			sourceVertex.setArrayIndex(destNodeList.size() - 1);
		        }
		        
		        getCost(gNodes, graph);
		        
				isCompleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 

		
		/*
		 * try (BufferedReader bufferedReader = new BufferedReader(new
		 * FileReader(file))) { while (!isCompleted) { String[] firstMultipleInput =
		 * bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
		 * 
		 * int n = Integer.parseInt(firstMultipleInput[0]);
		 * 
		 * int m = Integer.parseInt(firstMultipleInput[1]);
		 * 
		 * int k = Integer.parseInt(firstMultipleInput[2]);
		 * 
		 * List<String> centers = IntStream.range(0, n).mapToObj(i -> { try { return
		 * bufferedReader.readLine(); } catch (IOException ex) { throw new
		 * RuntimeException(ex); } }) .collect(Collectors.toList());
		 * 
		 * List<List<Integer>> roads = new ArrayList<>();
		 * 
		 * IntStream.range(0, m).forEach(i -> { try { roads.add(
		 * Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
		 * .map(Integer::parseInt) .collect(Collectors.toList()) ); } catch (IOException
		 * ex) { throw new RuntimeException(ex); } });
		 * 
		 * int res = shop(n, k, centers, roads);
		 * 
		 * System.out.println(res); System.out.println(); isCompleted = true; } } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		 
	}
	
	public static void getCost(int gNodes, MyLinkedListGraph graph) {
	    singleSourceShortestPathUsingDijkstra(graph, 1);
	    if (Integer.MAX_VALUE != graph.getVertices().get(gNodes - 1).get(0).getDistance()) {
	    	System.out.println(graph.getVertices().get(gNodes - 1).get(0).getDistance());
	    } else {
	    	System.out.println("NO PATH EXISTS");
	    }
	}
	
	static void singleSourceShortestPathUsingDijkstra(MyLinkedListGraph graph, int startIndex) {
		graph.getVertices().get(startIndex - 1).get(0).setDistance(0);
		
		MyIndexedArrayBinaryMinHeapTree q = addAllVerticesInPriorityQueueTemp(graph);
		
		while (!q.isEmpty()) {
			Vertex vertex = q.extractMin();
			
			List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
			
			for (int i = 1; i < adjacentVertices.size(); i++) {
				int newDistance = Integer.MAX_VALUE;
				if (Integer.MAX_VALUE != vertex.getDistance()) {
					int nodalDistance = adjacentVertices.get(i).getEdge() - vertex.getDistance();
					
					if (nodalDistance > 0) {
						newDistance = vertex.getDistance() + nodalDistance;
					} else {
						newDistance = vertex.getDistance();
					}
				}
				int mainVertexListIndex = adjacentVertices.get(i).getMainVertexListIndex();
				if (newDistance < graph.getVertices().get(mainVertexListIndex).get(0).getDistance()) {
					
					adjacentVertices.get(i).setDistance(newDistance);
					adjacentVertices.get(i).setParent(vertex);
					
					graph.getVertices().get(mainVertexListIndex).get(0).setDistance(newDistance);
					graph.getVertices().get(mainVertexListIndex).get(0).setParent(vertex);
					
					q.delete(graph.getVertices().get(mainVertexListIndex).get(0));
					q.add(graph.getVertices().get(mainVertexListIndex).get(0));
				}
			}
		}
	}
	
	static MyIndexedArrayBinaryMinHeapTree addAllVerticesInPriorityQueueTemp(MyLinkedListGraph graph) {
		MyIndexedArrayBinaryMinHeapTree q = new MyIndexedArrayBinaryMinHeapTree(graph.getVertices().size());
		for (List<Vertex> temp : graph.getVertices()) {
			q.add(temp.get(0));
		}
		return q;
	}

}
