package com.ds.impl;

import com.ds.exception.LinkedListNotInitializedException;

public class MyLinkedList<E> {
	
	private MyLinkedList<E> head;
	private MyLinkedList<E> tail;
	private E data;
	private int size;
	private MyLinkedList<E> next;
	private MyLinkedList<E> prev;
	private int height;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + height;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((prev == null) ? 0 : prev.hashCode());
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
		if (height != other.height)
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (prev == null) {
			if (other.prev != null)
				return false;
		} else if (!prev.equals(other.prev))
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

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[");
		MyLinkedList<E> tempNode = head;
		
		if (head.equals(tail)) {
			return "[" + head.getData() + "]";
		}
		
		while (!tail.equals(tempNode)) {
			sb.append(tempNode.getData()).append(" ");
			tempNode = tempNode.getNext();
		}
		sb.append(tail.getData());
		
		return sb.append("]").toString();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public MyLinkedList<E> getPrev() {
		return prev;
	}

	public void setPrev(MyLinkedList<E> prev) {
		this.prev = prev;
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
		return 0 == getSize();
	}
	
	public boolean add(E element) {
		if (isEmpty()) {
			addFirstNode(element);
		} else {
			addElementAtEnd(element);
		}
		return true;
	}
	
	private void addFirstNode(E element) {
		head = new MyLinkedList<>();
		head.setData(element);
		head.setPrev(head);
		head.setNext(head);
		tail = head;
		setSize(this.size + 1);
	}
	
	private void addElementAtEnd(E element) {
		MyLinkedList<E> node = new MyLinkedList<>();
		node.setData(element);
		
		node.setNext(head);
		node.setPrev(tail);
		tail.setNext(node);
		tail = node;
		head.setPrev(node);
		
		setSize(this.size + 1);
	}

	public boolean add(E element, int position) {
		if (isEmpty()) {
			addFirstNode(element);
		} else if (position <= 0) {
			MyLinkedList<E> node = new MyLinkedList<>();
			node.setData(element);
			
			if (1 == getSize()) {
				MyLinkedList<E> tempNode = head;
				head = node;
				head.setNext(tempNode);
				head.setPrev(tempNode);
				tempNode.setNext(head);
				tempNode.setPrev(head);
				tail = tempNode;
			} else {
				MyLinkedList<E> tempNode = head;
				tempNode.setNext(head.getNext());
				tempNode.setPrev(node);
				head = node;
				head.setNext(tempNode);
				head.setPrev(tail);
				tail.setNext(head);
			}
			setSize(this.size + 1);
		} else if (position >= getSize()) {
			addElementAtEnd(element);
		} else {
			MyLinkedList<E> tempNode = head;
			int count = 0;
			MyLinkedList<E> node = new MyLinkedList<>();
			node.setData(element);
			
			while (count < position - 1) {
				tempNode = tempNode.next;
				count++;
			}
			
			node.setNext(tempNode.getNext());
			node.setPrev(tempNode);
			tempNode.getNext().setPrev(node);
			tempNode.setNext(node);
			setSize(this.size + 1);
		}
		return true;
	}
	
	public E get(int position) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		
		if (1 == getSize() || position <= 0) {
			return head.getData();
		} else if (position >= getSize() - 1) {
			return tail.getData();
		}
		
		MyLinkedList<E> tempNode = head;
		int count = 0;
		
		while (!tail.equals(tempNode)) {
			if (count == position) {
				return tempNode.getData();
			}
			tempNode = tempNode.getNext();
			count++;
		}
		return null;
	}
	
	public E get(E element) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		
		if (head.getData().equals(element)) {
			return head.getData();
		}
		
		if (tail.getData().equals(element)) {
			return tail.getData();
		}
			
		MyLinkedList<E> tempNode = head;
		
		while (!tail.equals(tempNode)) {
			if (tempNode.getData().equals(element)) {
				return tempNode.getData();
			}
			tempNode = tempNode.getNext();
		}
		return null;
	}
	
	public boolean contains(E element) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		
		if (head.getData().equals(element) || tail.getData().equals(element)) {
			return true;
		}
			
		MyLinkedList<E> tempNode = head;
		
		while (!tail.equals(tempNode)) {
			if (tempNode.getData().equals(element)) {
				return true;
			}
			tempNode = tempNode.getNext();
		}
		return false;
	}
	
	public boolean remove(E element) {
		
		if (null == element) {
			return false;
		}
		
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		}
		
		if (1 == getSize() && element.equals(head.getData())) {
			removeFirstElementAndDeleteList();
			return true;
		} else if (element.equals(tail.getData())) {
			removeLastElement();
			return true;
		}
		
		MyLinkedList<E> tempNode = head;
		MyLinkedList<E> prev = head;
		int count = 0;
		
		while (!tail.equals(tempNode)) {
			if (element.equals(tempNode.getData())) {
				if (0 == count) {
					removeElementAtZerothPosition();
				} else {
					tempNode.getNext().setPrev(prev);
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
	
	private void removeFirstElementAndDeleteList() {
		head = null;
		tail = null;
		setSize(0);
	}
	
	private void removeElementAtZerothPosition() {
		head = head.getNext();
		tail.setNext(head);
		head.setPrev(tail);
		setSize(getSize() - 1);
	}
	
	private void removeLastElement() {
		tail = tail.getPrev();
		head.setPrev(tail);
		tail.setNext(head);
		setSize(getSize() - 1);
	}
	
	public boolean removeElementAt(int position) {
		if (isEmpty()) {
			throw new LinkedListNotInitializedException("Linked list is either deleted or not initialized");
		} else if (1 == getSize()) {
			removeFirstElementAndDeleteList();
			return true;
		} else if (position <= 0) {
			removeElementAtZerothPosition();
			return true;
		} else {
			MyLinkedList<E> tempNode = head;
			int count = 0;
			
			while (count < position - 1) {
				tempNode = tempNode.next;
				count++;
			}
			
			if (position >= getSize() - 1) {
				removeLastElement();
				return true;
			} else {
				tempNode.setNext(tempNode.getNext().getNext());
				tempNode.getNext().setPrev(tempNode);
			}
		}
		setSize(getSize() - 1);
		return true;
	}
	
	public boolean deleteList() {
		
		if (isEmpty()) {
			return true;
		}
		
		if (1 == getSize()) {
			head = null;
			tail = null;
			setSize(0);
			return true;
		}
		
		MyLinkedList<E> tempNode = head;
		tail.setNext(null);
		
		while (null != tempNode) {
			tempNode.setPrev(null);
			tempNode = tempNode.getNext();
		}
		head = null;
		tail = null;
		setSize(0);
		return true;
	}
 	
}
