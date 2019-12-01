package com.ds.impl;

import java.util.List;

public class MyDynamicProgrammingAlgos {
	// Top down approach is also called as Memoization
	public static int findNthElementInFibonacciUsingTopDownApproach(int nthElement) {
		int[] prevElemValuesArr = new int[nthElement];
		prevElemValuesArr[0] = 0;
		prevElemValuesArr[1] = 1;
		return fibonacciRecursiveTopDown(prevElemValuesArr, nthElement);
	}
	
	private static int fibonacciRecursiveTopDown(int[] prevElemValuesArr, int n) {
		if (1 == n) {
			return 0;
		} else if (2 == n) {
			return 1;
		}
		
		if (0 == prevElemValuesArr[n - 1]) {
			prevElemValuesArr[n - 1] = fibonacciRecursiveTopDown(prevElemValuesArr, n - 1) + fibonacciRecursiveTopDown(prevElemValuesArr, n - 2);
		}
		return prevElemValuesArr[n - 1];
	}
	
	// Bottom up approach is also called as Tubulation
	public static int findNthElementInFibonacciUsingBottopUpApproach(int nthElement) {
		int[] prevElemValuesArr = new int[nthElement];
		prevElemValuesArr[0] = 0;
		prevElemValuesArr[1] = 1;
		return fibonacciRecursiveBottomUp(prevElemValuesArr, nthElement);
	}
	
	private static int fibonacciRecursiveBottomUp(int[] prevElemValuesArr, int n) {
		for (int i = 2; i < n; i++) {
			prevElemValuesArr[i] = prevElemValuesArr[i - 1] + prevElemValuesArr[i - 2];
		}
		return prevElemValuesArr[n - 1];
	}
	
	public static int numberFactorProblemUsingTopDownApproach(int n) {
		int[] prevResults = new int[n + 1];
		prevResults[0] = 1;
		prevResults[1] = 1;
		prevResults[2] = 1;
		prevResults[3] = 2;
		return numberFactorProblemRecursiveTopDownApproach(prevResults, n);
	}
	
	private static int numberFactorProblemRecursiveTopDownApproach(int[] prevResults, int n) {
		if (0 == prevResults[n]) {
			prevResults[n] = numberFactorProblemRecursiveTopDownApproach(prevResults, n - 1)
					+ numberFactorProblemRecursiveTopDownApproach(prevResults, n - 3)
					+ numberFactorProblemRecursiveTopDownApproach(prevResults, n - 4);
		}
		return prevResults[n];
	}
	
	public static int numberFactorProblemUsingBottomUpApproach(int n) {
		int[] prevResults = new int[n + 1];
		prevResults[0] = 1;
		prevResults[1] = 1;
		prevResults[2] = 1;
		prevResults[3] = 2;
		return numberFactorProblemRecursiveBottomUpApproach(prevResults, n);
	}
	
	private static int numberFactorProblemRecursiveBottomUpApproach(int[] prevResults, int n) {
		for (int i = n - 3; i <= n; i++) {
			prevResults[i] = prevResults[i - 1] + prevResults[i - 3] + prevResults[i - 4];
		}
		return prevResults[n];
	}
	
	public static int houseThiefProblemUsingTopDownApproach(int[] houseNetWorthArr, int currIndex) {
		int[] prevResults = new int[houseNetWorthArr.length];
		return houseThiefProblemRecursiveTopDown(prevResults, houseNetWorthArr, currIndex);
	}
	
	private static int houseThiefProblemRecursiveTopDown(int[] prevResults, int[] houseNetWorthArr, int currIndex) {
		if (currIndex >= houseNetWorthArr.length) {
			return 0;
		}
		
		if (0 == prevResults[currIndex]) {
			int currHouseNetWorth = houseNetWorthArr[currIndex] + houseThiefProblemRecursiveTopDown(prevResults, houseNetWorthArr, currIndex + 2);
			int netWorthBySkippingCurrHouse = houseThiefProblemRecursiveTopDown(prevResults, houseNetWorthArr, currIndex + 1);
			prevResults[currIndex] = Math.max(currHouseNetWorth, netWorthBySkippingCurrHouse);
		}
		return prevResults[currIndex];
	}
	
