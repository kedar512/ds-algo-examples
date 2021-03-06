package com.ds.impl;

import com.ds.exception.StackNotInitializedException;

public class MyLinkedListStack<E> {
	private MyLinkedList<E> linkedList;
	
	public MyLinkedListStack() {
		this.linkedList = new MyLinkedList<>();
	}
	
	public boolean isEmpty() {
		if (null == linkedList || linkedList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean push(E element) {
		if (null == linkedList) {
			this.linkedList = new MyLinkedList<>();
		}
		linkedList.add(element);
		return true;
	}
	
	public E pop() {
		if (isEmpty()) {
			throw new StackNotInitializedException("Stack is deleted");
		} else {
			E element = linkedList.get(linkedList.getSize() - 1);
			linkedList.remove(element);
			return element;
		}
	}
	
	public E peek() {
		if (isEmpty()) {
			throw new StackNotInitializedException("Stack is deleted");
		} else {
			return linkedList.get(linkedList.getSize() - 1);
		}
	}
	
	public boolean delete() {
		linkedList = null;
		return true;
	}
}
