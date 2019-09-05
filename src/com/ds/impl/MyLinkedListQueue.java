package com.ds.impl;

import com.ds.exception.QueueNotInitializedException;

public class MyLinkedListQueue<E> {
	private MyLinkedList<E> linkedList;
	
	public MyLinkedListQueue() {
		this.linkedList = new MyLinkedList<>();
	}
	
	public boolean isEmpty() {
		if (null == linkedList || linkedList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean add(E element) {
		if (null == linkedList) {
			this.linkedList = new MyLinkedList<>();
		}
		linkedList.add(element);
		return true;
	}
	
	public E poll() {
		if (isEmpty()) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else {
			E element = linkedList.get(0);
			linkedList.removeElementAt(0);
			return element;
		}
	}
	
	public E peek() {
		if (isEmpty()) {
			throw new QueueNotInitializedException("Queue is deleted");
		} else {
			return linkedList.get(0);
		}
	}
	
	public boolean delete() {
		linkedList = null;
		return true;
	}
}
