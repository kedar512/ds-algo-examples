package com.ds.hackerrank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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
	
	public boolean makeSet(List<Integer> elements) {
		if (null == sets) {
			sets = new ArrayList<>();
		}
		
		for (Integer e: elements) {
			List<Integer> elemList = new ArrayList<>();
			elemList.add(e);
			
			MySet<Integer> set = new MySet<>();
			
			set.setElements(elemList);
			sets.add(set);
		}
		return true;
	}
	
	public boolean union(String firstSet, String secondSet) {
		int firstSetIndex = Integer.parseInt(firstSet);
		int secondSetIndex = Integer.parseInt(secondSet);
		
		MySet<Integer> set1 = sets.get(firstSetIndex - 1);
		MySet<Integer> set2 = sets.get(secondSetIndex - 1);
		
		while (null != set1.getMovedToSet()) {
			set1 = set1.getMovedToSet();
		}
		
		while (null != set2.getMovedToSet()) {
			set2 = set2.getMovedToSet();
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
	
	public MySet<Integer> findSet(String element) {
		
		MySet<Integer> set = sets.get(Integer.parseInt(element) - 1);
		
		while (null != set.getMovedToSet()) {
			set = set.getMovedToSet();
		}
		return set;
	}
}

public class MinSpanningTree {
	public static void main() throws IOException {

		String file = "src/hk_kruskal_mst.txt";
		boolean isCompleted = false;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			while (!isCompleted) {
				String[] gNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

		        int gNodes = Integer.parseInt(gNodesEdges[0]);
		        int gEdges = Integer.parseInt(gNodesEdges[1]);

		        List<Integer> gFrom = new ArrayList<>();
		        List<Integer> gTo = new ArrayList<>();
		        List<Integer> gWeight = new ArrayList<>();

		        IntStream.range(0, gEdges).forEach(i -> {
		            try {
		                String[] gFromToWeight = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

		                gFrom.add(Integer.parseInt(gFromToWeight[0]));
		                gTo.add(Integer.parseInt(gFromToWeight[1]));
		                gWeight.add(Integer.parseInt(gFromToWeight[2]));
		            } catch (IOException ex) {
		                throw new RuntimeException(ex);
		            }
		        });

		        int res = kruskals(gNodes, gFrom, gTo, gWeight);
		        System.out.println(res);
				isCompleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	// gNodes -> No of nodes
	// gFrom -> source node list
	// gTo -> destination node list
	// gWeight -> weight from source to destination list
	public static int kruskals(int gNodes, List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight) {
		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);
		DisjointSet ds = getDisjointSetForKruskalAlgo(gNodes);
		List<Vertex> edgeList = new ArrayList<>();

		for (int i = 1; i <= gNodes; i++) {
			Vertex v = new Vertex();

			v.setName(String.valueOf(i));
			graph.add(v);
		}
		
		for (int i = 0; i < gFrom.size(); i++) {
			Integer sourceNode = gFrom.get(i);
			Integer destNode = gTo.get(i);
			Integer weight = gWeight.get(i);
			
			Vertex destVertex = new Vertex();
			Vertex sourceVertex = new Vertex();

			List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
			List<Vertex> destNodeList = graph.getVertices().get(destNode - 1);
			
			destVertex.setName(String.valueOf(destNode));
			destVertex.setEdge(weight);
			destVertex.setParent(sourceNodeList.get(0));
			destVertex.setDistance(Integer.MAX_VALUE);
			destVertex.setMainVertexListIndex(destNodeList.get(0).getArrayIndex());
			
			sourceNodeList.add(destVertex);
			destVertex.setArrayIndex(sourceNodeList.size() - 1);
			
			sourceVertex.setName(String.valueOf(sourceNode));
			sourceVertex.setEdge(weight);
			sourceVertex.setParent(destNodeList.get(0));
			sourceVertex.setDistance(Integer.MAX_VALUE);
			sourceVertex.setMainVertexListIndex(sourceNodeList.get(0).getArrayIndex());
			
			destNodeList.add(sourceVertex);
			sourceVertex.setArrayIndex(destNodeList.size() - 1);
			
			edgeList.add(sourceVertex);
			edgeList.add(destVertex);
		}
		
		Collections.sort(edgeList, (v1, v2) -> {
			return v1.getEdge() - v2.getEdge();
		});
		
		int totalWeight = kruskalAlgoForMinimumSpanningTree(graph, ds, edgeList);
		
		return totalWeight;
    }
	
	public static DisjointSet getDisjointSetForKruskalAlgo(int gNodes) {
		DisjointSet ds = new DisjointSet();
		List<Integer> elements = new ArrayList<>();
		
		for (int i = 1; i <= gNodes; i++) {
			elements.add(i);
		}
		
		ds.makeSet(elements);
		return ds;
	}
	
	public static int kruskalAlgoForMinimumSpanningTree(MyLinkedListGraph graph, DisjointSet ds, List<Vertex> edgeList) {
		int totalWeight = 0;
		for (int i = 0; i < edgeList.size(); i++) {
			Vertex parent = edgeList.get(i).getParent();
			
			if (!ds.findSet(edgeList.get(i).getName()).getElements().get(0).equals(ds.findSet(parent.getName()).getElements().get(0))) {
				totalWeight += edgeList.get(i).getEdge();
				ds.union(edgeList.get(i).getName(), parent.getName());
				int mainVertexListIndex = parent.getArrayIndex();
				int index = edgeList.get(i).getArrayIndex();
				graph.getVertices().get(mainVertexListIndex).get(index).setDistance(edgeList.get(i).getEdge());
				graph.getVertices().get(edgeList.get(i).getMainVertexListIndex()).get(edgeList.get(i + 1).getArrayIndex())
						.setDistance(edgeList.get(i).getEdge());
				i++;
			}
		}
		return totalWeight;
	}
}
