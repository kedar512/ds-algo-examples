package com.ds.impl;

import com.ds.exception.StackEmptyException;
import com.ds.exception.StackNotInitializedException;

public class MyArrayStack {
	
	private Object[] arr;
	private int topOfStack = -1;
	
	public MyArrayStack(int size) {
		arr = new Object[size];
	}
	
	public boolean isEmpty() {
		if (-1 == topOfStack) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFull() {
		if (arr.length - 1 == topOfStack) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean push(Object obj) {
		if (null == arr) {
			throw new StackNotInitializedException("Stack is deleted");
		} else if (isFull()) {
			throw new StackOverflowError("Stack is full");
		} else {
			topOfStack++;
			arr[topOfStack] = obj;
			return true;
		}
	}
	
	public Object pop() {
		if (null == arr) {
			throw new StackNotInitializedException("Stack is deleted");
		} else if (isEmpty()) {
			throw new StackEmptyException("Stack is empty");
		} else {
			Object temp = arr[topOfStack];
			arr[topOfStack] = null;
			topOfStack--;
			return temp;
		}
	}
	
	public Object peek() {
		if (null == arr) {
			throw new StackNotInitializedException("Stack is deleted");
		} else if (isEmpty()) {
			throw new StackEmptyException("Stack is empty");
		} else {
			return arr[topOfStack];
		}
	}
	
	public boolean delete() {
		if (null == arr) {
			throw new StackNotInitializedException("Stack is already deleted");
		} else {
			arr = null;
			return true;
		}
	}
}
