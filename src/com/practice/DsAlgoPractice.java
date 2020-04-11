package com.practice;

import java.io.IOException;

import com.ds.impl.MyGraphAlgos;
import com.ds.impl.MyLinkedListGraph;
import com.ds.impl.Vertex;
import com.practice.util.DsAlgoUtils;

class Node {

	Node(int data) {
		this.data = data;
	}

	int data;
	int depth;
	Node left;
	Node right;
}

public class DsAlgoPractice {

	public static void main(String[] args) throws IOException {
		/*
		 * String file = "src/input.txt"; int count = 0; String line; int[] a = null;
		 * 
		 * try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		 * while ((line = reader.readLine()) != null) if (0 == count) { int n =
		 * Integer.parseInt(line); a = new int[n]; count++; } else { a[count - 1] =
		 * Integer.parseInt(line); count++; } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		MyLinkedListGraph allPairGraph = DsAlgoUtils.getPositiveWeightedSampleGraphForAllPairShortestPathAlgo();
		
		MyGraphAlgos<Vertex> graphAlgos = new MyGraphAlgos<>();
		graphAlgos.allPairShortestPathUsingFloydWarshall(allPairGraph);
		graphAlgos.printDistancesForAllPairShortestPath(allPairGraph);
	}
	

}
