package com.practice.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import com.ds.impl.MyArrayBinaryMaxHeapTree;
import com.ds.impl.MyArrayBinaryMinHeapTree;
import com.ds.impl.MyDisjointSet;
import com.ds.impl.MyLinkedListGraph;
import com.ds.impl.Vertex;

public class DsAlgoUtils {

	public static void readMultipleArgumentsFromStdin() {
		try (Scanner in = new Scanner(System.in)) {
			Integer noOfInputs = 0;
			int count = -1;

			while (in.hasNext() && noOfInputs >= count) {
				if (-1 == count) {
					noOfInputs = Integer.valueOf(in.nextLine());
					count++;
				} else {
					String s = in.nextLine();
					System.out.println(s);
					// logic here
					count++;
				}
			}
		}
	}

	public static void readMultipleArgumentsFromFile() {
		String file = "src/input.txt";
		int count = 0;
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null)
				if (0 == count) {
					System.out.println("Number of inputs: " + line);
					count++;
				} else {
					// Logic here
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readDataForHackerrankInput() throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = Integer.parseInt(reader.readLine());

		String[] unsorted = new String[n];

		for (int i = 0; i < n; i++) {
			String unsortedItem = reader.readLine();
			unsorted[i] = unsortedItem;
		}

		// calling sample method
		String[] result = bigSorting(unsorted);

		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(result[i]);

			if (i != result.length - 1) {
				bufferedWriter.write("\n");
			}
		}