	public static int houseThiefProblemUsingBottomUpApproach(int[] houseNetWorthArr, int currIndex) {
		int[] prevResults = new int[houseNetWorthArr.length + 2];
		prevResults[houseNetWorthArr.length] = 0;
		return houseThiefProblemRecursiveBottomUp(prevResults, houseNetWorthArr, currIndex);
	}
	
	private static int houseThiefProblemRecursiveBottomUp(int[] prevResults, int[] houseNetWorthArr, int currIndex) {
		for (int i = houseNetWorthArr.length - 1; i >= 0; i--) {
			int currHouseNetWorth = houseNetWorthArr[i] + prevResults[i + 2];
			int netWorthBySkippingCurrHouse = prevResults[i + 1];
			prevResults[i] = Math.max(currHouseNetWorth, netWorthBySkippingCurrHouse);
		}
		return prevResults[0];
	}
	
	public static int convertOneStringToAnotherUsingTopDownApproach(String s1, String s2) {
		Integer[][] resultArr = new Integer[s1.length() + 1][s2.length() + 1]; 
		return calculateNumberOfOperationsTopDown(resultArr, s1, s2, 0, 0);
	}
	
	private static int calculateNumberOfOperationsTopDown(Integer[][] resultArr, String s1, String s2, int i1, int i2) {
		if (null == resultArr[i1][i2]) {
			if (i1 == s1.length()) {
				resultArr[i1][i2] = s2.length() - i2;
			} else if (i2 == s2.length()) {
				resultArr[i1][i2] = s1.length() - i1;
			} else if (s1.charAt(i1) == s2.charAt(i2)) {
				resultArr[i1][i2] = calculateNumberOfOperationsTopDown(resultArr, s1, s2, i1 + 1, i2 + 1);
			} else {
				int c1 = 1 + calculateNumberOfOperationsTopDown(resultArr, s1, s2, i1 + 1, i2); // insert operation
				int c2 = 1 + calculateNumberOfOperationsTopDown(resultArr, s1, s2, i1, i2 + 1); // delete operation
				int c3 = 1 + calculateNumberOfOperationsTopDown(resultArr, s1, s2, i1 + 1, i2 + 1); // replace operation
				
				resultArr[i1][i2] = calculateMin(c1, c2, c3);
			}
		}
		return resultArr[i1][i2];
	}
	
	private static int calculateMin(int a, int b, int c) {
		int min = a;
		
		if (b < a) {
			min = b;
		}
		
		return c < min ? c : min;
	}
	
	public static int convertOneStringToAnotherUsingBottomUpApproach(String s1, String s2) {
		Integer[][] resultArr = new Integer[s1.length() + 1][s2.length() + 1]; 
		return calculateNumberOfOperationsBottomUp(resultArr, s1, s2);
	}
	
