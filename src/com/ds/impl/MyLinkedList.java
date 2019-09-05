package com.ds.impl;

import com.ds.exception.LinkedListNotInitializedException;

public class MyLinkedList<E> {
	
	private MyLinkedList<E> head;
	private MyLinkedList<E> tail;
	private E data;
	private int size;
	private MyLinkedList<E> next;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + size;
		result = prime * result + ((tail == null) ? 0 : tail.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyLinkedList<E> other = (MyLinkedList<E>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (size != other.size)
			return false;
		if (tail == null) {
			if (other.tail != null)
				return false;
		} else if (!tail.equals(other.tail))
			return false;
		return true;
	}
	
	public MyLinkedList<E> getHead() {
		return head;
	}

	public void setHead(MyLinkedList<E> head) {
		this.head = head;
	}

	public MyLinkedList<E> getNext() {
		return next;
	}

	public void setNext(MyLinkedList<E> next) {
		this.next = next;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean isEmpty() {
		if (0 == getSize()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean add(E element) {
		if (isEmpty()) {
			head = new MyLinkedList<>();
			head.setData(element);
			tail = head;
			setSize(this.size + 1);
		} else {
			addElementAtEnd(element);
		}
		return true;
	}
	
	private void addElementAtEnd(E element) {
		MyLinkedList<E> node = new MyLinkedList<>();
		MyLinkedList<E> tempNode = head;
		node.setData(element);
		
		while (null != tempNode.getNext()) {
			tempNode = tempNode.getNext();
		}
		
		tempNode.setNext(node);
		tail = node;
		setSize(this.size + 1);
	}

	public boolean add(E element, int position) {
		if (isEmpty()) {
			head = new MyLinkedList<>();
			head.setData(element);
			tail = head;
			setSize(this.size + 1);
		} else if (position <= 0) {
			MyLinkedList<E> node = new MyLinkedList<>();
			node.setData(element);
			
			if (1 == getSize()) {
				MyLinkedList<E> tempNode = head;
				head = node;
				head.setNext(tempNode);
				tail = head.getNext();
			} else {
				MyLinkedList<E> tempNode = head;
				head = node;
				head.setNext(tempNode);
			}
			setSize(this.size + 1);
		} else if (position >= getSize()) {
			addElementAtEnd(element);
		} else {
			MyLinkedList<E> tempNode = head;
			int count = 0;
			MyLinkedList<E> node = new MyLinkedList<>();
			node.setData(element);
			
			while (count != position - 1) {
				tempNode = tempNode.next;
				count++;
			}
			
			node.setNext(tempNode.getNext());
			tempNode.next = node;
			setSize(this.size + 1);
		}
		return true;
	}
	
	public E get(int position) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		MyLinkedList<E> tempNode = head;
		int count = 0;
		
		while (null != tempNode) {
			if (count == position) {
				return tempNode.getData();
			}
			tempNode = tempNode.getNext();
			count++;
		}
		throw new IndexOutOfBoundsException("Index out of range");
	}
	
	public boolean contains(E element) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		MyLinkedList<E> tempNode = head;
		
		while (null != tempNode) {
			if (tempNode.getData().equals(element)) {
				return true;
			}
			tempNode = tempNode.getNext();
		}
		return false;
	}
	
	public boolean remove(E element) {
		
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		MyLinkedList<E> tempNode = head;
		MyLinkedList<E> prev = head;
		int count = 0;
		
		while (null != tempNode) {
			if (tempNode.getData().equals(element)) {
				if (1 == getSize()) {
					head = null;
					tail = null;
					setSize(0);
				} else if (0 == count) {
					head = head.getNext();
					setSize(getSize() - 1);
				} else if (getSize() - 1 == count) {
					prev.setNext(null);
					tail = prev;
					setSize(getSize() - 1);
				} else {
					prev.setNext(tempNode.getNext());
					setSize(getSize() - 1);
				}
				return true;
			}
			
			prev = tempNode;
			tempNode = tempNode.getNext();
			count++;
		}
		
		return false;
	}
	
	public boolean removeElementAt(int position) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		} else if (position <= 0) {
			head = head.getNext();
		} else {
			MyLinkedList<E> tempNode = head;
			int count = 0;
			
			while (count != position - 1) {
				tempNode = tempNode.next;
				count++;
			}
			
			if (position >= getSize() - 1) {
				tempNode.setNext(null);
				tail = tempNode;
			} else {
				tempNode.setNext(tempNode.getNext().getNext());
			}
		}
		setSize(getSize() - 1);
		return true;
	}
	
	public boolean deleteList() {
		head = null;
		tail = null;
		setSize(0);
		return true;
	}
 	
}
