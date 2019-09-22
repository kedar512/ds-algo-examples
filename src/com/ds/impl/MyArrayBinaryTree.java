package com.ds.impl;

import java.lang.reflect.Array;

import com.ds.exception.TreeOverflowException;

public class MyArrayBinaryTree<E> {
	private E[] arr;
	private int lastUpdatedIndex = 0;
	
	public MyArrayBinaryTree(Class<E> clazz, int size) {
        // Use Array native method to create array
        // of a type only known at run time
        @SuppressWarnings("unchecked")
        final E[] arr = (E[]) Array.newInstance(clazz, size + 1);
        this.arr = arr;
    }
	
	public boolean isEmpty() {
		return 0 == lastUpdatedIndex;
	}
	
	public boolean isFull() {
		return arr.length - 1 == lastUpdatedIndex;
	}
	
	public boolean add(E element) {
		if (isFull()) {
			throw new TreeOverflowException("Tree is full");
		} else {
			lastUpdatedIndex++;
			arr[lastUpdatedIndex] = element;
			return true;
		}
	}
	
	public boolean contains(E element) {
		if(isEmpty() || null == element) {
			return false;
		}
		
		for(int i = 1; i <= lastUpdatedIndex; i++) {
			if (element.equals(arr[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean remove(E element) {
		if(isEmpty() || null == element) {
			return false;
		}
		
		for(int i = 1; i <= lastUpdatedIndex; i++) {
			if (element.equals(arr[i])) {
				if (i == lastUpdatedIndex) {
					arr[i] = null;
				} else {
					E e = arr[lastUpdatedIndex];
					arr[i] = e;
					arr[lastUpdatedIndex] = null;
				}
				lastUpdatedIndex--;
				return true;
			}
		}
		return false;
	}
}