	private static int calculateNumberOfOperationsBottomUp(Integer[][] resultArr, String s1, String s2) {
		for (int i = 0; i <= s1.length(); i++) {
			resultArr[i][0] = i;
		}
		
		for (int i = 0; i <= s2.length(); i++) {
			resultArr[0][i] = i;
		}
		
		for (int i = 1; i <= s1.length(); i++) {
			for (int j = 1; j <= s2.length(); j++) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					resultArr[i][j] = resultArr[i - 1][j - 1];
				} else {
					resultArr[i][j] = 1 + calculateMin(resultArr[i - 1][j], resultArr[i][j - 1], resultArr[i - 1][j - 1]);
				}
			}
		}
		return resultArr[s1.length()][s2.length()];
	}
	
	public static int zeroOneKnapsackProblemUsingTopDownApproach(List<Item> items, int capacity) {
		Integer[][] resultArr = new Integer[items.size()][capacity + 1];
		return calculateMaxProfitTopDown(resultArr, items, capacity, 0);
	}
	
	private static int calculateMaxProfitTopDown(Integer[][] resultArr, List<Item> items, int capacity, int currIndex) {
		if (capacity <= 0 || currIndex < 0 || currIndex >= items.size()) {
			return 0;
		}
		
		if (null != resultArr[currIndex][capacity]) {
			return resultArr[currIndex][capacity];
		}
		int profit1 = 0;
		
		if (items.get(currIndex).getWeight() <= capacity) {
			profit1 = items.get(currIndex).getValue() + calculateMaxProfitTopDown(resultArr, items,
					capacity - items.get(currIndex).getWeight(), currIndex + 1);
		}
		int profit2 = calculateMaxProfitTopDown(resultArr, items, capacity, currIndex + 1);
		
		resultArr[currIndex][capacity] = Math.max(profit1, profit2);
		return resultArr[currIndex][capacity];
	}
	
	public static int zeroOneKnapsackProblemUsingBottomUpApproach(List<Item> items, int capacity) {
		int[][] resultArr = new int[items.size()][capacity + 1];
		return calculateMaxProfitBottomUp(resultArr, items, capacity);
	}
	
	private static int calculateMaxProfitBottomUp(int[][] resultArr, List<Item> items, int capacity) {
		if (capacity <= 0 || 0 == items.size()) {
			return 0;
		}
		
		for (int i = items.size() - 2; i >= 0; i--) {
			for (int j = 1; j <= capacity; j++) {
				int profit1 = 0;
				int profit2 = 0;
				if (items.get(i).getWeight() <= j) {
					profit1 = items.get(i).getValue() + resultArr[i + 1][j - items.get(i).getWeight()];
				}
				profit2 = resultArr[i + 1][j];
				
				resultArr[i][j] = Math.max(profit1, profit2);
			}
		}
		return resultArr[0][capacity];
	}
	
	public static int findLongestCommonSubsequenceUsingTopDownApproach(String s1, String s2) {
		Integer[][] resultArr = new Integer[s1.length() + 1][s2.length() + 1];
		return longestSubsequenceRecursiveTopDown(resultArr, s1, s2, 0, 0);
	}
	
	private static int longestSubsequenceRecursiveTopDown(Integer[][] resultArr, String s1, String s2, int i1, int i2) {
		if (i1 == s1.length() || i2 == s2.length()) {
			return 0;
		}
		
		int c3 = 0;
		
		if (null == resultArr[i1][i2]) {
			if (s1.charAt(i1) == s2.charAt(i2)) {
				c3 = 1 + longestSubsequenceRecursiveTopDown(resultArr, s1, s2, i1 + 1, i2 + 1);
			}
			int c1 = longestSubsequenceRecursiveTopDown(resultArr, s1, s2, i1, i2 + 1);
			int c2 = longestSubsequenceRecursiveTopDown(resultArr, s1, s2, i1 + 1, i2);
			
			resultArr[i1][i2] = calculateMax(c1, c2, c3);
		}
		return resultArr[i1][i2];
	}
	
	private static int calculateMax(int a, int b, int c) {
		int max = a;
		
		if (b > a) {
			max = b;
		}
		
		return c > max ? c : max;
	}
	
	public static int findLongestCommonSubsequenceUsingBottomUpApproach(String s1, String s2) {
		int[][] resultArr = new int[s1.length() + 1][s2.length() + 1];
		return longestSubsequenceRecursiveBottomUp(resultArr, s1, s2);
	}
	
	private static int longestSubsequenceRecursiveBottomUp(int[][] resultArr, String s1, String s2) {
		for (int i = s1.length(); i >= 1; i--) {
			for (int j = s2.length(); j >= 1; j--) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					resultArr[i - 1][j - 1] = calculateMax(1 + resultArr[i][j], resultArr[i][j - 1], resultArr[i - 1][j]);
				} else {
					resultArr[i - 1][j - 1] = Math.max(resultArr[i][j - 1], resultArr[i - 1][j]);
				}
			}
		}
		return resultArr[0][0];
	}
}
