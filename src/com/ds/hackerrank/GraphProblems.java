package com.ds.hackerrank;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.ds.impl.Vertex;

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

public class GraphProblems {
	
	// Solved using bit mask subset and DP
	public static void synchronousShoppingProblemStart() throws IOException {

		String filePath = "src/hk_sync_shopping.txt";
		boolean isCompleted = false;
		File file = new File(filePath);
		
		
		try (InputStream in = new FileInputStream(file)) {
			FastReader reader = new FastReader(in);
			while (!isCompleted) {
				int n = reader.nextInt();

		        int m = reader.nextInt();

		        int k = reader.nextInt();

		        int[] centerBits = new int[n];

		        for (int i = 0; i < n; i++) {
		            int fishTypes = reader.nextInt();
		            if (fishTypes > 0) {
		                for (int j = 0; j < fishTypes; j++) {
		                	int fishType = reader.nextInt();
		                    centerBits[i] = setKthBit(centerBits[i], fishType - 1);
		                }
		            }
		        }
		        
		        Map<Integer, List<PathDetails>> connMap = new HashMap<>();

		        for (int i = 0; i < m; i++) {
		            int sourceNode = reader.nextInt();
		            int destNode = reader.nextInt();
		            int time = reader.nextInt();
		            
		            List<PathDetails> sourceNodePaths = connMap.get(sourceNode);
		            List<PathDetails> destNodePaths = connMap.get(destNode);
		            
		            PathDetails sourceToDestPath = new PathDetails();
		            PathDetails destToSourcePath = new PathDetails();
		            
		            sourceToDestPath.setDestNode(destNode);
		            sourceToDestPath.setTime(time);
		            
		            destToSourcePath.setDestNode(sourceNode);
		            destToSourcePath.setTime(time);
		            
		            if (null == sourceNodePaths) {
		            	List<PathDetails> paths = new ArrayList<>();
		            	paths.add(sourceToDestPath);
		            	connMap.put(sourceNode, paths);
		            } else {
		            	sourceNodePaths.add(sourceToDestPath);
		            }
		            
		            if (null == destNodePaths) {
		            	List<PathDetails> paths = new ArrayList<>();
		            	paths.add(destToSourcePath);
		            	connMap.put(destNode, paths);
		            } else {
		            	destNodePaths.add(destToSourcePath);
		            }

		        }

		        int res = shop(n, k, centerBits, connMap);

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
	
	public static int shop(int n, int k, int[] centerBits, Map<Integer, List<PathDetails>> connMap) {
		int minTime = Integer.MAX_VALUE;
		int[][] minTimeArr = new int[n][(int) Math.pow(2.0, Double.valueOf(String.valueOf(k)))];
		boolean[][] visitedArr = new boolean[n][(int) Math.pow(2.0, Double.valueOf(String.valueOf(k)))];
		int allFishTypesBits = 0;
		
		for (int i = 0; i < k; i++) {
			allFishTypesBits = setKthBit(allFishTypesBits, i);
		}
		
		for (int i = 0; i < minTimeArr.length; i++) {
			for (int j = 0; j < minTimeArr[i].length; j++) {
				minTimeArr[i][j] = Integer.MAX_VALUE;
			}
		}
		
		minTimeArr[0][centerBits[0]] = 0;
        
        PriorityQueue<Vertex> q = new PriorityQueue<>(1000, (v1, v2) ->
        v1.getDistance() > v2.getDistance() ? 1 : (v2.getDistance() > v1.getDistance() ? -1 : 0));
        
        Vertex initialVertex = new Vertex();
        
        initialVertex.setMainVertexListIndex(0);
        initialVertex.setDistance(0);
        initialVertex.setMaskedBits(centerBits[0]);
		
		q.add(initialVertex);
		
		while (!q.isEmpty()) {
			Vertex vertex = q.poll();
			
			int nodeIndex = vertex.getMainVertexListIndex();
			int fishTypeBits = vertex.getMaskedBits();
			int time = vertex.getDistance();
			List<PathDetails> adjacentVertices = connMap.get(nodeIndex + 1);
			
			for (PathDetails path : adjacentVertices) {
				int totalTime = time + path.getTime();
				int destNodeIndex = path.getDestNode() - 1;
				int totalFishTypeBits = fishTypeBits | centerBits[destNodeIndex];
				int currentMinTime = minTimeArr[destNodeIndex][totalFishTypeBits];
				
				if (!visitedArr[destNodeIndex][totalFishTypeBits] && totalTime < currentMinTime) {
					
					Vertex toVisit = new Vertex();
					
					toVisit.setMainVertexListIndex(destNodeIndex);
					toVisit.setDistance(totalTime);
					toVisit.setMaskedBits(totalFishTypeBits);
					
					q.add(toVisit);
					minTimeArr[destNodeIndex][totalFishTypeBits] = totalTime;
				}
			}
			visitedArr[nodeIndex][fishTypeBits] = true;
		}
		int lastRow = minTimeArr.length - 1;
		for (int i = 0; i < minTimeArr[lastRow].length; i++) {
			for (int j = 0; j < minTimeArr[lastRow].length; j++) {
				int totalFishTypes = i | j;
				
				if (allFishTypesBits == totalFishTypes) {
					int time1 = minTimeArr[lastRow][i];
					int time2 = minTimeArr[lastRow][j];
					
					if (Integer.MAX_VALUE != time1 && Integer.MAX_VALUE != time2) {
						int maxTime = Math.max(time1, time2);
						minTime = Math.min(minTime, maxTime);
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
