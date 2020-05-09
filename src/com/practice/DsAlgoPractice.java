package com.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.ds.hackerrank.FastReader;
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
	private int time;
	private Set<Integer> fishTypes;
	private int destNode;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destNode;
		result = prime * result + ((fishTypes == null) ? 0 : fishTypes.hashCode());
		result = prime * result + time;
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
		if (time != other.time)
			return false;
		return true;
	}
	public int getDestNode() {
		return destNode;
	}
	public void setDestNode(int destNode) {
		this.destNode = destNode;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
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
				int n = reader.nextInt();

		        int m = reader.nextInt();

		        int k = reader.nextInt();

		        List<List<Integer>> centers = new ArrayList<>();

		        for (int i = 0; i < n; i++) {
		            List<Integer> fishTypeList = new ArrayList<>();
		            int fishTypes = reader.nextInt();
		            if (fishTypes > 0) {
		                for (int j = 0; j < fishTypes; j++) {
		                    fishTypeList.add(reader.nextInt());
		                }
		            }
		            centers.add(fishTypeList);
		        }
		        
		        List<List<Integer>> roads = new ArrayList<>();

		        for (int i = 0; i < m; i++) {
		            List<Integer> connList = new ArrayList<>();

		            for (int j = 0; j < 3; j++) {
		                connList.add(reader.nextInt());
		            }
		            roads.add(connList);
		        }

		        int res = shop(n, k, centers, roads);

		        System.out.println(res);
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
	
	public static int shop(int n, int k, List<List<Integer>> centers, List<List<Integer>> roads) {
		int minTime = Integer.MAX_VALUE;
		int[][] minTimeArr = new int[n][(int) Math.pow(2.0, Double.valueOf(String.valueOf(k)))];
		int allFishTypesBits = 0;
		
		for (int i = 0; i < k; i++) {
			allFishTypesBits = setKthBit(allFishTypesBits, i);
		}
		
		for (int i = 0; i < minTimeArr.length; i++) {
			for (int j = 0; j < minTimeArr[i].length; j++) {
				minTimeArr[i][j] = Integer.MAX_VALUE;
			}
		}
		
		MyLinkedListGraph graph = new MyLinkedListGraph(true, false);

		for (int i = 1; i <= n; i++) {
			Vertex v = new Vertex();

			v.setName(String.valueOf(i));
			graph.add(v);
		}

        for (int i = 0; i < roads.size(); i++) {
			Integer sourceNode = roads.get(i).get(0);
			Integer destNode = roads.get(i).get(1);
			Integer edge = roads.get(i).get(2);
			int fishTypesBits = 0;
			int sourceFishTypesBits = 0;
			int destFishTypesBits = 0;
			List<Integer> sourceNodeFishTypes = centers.get(sourceNode - 1);
			List<Integer> destNodeFishTypes = centers.get(destNode - 1);
			
			for (Integer bit : sourceNodeFishTypes) {
				fishTypesBits = setKthBit(fishTypesBits, bit - 1);
				sourceFishTypesBits = setKthBit(sourceFishTypesBits, bit - 1);
			}
			graph.getVertices().get(sourceNode - 1).get(0).setMaskedBits(sourceFishTypesBits);
			
			for (Integer bit : destNodeFishTypes) {
				fishTypesBits = setKthBit(fishTypesBits, bit - 1);
				destFishTypesBits = setKthBit(destFishTypesBits, bit - 1);
			}
			graph.getVertices().get(destNode - 1).get(0).setMaskedBits(destFishTypesBits);
			
			Vertex destVertex = new Vertex();
			Vertex sourceVertex = new Vertex();

			List<Vertex> sourceNodeList = graph.getVertices().get(sourceNode - 1);
			List<Vertex> destNodeList = graph.getVertices().get(destNode - 1);
			
			destVertex.setName(String.valueOf(destNode));
			destVertex.setEdge(edge);
			destVertex.setParent(sourceNodeList.get(0));
			destVertex.setDistance(Integer.MAX_VALUE);
			destVertex.setMaskedBits(fishTypesBits);
			destVertex.setMainVertexListIndex(destNodeList.get(0).getArrayIndex());
			
			sourceNodeList.add(destVertex);
			destVertex.setArrayIndex(sourceNodeList.size() - 1);
			
			sourceVertex.setName(String.valueOf(sourceNode));
			sourceVertex.setEdge(edge);
			sourceVertex.setParent(destNodeList.get(0));
			sourceVertex.setDistance(Integer.MAX_VALUE);
			sourceVertex.setMaskedBits(fishTypesBits);
			sourceVertex.setMainVertexListIndex(sourceNodeList.get(0).getArrayIndex());
			
			destNodeList.add(sourceVertex);
			sourceVertex.setArrayIndex(destNodeList.size() - 1);
        }
        
        Queue<Vertex> q = new LinkedList<>();
		
		q.add(graph.getVertices().get(0).get(0));
		graph.getVertices().get(0).get(0).setDistance(0);
		
		while (!q.isEmpty()) {
			Vertex vertex = q.poll();
			
			if (!vertex.isVisited()) {
				vertex.setVisited(true);
				List<Vertex> adjacentVertices = graph.getVertices().get(vertex.getArrayIndex());
				
				for (int i = 1; i < adjacentVertices.size(); i++) {
					Vertex dest = adjacentVertices.get(i);
					if (!dest.isVisited()) {
						int time = vertex.getDistance() + dest.getEdge();
						int fishTypesBits = vertex.getMaskedBits() | dest.getMaskedBits();
						int currentMinTime = minTimeArr[dest.getMainVertexListIndex()][fishTypesBits];
						
						if (time < currentMinTime) {
							minTimeArr[dest.getMainVertexListIndex()][fishTypesBits] = time;
							dest.setDistance(time);
							dest.setMaskedBits(fishTypesBits);
							graph.getVertices().get(dest.getMainVertexListIndex()).get(0).setMaskedBits(fishTypesBits);
							graph.getVertices().get(dest.getMainVertexListIndex()).get(0).setDistance(time);
							q.add(graph.getVertices().get(dest.getMainVertexListIndex()).get(0));
						}
					}
				}
			}
		}
		
		for (int i = 0; i < minTimeArr[k - 1].length; i++) {
			for (int j = i + 1; j < minTimeArr[k - 1].length; j++) {
				int totalFishTypes = i | j;
				
				if (allFishTypesBits == totalFishTypes) {
					int time1 = minTimeArr[k - 1][i];
					int time2 = minTimeArr[k - 1][j];
					int maxTime = 0;
					
					if (time1 > time2) {
						maxTime = time1;
					} else {
						maxTime = time2;
					}
					if (maxTime < minTime) {
						minTime = maxTime;
					}
				}
			}
		}
		
		return minTime;
	}
	
	static int setKthBit(int n, int k) { 
	    // kth bit of n is being set by this operation 
	    return ((1 << k) | n); 
	}

}