		bufferedWriter.newLine();
		bufferedWriter.close();
		reader.close();
	}
	
	public static List<Integer> primeFactors(int n) {
		// Print the number of 2s that divide n
		List<Integer> factors = new ArrayList<>();

		while (n % 2 == 0) {
			factors.add(2);
			n /= 2;
		}

		// n must be odd at this point. So we can
		// skip one element (Note i = i +2)
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			// While i divides n, print i and divide n
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}

		// This condition is to handle the case whien
		// n is a prime number greater than 2
		if (n > 2) {
			factors.add(n);
		}
		return factors;
	}

	private static String[] bigSorting(String[] unsorted) {
		return unsorted;
	}

	public static Integer findMaxInt(Stack<Integer> stack) {
		return stack.stream().max(Integer::compare).get();
	}
	
	static double[] runningMedian(int[] a) {
		double[] medArr = new double[a.length];
		MyArrayBinaryMinHeapTree minHeapTree = new MyArrayBinaryMinHeapTree(a.length);
		MyArrayBinaryMaxHeapTree maxHeapTree = new MyArrayBinaryMaxHeapTree(a.length);

		for (int i = 0; i < a.length; i++) {
			double med = 0;
			int size = 0;

			if (1 == minHeapTree.getSize() && 1 == maxHeapTree.getSize()) {
				if (minHeapTree.peekMin() < maxHeapTree.peekMax()) {
					int temp = minHeapTree.extractMin();
					minHeapTree.add(maxHeapTree.extractMax());
					maxHeapTree.add(temp);
				}
			}

			if (minHeapTree.isEmpty()) {
				minHeapTree.add(a[i]);
			} else if (maxHeapTree.isEmpty()) {
				maxHeapTree.add(a[i]);
			} else if (a[i] <= maxHeapTree.peekMax()) {
				if (maxHeapTree.getSize() > minHeapTree.getSize()) {
					minHeapTree.add(maxHeapTree.extractMax());
				}
				maxHeapTree.add(a[i]);
			} else {
				if (minHeapTree.getSize() > maxHeapTree.getSize()) {
					if (a[i] >= minHeapTree.peekMin()) {
						maxHeapTree.add(minHeapTree.extractMin());
						minHeapTree.add(a[i]);
					} else {
						maxHeapTree.add(a[i]);
					}
				} else {
					minHeapTree.add(a[i]);
				}
			}

			size = minHeapTree.getSize() + maxHeapTree.getSize();
			if (maxHeapTree.isEmpty()) {
				med = a[i];
			} else if (size % 2 == 0) {
				int med1 = minHeapTree.peekMin();
				int med2 = maxHeapTree.peekMax();

				med = (double) (med1 + med2) / 2;
			} else {
				if (minHeapTree.getSize() > maxHeapTree.getSize()) {
					med = minHeapTree.peekMin();
				} else {
					med = maxHeapTree.peekMax();
				}
			}
			med = Math.round(med * 10) / 10.0d;
			medArr[i] = med;
		}

		return medArr;
	}
	
	public static MyLinkedListGraph getPositiveWeightedSampleGraphForSingleSourceShortestPathAlgo() {
		MyLinkedListGraph graph = new MyLinkedListGraph(true, true);
		
		Vertex v = new Vertex();
		
		v.setName("A");
		graph.add(v);
		
		v = new Vertex();
		v.setName("B");
		graph.add(v);
		
		v = new Vertex();
		v.setName("C");
		graph.add(v);
		
		v = new Vertex();
		v.setName("D");
		graph.add(v);
		
		v = new Vertex();
		v.setName("E");
		graph.add(v);
		
		graph.connectVerticesForShortestPath("A", "C", 6);
		graph.connectVerticesForShortestPath("A", "D", 6);
		graph.connectVerticesForShortestPath("B", "A", 3);
		graph.connectVerticesForShortestPath("C", "D", 2);
		graph.connectVerticesForShortestPath("D", "B", 1);
		graph.connectVerticesForShortestPath("D", "C", 1);
		graph.connectVerticesForShortestPath("E", "B", 4);
		graph.connectVerticesForShortestPath("E", "D", 2);
		
		return graph;
	}
	
	public static MyLinkedListGraph getPositiveWeightedSampleGraphForAllPairShortestPathAlgo() {
		MyLinkedListGraph graph = new MyLinkedListGraph(true, true);
		
		Vertex v = new Vertex();
		
		v.setName("A");
		graph.addForAllPairShortestPath(v);
		
		v = new Vertex();
		v.setName("B");
		graph.addForAllPairShortestPath(v);
		
		v = new Vertex();
		v.setName("C");
		graph.addForAllPairShortestPath(v);
		
		v = new Vertex();
		v.setName("D");
		graph.addForAllPairShortestPath(v);
		
		graph.connectVerticesForAllPairShortestPath("A", "B", 8);
		graph.connectVerticesForAllPairShortestPath("A", "C", Integer.MAX_VALUE);
		graph.connectVerticesForAllPairShortestPath("A", "D", 1);
		graph.connectVerticesForAllPairShortestPath("B", "A", Integer.MAX_VALUE);
		graph.connectVerticesForAllPairShortestPath("B", "C", 1);
		graph.connectVerticesForAllPairShortestPath("B", "D", Integer.MAX_VALUE);
		graph.connectVerticesForAllPairShortestPath("C", "A", 4);
		graph.connectVerticesForAllPairShortestPath("C", "B", Integer.MAX_VALUE);
		graph.connectVerticesForAllPairShortestPath("C", "D", Integer.MAX_VALUE);
		graph.connectVerticesForAllPairShortestPath("D", "A", Integer.MAX_VALUE);
		graph.connectVerticesForAllPairShortestPath("D", "B", 2);
		graph.connectVerticesForAllPairShortestPath("D", "C", 9);
		
		return graph;
	}
	
	public static MyLinkedListGraph getPositiveWeightedSampleGraphForMinSpanningTreeAlgo() {
		MyLinkedListGraph graph = new MyLinkedListGraph(false, true);
		
		Vertex v = new Vertex();
		
		v.setName("A");
		graph.add(v);
		
		v = new Vertex();
		v.setName("B");
		graph.add(v);
		
		v = new Vertex();
		v.setName("C");
		graph.add(v);
		
		v = new Vertex();
		v.setName("D");
		graph.add(v);
		
		v = new Vertex();
		v.setName("E");
		graph.add(v);
		
		graph.connectVerticesForMinSpanningTree("A", "B", 15);
		graph.connectVerticesForMinSpanningTree("A", "C", 20);
		graph.connectVerticesForMinSpanningTree("B", "A", 15);
		graph.connectVerticesForMinSpanningTree("B", "C", 13);
		graph.connectVerticesForMinSpanningTree("B", "D", 5);
		graph.connectVerticesForMinSpanningTree("C", "A", 20);
		graph.connectVerticesForMinSpanningTree("C", "B", 13);
		graph.connectVerticesForMinSpanningTree("C", "D", 10);
		graph.connectVerticesForMinSpanningTree("C", "E", 6);
		graph.connectVerticesForMinSpanningTree("D", "B", 5);
		graph.connectVerticesForMinSpanningTree("D", "C", 10);
		graph.connectVerticesForMinSpanningTree("D", "E", 8);
		graph.connectVerticesForMinSpanningTree("E", "C", 6);
		graph.connectVerticesForMinSpanningTree("E", "D", 8);
		
		return graph;
	}
	
	public static MyLinkedListGraph getPositiveWeightedSampleGraphForPrimsMinSpanningTreeAlgo() {
		MyLinkedListGraph graph = new MyLinkedListGraph(false, true);
		
		Vertex v = new Vertex();
		
		v.setName("A");
		graph.add(v);
		
		v = new Vertex();
		v.setName("B");
		graph.add(v);
		
		v = new Vertex();
		v.setName("C");
		graph.add(v);
		
		v = new Vertex();
		v.setName("D");
		graph.add(v);
		
		v = new Vertex();
		v.setName("E");
		graph.add(v);
		
		graph.connectVerticesForMinSpanningTree("A", "B", 10);
		graph.connectVerticesForMinSpanningTree("A", "C", 20);
		graph.connectVerticesForMinSpanningTree("B", "A", 10);
		graph.connectVerticesForMinSpanningTree("B", "C", 30);
		graph.connectVerticesForMinSpanningTree("B", "D", 5);
		graph.connectVerticesForMinSpanningTree("C", "A", 20);
		graph.connectVerticesForMinSpanningTree("C", "B", 30);
		graph.connectVerticesForMinSpanningTree("C", "D", 15);
		graph.connectVerticesForMinSpanningTree("C", "E", 6);
		graph.connectVerticesForMinSpanningTree("D", "B", 5);
		graph.connectVerticesForMinSpanningTree("D", "C", 15);
		graph.connectVerticesForMinSpanningTree("D", "E", 8);
		graph.connectVerticesForMinSpanningTree("E", "C", 6);
		graph.connectVerticesForMinSpanningTree("E", "D", 8);
		
		return graph;
	}
	
	public static MyDisjointSet<String> getDisjointSetForKruskalAlgo() {
		MyDisjointSet<String> ds = new MyDisjointSet<>();
		List<String> elements = new ArrayList<>();
		
		elements.add("A");
		elements.add("B");
		elements.add("C");
		elements.add("D");
		elements.add("E");
		
		ds.makeSet(elements);
		return ds;
	}
	
	public static MyLinkedListGraph getNegativeWeightedSampleGraphForSingleSourceShortestPathAlgo() {
		MyLinkedListGraph graph = new MyLinkedListGraph(true, true);
		
		Vertex v = new Vertex();
		
		v.setName("A");
		graph.add(v);
		
		v = new Vertex();
		v.setName("B");
		graph.add(v);
		
		v = new Vertex();
		v.setName("C");
		graph.add(v);
		
		v = new Vertex();
		v.setName("D");
		graph.add(v);
		
		v = new Vertex();
		v.setName("E");
		graph.add(v);
		
		graph.connectVerticesForShortestPath("A", "C", 6);
		graph.connectVerticesForShortestPath("A", "D", -6);
		graph.connectVerticesForShortestPath("B", "A", 3);
		graph.connectVerticesForShortestPath("C", "D", 2);
		graph.connectVerticesForShortestPath("D", "B", 1);
		graph.connectVerticesForShortestPath("D", "C", 1);
		graph.connectVerticesForShortestPath("E", "B", 4);
		graph.connectVerticesForShortestPath("E", "D", 2);
		
		return graph;
	}
	
	public static int[][] makeGraph(int noOfNodes, int[][] connArr) {
		int[][] graph = new int[noOfNodes + 1][noOfNodes + 1];
		
		for (int i = 0; i < connArr.length; i++) {
			int sourceNode = connArr[i][0];
    		int destNode = connArr[i][1];
    		
    		graph[sourceNode][destNode] = 1;
    		graph[destNode][sourceNode] = 1;
		}
		
		return graph;
	}
	
	public MyLinkedListGraph iterateGraphUsingDFS(int n, int[][] cities) {
		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);
		List<Set<Vertex>> connSetList = new ArrayList<>();

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
		return graph;
	}
}
