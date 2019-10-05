package com.ds.impl;

import com.ds.exception.EmptyTreeException;
import com.ds.exception.TreeOverflowException;

public class MyArrayBinaryMaxHeapTree {
	private int[] arr;
	private int lastUpdatedIndex = 0;
	private int size;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MyArrayBinaryMaxHeapTree(int arrSize) {
        arr = new int[arrSize + 1];
    }
	
	public boolean isEmpty() {
		return 0 == getSize();
	}
	
	public boolean isFull() {
		return arr.length - 1 == getSize();
	}
	
	public boolean add(int element) {
		if (isFull()) {
			throw new TreeOverflowException("Tree is full");
		} else {
			lastUpdatedIndex++;
			arr[lastUpdatedIndex] = element;
			setSize(this.size + 1);
			heapifyBottomToTop(lastUpdatedIndex);
			return true;
		}
	}
	
	public int peekMax() {
		return arr[1];
	}
	
	public int extractMax() {
		if(isEmpty()) {
			throw new EmptyTreeException("Tree is empty");
		}
		
		arr[1] = arr[lastUpdatedIndex];
		arr[lastUpdatedIndex] = 0;
		setSize(this.size - 1);
		lastUpdatedIndex--;
		
		if (getSize() > 1) {
			heapifyTopToBottom(1);
		}
		return arr[1];
	}
	
	private void heapifyBottomToTop(int nodeIndex) {
		
		if (1 == nodeIndex) {
			return;
		}
		
		int parentIndex = nodeIndex / 2;
		
		if (arr[parentIndex] < arr[nodeIndex]) {
			int temp = arr[parentIndex];
			arr[parentIndex] = arr[nodeIndex];
			arr[nodeIndex] = temp;
			heapifyBottomToTop(parentIndex);
		} else {
			return;
		}
	}
	
	private void heapifyTopToBottom(int nodeIndex) {
		
		if (nodeIndex == lastUpdatedIndex || 1 == getSize()) {
			return;
		}
		
		int leftChildIndex = 2 * nodeIndex;
		int rightChildIndex = (2 * nodeIndex) + 1;
		
		if (leftChildIndex <= lastUpdatedIndex && rightChildIndex <= lastUpdatedIndex) {
			int smallerChildIndex = arr[leftChildIndex] >= arr[rightChildIndex] ? leftChildIndex : rightChildIndex;
			int temp = arr[smallerChildIndex];
			arr[smallerChildIndex] = arr[nodeIndex];
			arr[nodeIndex] = temp;
			heapifyTopToBottom(smallerChildIndex);
		} else if (leftChildIndex <= lastUpdatedIndex) {
			if (arr[leftChildIndex] > arr[nodeIndex]) {
				int temp = arr[leftChildIndex];
				arr[leftChildIndex] = arr[nodeIndex];
				arr[nodeIndex] = temp;
				heapifyTopToBottom(leftChildIndex);
			}
		}
	}
}
