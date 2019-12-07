package com.ds.impl;

import java.util.List;

public class MyDivideAndConquerAlgos {
	public static int findNthElementInFibonacci(int nthElement) {
		return fibonacciRecursive(nthElement);
	}
	
	private static int fibonacciRecursive(int n) {
		if (1 == n) {
			return 0;
		} else if (2 == n) {
			return 1;
		} else {
			return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
		}
	}
	
	public static int numberFactorProblem(int n) {
		if (0 == n || 1 == n || 2 == n) {
			return 1;
		}
		
		if (3 == n) {
			return 2;
		}
		
		return numberFactorProblem(n - 1) + numberFactorProblem(n - 3) + numberFactorProblem(n - 4);
	}
	
	public static int houseThiefProblem(int[] houseNetWorthArr, int currIndex) {
		if (currIndex >= houseNetWorthArr.length) {
			return 0;
		}
		
		int currHouseNetWorth = houseNetWorthArr[currIndex] + houseThiefProblem(houseNetWorthArr, currIndex + 2);
		int netWorthBySkippingCurrHouse = houseThiefProblem(houseNetWorthArr, currIndex + 1);
		return Math.max(currHouseNetWorth, netWorthBySkippingCurrHouse);
	}
	
	public static int convertOneStringToAnother(String s1, String s2) {
		return calculateNumberOfOperations(s1, s2, 0, 0);
	}
	
	private static int calculateNumberOfOperations(String s1, String s2, int i1, int i2) {
		if (i1 == s1.length()) {
			return s2.length() - i2;
		}
		
		if (i2 == s2.length()) {
			return s1.length() - i1;
		}
		
		if (s1.charAt(i1) == s2.charAt(i2)) {
			return calculateNumberOfOperations(s1, s2, i1 + 1, i2 + 1);
		}
		
		int c1 = 1 + calculateNumberOfOperations(s1, s2, i1 + 1, i2); // insert operation
		int c2 = 1 + calculateNumberOfOperations(s1, s2, i1, i2 + 1); // delete operation
		int c3 = 1 + calculateNumberOfOperations(s1, s2, i1 + 1, i2 + 1); // replace operation
		
		return calculateMin(c1, c2, c3);
	}
	
	private static int calculateMin(int a, int b, int c) {
		int min = a;
		
		if (b < a) {
			min = b;
		}
		
		return c < min ? c : min;
	}
	
	public static int zeroOneKnapsackProblem(List<Item> items, int capacity) {
		return calculateMaxProfit(items, capacity, 0);
	}
	
	private static int calculateMaxProfit(List<Item> items, int capacity, int currIndex) {
		if (capacity <= 0 || currIndex < 0 || currIndex >= items.size()) {
			return 0;
		}
		
		int profit1 = 0;
		
		if (items.get(currIndex).getWeight() <= capacity) {
			profit1 = items.get(currIndex).getValue() + calculateMaxProfit(items, capacity - items.get(currIndex).getWeight(), currIndex + 1);
		}
		int profit2 = calculateMaxProfit(items, capacity, currIndex + 1);
		
		return Math.max(profit1, profit2);
	}
	
	public static int findLongestCommonSubsequence(String s1, String s2) {
		return longestSubsequenceRecursive(s1, s2, 0, 0);
	}
	
	private static int longestSubsequenceRecursive(String s1, String s2, int i1, int i2) {
		if (i1 == s1.length() || i2 == s2.length()) {
			return 0;
		}
		
		int c3 = 0;
		
		if (s1.charAt(i1) == s2.charAt(i2)) {
			c3 = 1 + longestSubsequenceRecursive(s1, s2, i1 + 1, i2 + 1);
		}
		int c1 = longestSubsequenceRecursive(s1, s2, i1, i2 + 1);
		int c2 = longestSubsequenceRecursive(s1, s2, i1 + 1, i2);
		
		return calculateMax(c1, c2, c3);
	}
	
	private static int calculateMax(int a, int b, int c) {
		int max = a;
		
		if (b > a) {
			max = b;
		}
		
		return c > max ? c : max;
	}
	
	public static int findLongestPalindromicSubsequence(String s) {
		return longestPalindromicRecursive(s, 0, s.length() - 1);
	}
	
	private static int longestPalindromicRecursive(String s, int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			return 0;
		}
		
		if (startIndex == endIndex) {
			return 1;
		}
		
		int count1 = 0;
		if (s.charAt(startIndex) == s.charAt(endIndex)) {
			count1 = 2 + longestPalindromicRecursive(s, startIndex + 1, endIndex - 1);
		}
		
		int count2 = longestPalindromicRecursive(s, startIndex + 1, endIndex);
		int count3 = longestPalindromicRecursive(s, startIndex, endIndex - 1);
		
		return calculateMax(count1, count2, count3);
				
	}
	
	public static int findLongestPalindromicSubstring(String s) {
		return longestPalindromicSubstringRecursive(s, 0, s.length() - 1);
	}
	
	private static int longestPalindromicSubstringRecursive(String s, int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			return 0;
		}
		
		if (startIndex == endIndex) {
			return 1;
		}
		
		int count1 = 0;
		if (s.charAt(startIndex) == s.charAt(endIndex)) {
			int remaining = endIndex - startIndex - 1;
			if (remaining == longestPalindromicSubstringRecursive(s, startIndex + 1, endIndex - 1)) {
				count1 = 2 + longestPalindromicSubstringRecursive(s, startIndex + 1, endIndex - 1);
			}
		}
		
		int count2 = longestPalindromicSubstringRecursive(s, startIndex + 1, endIndex);
		int count3 = longestPalindromicSubstringRecursive(s, startIndex, endIndex - 1);
		
		return calculateMax(count1, count2, count3);
				
	}
	
	public static int findMinCost(int[][] arr) {
		return minCostRecursive(arr, arr.length - 1, arr.length - 1);
	}
	
	private static int minCostRecursive(int[][] arr, int row, int col) {
		if (-1 == row || -1 == col) {
			return Integer.MAX_VALUE;
		}
		
		if (0 == row && 0 == col) {
			return arr[0][0];
		}
		
		int minCost1 = minCostRecursive(arr, row - 1, col);
		int minCost2 = minCostRecursive(arr, row, col - 1);
		
		int minCost = Math.min(minCost1, minCost2);
		
		return minCost + arr[row][col];
	}
	
	public static int findNumberOfPathsForGivenCost(int[][] arr, int cost) {
		return numberOfPathsRecursive(arr, cost, arr.length - 1, arr.length - 1);
	}
	
	private static int numberOfPathsRecursive(int[][] arr, int cost, int row, int col) {
		if (cost < 0) {
			return 0;
		}
		
		if (0 == row && 0 == col) {
			return (arr[0][0] - cost == 0) ? 1 : 0;
		}
		
		if (0 == row) {
			return numberOfPathsRecursive(arr, cost - arr[row][col], 0, col - 1);
		}
		
		if (0 == col) {
			return numberOfPathsRecursive(arr, cost - arr[row][col], row - 1, 0);
		}
		
		int noOfPathsFromPrevRow = numberOfPathsRecursive(arr, cost - arr[row][col], row - 1, col);
		int noOfPathsFromPrevCol = numberOfPathsRecursive(arr, cost - arr[row][col], row, col - 1);
		
		return noOfPathsFromPrevRow + noOfPathsFromPrevCol;
	}
}
