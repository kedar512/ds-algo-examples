package com.ds.impl;

import com.ds.exception.QueueNotInitializedException;
import com.ds.exception.QueueOverflowException;

public class MyArrayQueue {
	private Object[] arr;
	private int startOfQueue = -1;
	private int topOfQueue = -1;
	private int size = 0;
	
	public MyArrayQueue(int size) {
		arr = new Object[size];
	}
	
	public boolean isEmpty() {
		if (0 == size) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFull() {
		if (size == arr.length) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean add(Object o) {
		if (null == arr) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else if (isFull()) {
			throw new QueueOverflowException("Queue is full");
		} else {
			if (0 == this.size) {
				this.topOfQueue = 0;
				this.startOfQueue = 0;
				arr[this.startOfQueue] = o;
			} else if (arr.length - 1 == topOfQueue) {
				this.topOfQueue = 0;
				arr[this.topOfQueue] = o;
			} else {
				this.topOfQueue++;
				arr[this.topOfQueue] = o;
			}
			this.size++;
		}
		return true;
	}
	
	public Object poll() {
		if (null == arr) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else if (isEmpty()) {
			return null;
		} else {
			Object temp = arr[this.startOfQueue];
			arr[this.startOfQueue] = null;
			
			if (arr.length - 1 == this.startOfQueue) {
				this.startOfQueue = 0;
			} else {
				this.startOfQueue++;
			}
			this.size--;
			return temp;
		}
	}
	
	public Object peek() {
		if (null == arr) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else if (isEmpty()) {
			return null;
		} else {
			return arr[this.startOfQueue];
		}
	}
	
	public boolean delete() {
		if (null == arr) {
			throw new QueueNotInitializedException("Queue is already deleted");
		} else {
			arr = null;
			return true;
		}
	}
}
