package com.ds.impl;

import java.lang.reflect.Array;

import com.ds.exception.StackEmptyException;
import com.ds.exception.StackNotInitializedException;

public class MyArrayStack<E> {
	
	private E[] arr;
	private int topOfStack = -1;
	
	public MyArrayStack(Class<E> clazz, int size) {
        // Use Array native method to create array
        // of a type only known at run time
        @SuppressWarnings("unchecked")
        final E[] arr = (E[]) Array.newInstance(clazz, size);
        this.arr = arr;
    }
	
	public boolean isEmpty() {
		return -1 == topOfStack;
	}
	
	public boolean isFull() {
		return arr.length - 1 == topOfStack;		
	}
	
	public boolean push(E element) {
		if (null == arr) {
			throw new StackNotInitializedException("Stack is deleted");
		} else if (isFull()) {
			throw new StackOverflowError("Stack is full");
		} else {
			topOfStack++;
			arr[topOfStack] = element;
			return true;
		}
	}
	
	public E pop() {
		if (null == arr) {
			throw new StackNotInitializedException("Stack is deleted");
		} else if (isEmpty()) {
			throw new StackEmptyException("Stack is empty");
		} else {
			E temp = arr[topOfStack];
			arr[topOfStack] = null;
			topOfStack--;
			return temp;
		}
	}
	
	public E peek() {
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
