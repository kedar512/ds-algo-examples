package com.ds.impl;

import com.ds.exception.EmptyTreeException;
import com.ds.exception.TreeOverflowException;

public class MyIndexedArrayBinaryMinHeapTree {
	private Vertex[] arr;
	private int lastUpdatedIndex = 0;
	private int size;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MyIndexedArrayBinaryMinHeapTree(int arrSize) {
        arr = new Vertex[arrSize + 1];
    }
	
	public boolean isEmpty() {
		return 0 == getSize();
	}
	
	public boolean isFull() {
		return arr.length - 1 == getSize();
	}
	
	public boolean add(Vertex element) {
		if (isFull()) {
			throw new TreeOverflowException("Tree is full");
		} else {
			lastUpdatedIndex++;
			element.setHeapIndex(lastUpdatedIndex);
			arr[lastUpdatedIndex] = element;
			setSize(this.size + 1);
			heapifyBottomToTop(lastUpdatedIndex);
			return true;
		}
	}
	
	public Vertex peekMin() {
		return arr[1];
	}
	
	public Vertex extractMin() {
		if(isEmpty()) {
			throw new EmptyTreeException("Tree is empty");
		}
		
		Vertex min = arr[1];
		arr[1] = arr[lastUpdatedIndex];
		arr[1].setHeapIndex(1);
		arr[lastUpdatedIndex] = null;
		setSize(this.size - 1);
		lastUpdatedIndex--;
		
		if (getSize() > 1) {
			heapifyTopToBottom(1);
		}
		return min;
	}
	
	public boolean delete(Vertex element) {
		if (size > 0) {
			if (element.equals(arr[1])) {
				extractMin();
				return true;
			}
			
			int i = element.getHeapIndex();
			if (element.equals(arr[i])) {
				arr[i] = arr[lastUpdatedIndex];
				arr[i].setHeapIndex(i);
				arr[lastUpdatedIndex] = null;
				setSize(this.size - 1);
				lastUpdatedIndex--;

				if (getSize() > 1) {
					heapifyTopToBottom(i);
				}
				return true;
			}
			 
			
		}
		return false;
	}
	
	private void heapifyBottomToTop(int nodeIndex) {
		
		if (1 == nodeIndex) {
			return;
		}
		
		int parentIndex = nodeIndex / 2;
		
		if (arr[parentIndex].getDistance() > arr[nodeIndex].getDistance()) {
			Vertex temp = arr[parentIndex];
			arr[parentIndex] = arr[nodeIndex];
			arr[parentIndex].setHeapIndex(parentIndex);
			arr[nodeIndex] = temp;
			arr[nodeIndex].setHeapIndex(nodeIndex);
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
			int smallerChildIndex = arr[leftChildIndex].getDistance() <= arr[rightChildIndex].getDistance() ? leftChildIndex : rightChildIndex;
			if (arr[smallerChildIndex].getDistance() < arr[nodeIndex].getDistance()) {
				Vertex temp = arr[smallerChildIndex];
				arr[smallerChildIndex] = arr[nodeIndex];
				arr[smallerChildIndex].setHeapIndex(smallerChildIndex);
				arr[nodeIndex] = temp;
				arr[nodeIndex].setHeapIndex(nodeIndex);
				heapifyTopToBottom(smallerChildIndex);
			}
		} else if (leftChildIndex <= lastUpdatedIndex) {
			if (arr[leftChildIndex].getDistance() < arr[nodeIndex].getDistance()) {
				if (234 == leftChildIndex || 234 == nodeIndex) {
					System.out.println("Test");
				}
				Vertex temp = arr[leftChildIndex];
				arr[leftChildIndex] = arr[nodeIndex];
				arr[leftChildIndex].setHeapIndex(leftChildIndex);
				arr[nodeIndex] = temp;
				arr[nodeIndex].setHeapIndex(nodeIndex);
				heapifyTopToBottom(leftChildIndex);
			}
		}
	}
}
