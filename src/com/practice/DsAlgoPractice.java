package com.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.ds.impl.MyLinkedListGraph;
import com.ds.impl.Vertex;

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

		String file = "src/input.txt";
		int count = 0;
		String line;
		int[] a = null;
		int q = 0;
		boolean isCompleted = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (!isCompleted) {
				q = Integer.parseInt(reader.readLine());
				for (int qItr = 0; qItr < q; qItr++) {
					String[] nmC_libC_road = reader.readLine().split(" ");

					int n = Integer.parseInt(nmC_libC_road[0]);

					int m = Integer.parseInt(nmC_libC_road[1]);

					int c_lib = Integer.parseInt(nmC_libC_road[2]);

					int c_road = Integer.parseInt(nmC_libC_road[3]);

					int[][] cities = new int[m][2];

					for (int i = 0; i < m; i++) {
						String[] citiesRowItems = reader.readLine().split(" ");

						for (int j = 0; j < 2; j++) {
							int citiesItem = Integer.parseInt(citiesRowItems[j]);
							cities[i][j] = citiesItem;
						}
					}

					long result = roadsAndLibraries(n, c_lib, c_road, cities);
					System.out.println(result);
				}
				isCompleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * int[][] cities = new int[2][2];
		 * 
		 * cities[0][0] = 8; cities[0][1] = 2; cities[1][0] = 2; cities[1][1] = 9;
		 * 
		 * cities[2][0] = 2; cities[2][1] = 4; cities[3][0] = 1; cities[3][1] = 2;
		 * cities[4][0] = 2; cities[4][1] = 3; cities[5][0] = 5; cities[5][1] = 6;
		 * 
		 * 
		 * //int[][] graph = DsAlgoUtils.makeGraph(3, cities);
		 * 
		 * long minCost = roadsAndLibraries(9, 91, 84, cities);
		 * System.out.println(minCost);
		 */
	}

	// Complete the roadsAndLibraries function below.
	static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
		long totalCost = 0;
		// List<List<Vertex>> connList = new ArrayList<>();
		List<Set<Vertex>> connSetList = new ArrayList<>();
		if (0 == cities.length || c_lib <= c_road) {
			return (long) n * c_lib;
		}

		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);

		for (int i = 1; i <= n; i++) {
			Vertex v = new Vertex();

			v.setName(String.valueOf(i));
			graph.add(v);
		}

		for (int i = 0; i < cities.length; i++) {
			int sourceNode = cities[i][0];
			int destNode = cities[i][1];

			List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
			Vertex destVertex = graph.getVertices().get(destNode - 1).get(0);

			List<Vertex> destNodeList = graph.getVertices().get(destNode - 1);
			Vertex sourceVertex = graph.getVertices().get(sourceNode - 1).get(0);

			sourceNodeList.add(destVertex);
			destNodeList.add(sourceVertex);
		}

		Stack<Vertex> stack = new Stack<>();

		for (List<Vertex> temp : graph.getVertices()) {
			if (temp.size() <= 1) {
				continue;
			}
			if (!temp.get(0).isVisited()) {
				stack.push(temp.get(0));
				Set<Vertex> connNodes = new HashSet<>();
				connSetList.add(connNodes);
				while (!stack.isEmpty()) {
					Vertex vertex = stack.pop();

					if (!vertex.isVisited()) {
						connSetList.get(connSetList.size() - 1).add(vertex);
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

		long totalNodeCount = 0;
		for (int i = 0; i < connSetList.size(); i++) {
			int connCount = connSetList.get(i).size();
			System.out.println("Set size:" + connCount);
			long tempCost = c_lib + ((connCount - 1) * c_road);
			totalCost += tempCost;
			totalNodeCount += connCount;
		}

		if (n > totalNodeCount) {
			totalCost += (n - totalNodeCount) * c_lib;
		}

		return totalCost;
	}

}
