package com.ds.impl;

import java.lang.reflect.Array;

import com.ds.exception.QueueNotInitializedException;
import com.ds.exception.QueueOverflowException;

public class MyArrayQueue<E> {
	private E[] arr;
	private int startOfQueue = -1;
	private int topOfQueue = -1;
	private int size = 0;
	
	public MyArrayQueue(Class<E> clazz, int size) {
        // Use Array native method to create array
        // of a type only known at run time
        @SuppressWarnings("unchecked")
        final E[] arr = (E[]) Array.newInstance(clazz, size);
        this.arr = arr;
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
	
	public boolean add(E element) {
		if (null == arr) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else if (isFull()) {
			throw new QueueOverflowException("Queue is full");
		} else {
			if (0 == this.size) {
				this.topOfQueue = 0;
				this.startOfQueue = 0;
				arr[this.startOfQueue] = element;
			} else if (arr.length - 1 == topOfQueue) {
				this.topOfQueue = 0;
				arr[this.topOfQueue] = element;
			} else {
				this.topOfQueue++;
				arr[this.topOfQueue] = element;
			}
			this.size++;
		}
		return true;
	}
	
	public E poll() {
		if (null == arr) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else if (isEmpty()) {
			return null;
		} else {
			E temp = arr[this.startOfQueue];
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
	
	public E peek() {
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
