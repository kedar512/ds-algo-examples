package com.ds.impl;

import java.util.List;

public class MyLinkedListBinarySearchTree {
	private MyLinkedList<Integer> root;
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
	
	public boolean add(Integer number) {
		if (isEmpty()) {
			root = new MyLinkedList<>();
			root.setData(number);
			setSize(this.size + 1);
			return true;
		} else {
			boolean isAdded = addElement(root, number);
			setSize(this.size + 1);
			return isAdded;
		}
	}
	
	private boolean addElement(MyLinkedList<Integer> node, Integer number) {
		  if (number <= node.getData()) {
			  if (null == node.getPrev()) {
				  MyLinkedList<Integer> newNode = new MyLinkedList<>();
				  newNode.setData(number);
				  newNode.setHead(node);
				  node.setPrev(newNode);
				  return true;
			  } else {
				  return addElement(node.getPrev(), number);
			  }
		  } else {
			  if (null == node.getNext()) {
				  MyLinkedList<Integer> newNode = new MyLinkedList<>();
				  newNode.setData(number);
				  newNode.setHead(node);
				  node.setNext(newNode);
				  return true;
			  } else {
				  return addElement(node.getNext(), number);
			  }
		  }
	}
	
	public boolean contains(Integer i) {
		if (null == i) {
			return false;
		}
		return containsRecursive(root, i);
	}
	
	private boolean containsRecursive(MyLinkedList<Integer> node, Integer i) {
		if (null == node) {
			return false;
		} else if (i.equals(node.getData())) {
			return true;
		} else if (i < node.getData()) {
			return containsRecursive(node.getPrev(), i);
		} else {
			return containsRecursive(node.getNext(), i);
		}
	}
	
	public boolean remove(Integer i) {
		if (null == i || isEmpty()) {
			return false;
		}
		return removeElement(root, i);
	}
	
	private boolean removeElement(MyLinkedList<Integer> node, Integer i) {
		if (null == node) {
			return false;
		} else if (i.equals(node.getData())) {
			if (null == node.getPrev() && null == node.getNext()) {
				if (null != node.getHead()) {
					MyLinkedList<Integer> head = node.getHead();
					if (node.equals(head.getPrev())) {
						head.setPrev(null);
					} else {
						head.setNext(null);
					}
				} else {
					root = null;
				}
			} else if (null == node.getNext()) {
				if (null != node.getHead()) {
					MyLinkedList<Integer> parent = node.getHead();
					MyLinkedList<Integer> child = node.getPrev();
					connectParentToChild(node, parent, child);
				} else {
					root = root.getPrev();
					root.setHead(null);
				}
			} else if (null == node.getPrev()) {
				if (null != node.getHead()) {
					MyLinkedList<Integer> parent = node.getHead();
					MyLinkedList<Integer> child = node.getNext();
					connectParentToChild(node, parent, child);
				} else {
					root = root.getNext();
					root.setHead(null);
				}
			} else {
				MyLinkedList<Integer> successor = getSuccessor(node.getNext());
				MyLinkedList<Integer> successorHead = successor.getHead();
				MyLinkedList<Integer> nodeHead = node.getHead();

				if (null == successor.getNext() && null == successor.getPrev()) {
					if (successor.equals(successorHead.getPrev())) {
						successorHead.setPrev(null);
					} else {
						successorHead.setNext(null);
					}
				} else {
					MyLinkedList<Integer> successorChild = null;
					
					if (null != successor.getPrev()) {
						successorChild = successor.getPrev();
					} else {
						successorChild = successor.getNext();
					}
					connectParentToChild(successor, successorHead, successorChild);
				}
				
				if (null != node.getPrev()) {
					node.getPrev().setHead(successor);
					successor.setPrev(node.getPrev());
				}
				
				if (null != node.getNext()) {
					node.getNext().setHead(successor);
					successor.setNext(node.getNext());
				}
				successor.setHead(node.getHead());
				
				if (null != nodeHead) {
					if (node.equals(nodeHead.getPrev())) {
						nodeHead.setPrev(successor);
					} else {
						nodeHead.setNext(successor);
					}
				} else {
					root = successor;
					root.setHead(null);
				}
			}
			setSize(this.size - 1);
			return true;
		} else if (i < node.getData()) {
			return removeElement(node.getPrev(), i);
		} else {
			return removeElement(node.getNext(), i);
		}
	}
	
	private void connectParentToChild(MyLinkedList<Integer> node, MyLinkedList<Integer> parent, MyLinkedList<Integer> child) {
		if (node.equals(parent.getPrev())) {
			parent.setPrev(child);
			child.setHead(parent);
		} else {
			parent.setNext(child);
			child.setHead(parent);
		}
	}
	
	private MyLinkedList<Integer> getSuccessor(MyLinkedList<Integer> node) {
		if (null != node.getPrev()) {
			return getSuccessor(node.getPrev());
		} else {
			return node;
		}
	}
	
	public void getInOrderTraversalList(MyLinkedList<Integer> node, List<MyLinkedList<Integer>> nodeList) {
		if (null == node) {
			return;
		} else {
			getInOrderTraversalList(node.getPrev(), nodeList);
			nodeList.add(node);
			getInOrderTraversalList(node.getNext(), nodeList);
		}
	}
}
