package com.ds.impl;

public class MyLinkedList<E> {
	
	private Node head;
	private Node tail;
	private int size;
	private Node node;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean add(E e) {
		if (null == head) {
			head = new Node(e);
			tail = head;
			setSize(this.size + 1);
		} else {
			Node newNode = new Node(e);
			node = head;
			while(null != node.getNext()) {
				if (null == node.getNext()) {
					node.setNext(newNode);
					tail = newNode;
					setSize(this.size + 1);
					break;
				}
			}
		}
		return true;
	}
 	
}
