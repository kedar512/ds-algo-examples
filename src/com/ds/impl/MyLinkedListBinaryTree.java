package com.ds.impl;

public class MyLinkedListBinaryTree<E> {
	private MyLinkedList<E> root;
	private int size;
	
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
			root = new MyLinkedList<>();
			root.setData(element);
		} else {
			MyLinkedListQueue<MyLinkedList<E>> q = new MyLinkedListQueue<>();
			MyLinkedList<E> node = new MyLinkedList<>();
			node.setData(element);
			q.add(root);
			
			while (!q.isEmpty()) {
				if (null == q.peek().getPrev()) {
					MyLinkedList<E> temp = q.poll();
					temp.setPrev(node);
					node.setHead(temp);
					break;
				} else if (null == q.peek().getNext()) {
					MyLinkedList<E> temp = q.poll();
					temp.setNext(node);
					node.setHead(temp);
					break;
				} else {
					q.add(q.peek().getPrev());
					q.add(q.peek().getNext());
					q.poll();
				}
			}
		}
		setSize(this.size + 1);
		return true;
	}
	
	public boolean contains(E element) {
		
		if (null == element || isEmpty()) {
			return false;
		}
		
		MyLinkedListQueue<MyLinkedList<E>> q = new MyLinkedListQueue<>();
		q.add(root);
		
		while(!q.isEmpty()) {
			if (null != q.peek().getPrev()) {
				q.add(q.peek().getPrev());
			}
			
			if (null != q.peek().getNext()) {
				q.add(q.peek().getNext());
			}
			
			if (element.equals(q.poll().getData())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean remove(E element) {
		if (isEmpty()) {
			return false;
		}
		
		if (1 == getSize() && element.equals(root.getData())) {
			return delete();
		}
		
		MyLinkedListQueue<MyLinkedList<E>> q = new MyLinkedListQueue<>();
		q.add(root);
		
		while(!q.isEmpty()) {
			boolean isLeftNodePresent = false;
			boolean isRightNodePresent = false;
			
			if (null != q.peek().getPrev()) {
				q.add(q.peek().getPrev());
				isLeftNodePresent = true;
			}
			
			if (null != q.peek().getNext()) {
				isRightNodePresent = true;
				q.add(q.peek().getNext());
			}
			
			if (element.equals(q.peek().getData())) {
				MyLinkedList<E> nodeToRemove = q.poll();
				MyLinkedList<E> parentNode = nodeToRemove.getHead();
				
				if (isLeftNodePresent || isRightNodePresent) {
					MyLinkedList<E> deepestNode = getDeepestNode();
					deepestNode.setPrev(nodeToRemove.getPrev());
					deepestNode.setNext(nodeToRemove.getNext());
					deepestNode.setHead(nodeToRemove.getHead());
					
					if (root.equals(nodeToRemove)) {
						root = deepestNode;
						root.setHead(null);
						updateChildren(root);
					} else {
						if (null != parentNode.getPrev() && parentNode.getPrev().equals(nodeToRemove)) {
							parentNode.setPrev(deepestNode);
						} else {
							parentNode.setNext(deepestNode);
						}
						
						updateChildren(deepestNode);
					}
				} else {
					if (null != parentNode.getPrev() && parentNode.getPrev().equals(nodeToRemove)) {
						parentNode.setPrev(null);
					} else {
						parentNode.setNext(null);
					}
				}
				setSize(this.size - 1);
				return true;
			} else {
				q.poll();
			}
		}
		return false;
	}
	
	private void updateChildren(MyLinkedList<E> node) {
		if (null != node.getPrev()) {
			node.getPrev().setHead(node);
		}
		
		if (null != node.getNext()) {
			node.getNext().setHead(node);
		}
	}
	
	private MyLinkedList<E> getDeepestNode() {
		MyLinkedListQueue<MyLinkedList<E>> q = new MyLinkedListQueue<>();
		q.add(root);
		MyLinkedList<E> temp = root;
		MyLinkedList<E> parentNode = root;
		
		while(!q.isEmpty()) {
			if (null != q.peek().getPrev()) {
				q.add(q.peek().getPrev());
			}
			
			if (null != q.peek().getNext()) {
				q.add(q.peek().getNext());
			}
			
			temp = q.poll();
		}
		
		parentNode = temp.getHead();
		
		if (null != parentNode.getPrev() && parentNode.getPrev().equals(temp)) {
			parentNode.setPrev(null);
		} else {
			parentNode.setNext(null);
		}
		return temp;
	}
	
	public boolean delete() {
		root = null;
		setSize(0);
		return true;
	}
	
}
