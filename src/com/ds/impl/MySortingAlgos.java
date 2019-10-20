package com.ds.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySortingAlgos {
	
	public static void bubbleSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j + 1] < arr[j]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}
	
	public static void selectionSort(int[] arr) {
		for (int i = -1; i < arr.length - 2; i++) {
			int min = arr[i + 1];
			int minIndex = i + 1;
			for (int j = i + 1;j < arr.length; j++) {
				if (arr[j] < min) {
					min = arr[j];
					minIndex = j;
				}
			}
			int temp = arr[i + 1];
			arr[i + 1] = min;
			arr[minIndex] = temp;
		}
	}
	
	public static void insertionSort(int[] arr) {
		int i = 0;
		
		while (i < arr.length) {
			int firstElem = arr[i];
			for (int j = i - 1; j >= 0; j--) {
				if (firstElem < arr[j]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
			i++;
		}
	}
	
	public static void bucketSort(int[] arr) {
		int noOfBuckets = (int) Math.floor(Math.sqrt(Double.parseDouble(String.valueOf(arr.length))));
		List<int[]> arrList = new ArrayList<>();
		
		for (int i = 0; i < noOfBuckets; i++) {
			int[] intArr = new int[0];
			arrList.add(intArr);
		}
		
		int max = 0;
		
		for (int i : arr) {
			if (i > max) {
				max = i;
			}
		}
		
		for (int i : arr) {
			int bucketIndex = (int) Math.ceil((i * noOfBuckets) / max);
			if (max == i) {
				bucketIndex -= 1;
			}
			int[] temp = arrList.get(bucketIndex);
			temp = Arrays.copyOf(temp, temp.length + 1);
			arrList.remove(bucketIndex);
			temp[temp.length - 1] = i;
			arrList.add(bucketIndex, temp);
		}
		
		int count = 0;
		int initialIndex = 0;
		for (int[] tempArr: arrList) {
			quickSort(tempArr);
			count += tempArr.length;
			for (int i = initialIndex, j = 0; i < count; i++, j++) {
				arr[i] = tempArr[j];
			}
			initialIndex = count;
		}
	}
	
	public static void mergeSort(int[] arr) {
		mergeRecursive(arr, 0, arr.length - 1);
	}
	
	public static void quickSort(int[] arr) {
		quickSortRecursive(arr, 0, arr.length - 1);
	}
	
	public static void heapSort(int[] arr) {
		MyArrayBinaryMinHeapTree heap = new MyArrayBinaryMinHeapTree(arr.length);
		
		for (int i : arr) {
			heap.add(i);
		}
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = heap.extractMin();
		}
	}
	
	private static void quickSortRecursive(int[] arr, int initialIndex, int lastIndex) {
		if (lastIndex > initialIndex) {
			int pivotIndex = calculatePivotIndex(arr, initialIndex, lastIndex);
			quickSortRecursive(arr, initialIndex, pivotIndex - 1);
			quickSortRecursive(arr, pivotIndex + 1, lastIndex);
		}
	}
	
	private static int calculatePivotIndex(int[] arr, int initialIndex, int lastIndex) {
		int i = initialIndex - 1;
		int pivot = arr[lastIndex];
		int pivotIndex = lastIndex;
		
		for (int j = initialIndex; j <= lastIndex; j++) {
			if (arr[j] <= pivot) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				
				if (lastIndex == j) {
					pivotIndex = i;
				}
			}
		}
		return pivotIndex;
	}
	
	private static void mergeRecursive(int[] arr, int initialIndex, int lastIndex) {
		if (lastIndex > initialIndex) {
			int middleIndex = (initialIndex + lastIndex) / 2;
			mergeRecursive(arr, initialIndex, middleIndex);
			mergeRecursive(arr, middleIndex + 1, lastIndex);
			mergeArrays(arr, initialIndex, middleIndex, middleIndex + 1, lastIndex);
		}
	}
	
	private static void mergeArrays(int[] arr, int firstArrStartIndex, int firstArrLastIndex, int secondArrStartIndex, int secondArrLastIndex) {
		int[] firstArr = Arrays.copyOfRange(arr, firstArrStartIndex, firstArrLastIndex + 1);
		int[] secondArr = Arrays.copyOfRange(arr, secondArrStartIndex, secondArrLastIndex + 1);
		
		int i = 0;
		int j = 0;
		
		for (int k = firstArrStartIndex; k <= secondArrLastIndex; k++) {
			if (i < firstArr.length) {
				if (j < secondArr.length) {
					if (firstArr[i] < secondArr[j]) {
						arr[k] = firstArr[i];
						i++;
					} else {
						arr[k] = secondArr[j];
						j++;
					}
				} else {
					arr[k] = firstArr[i];
					i++;
				}
			} else if (j < secondArr.length) {
				arr[k] = secondArr[j];
				j++;
			}
		}
	}
}
