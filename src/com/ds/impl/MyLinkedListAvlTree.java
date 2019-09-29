package com.ds.impl;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedListAvlTree {
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
		} else {
			MyLinkedList<Integer> newNode = addElement(root, number);
			MyLinkedList<Integer> disbalancedNode = checkTreeBalanced(newNode.getHead());
			if (null != disbalancedNode) {
				rotate(disbalancedNode);
			}
			setSize(this.size + 1);
		}
		return true;
	}
	
	public boolean contains(Integer i) {
		if (null == i) {
			return false;
		}
		return containsRecursive(root, i);
	}
	
	public boolean remove(Integer i) {
		if (null == i || isEmpty()) {
			return false;
		}
		MyLinkedList<Integer> removedNode = removeElement(root, i);
		
		MyLinkedList<Integer> disbalancedNode = checkTreeBalanced(removedNode.getHead());
		if (null != disbalancedNode) {
			rotate(disbalancedNode);
		}
		return true;
	}
	
	private MyLinkedList<Integer> removeElement(MyLinkedList<Integer> node, Integer i) {
		if (null == node) {
			return null;
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
				MyLinkedList<Integer> successor = getSuccessor(node);
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
			return node;
		} else if (i < node.getData()) {
			return removeElement(node.getPrev(), i);
		} else {
			return removeElement(node.getNext(), i);
		}
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
	
	private MyLinkedList<Integer> addElement(MyLinkedList<Integer> node, Integer number) {
		  if (number <= node.getData()) {
			  if (null == node.getPrev()) {
				  MyLinkedList<Integer> newNode = new MyLinkedList<>();
				  newNode.setData(number);
				  newNode.setHead(node);
				  node.setPrev(newNode);
				  if (null == node.getPrev() || null == node.getNext()) {
					  updateHeight(node);
				  }
				  return newNode;
			  } else {
				  return addElement(node.getPrev(), number);
			  }
		  } else {
			  if (null == node.getNext()) {
				  MyLinkedList<Integer> newNode = new MyLinkedList<>();
				  newNode.setData(number);
				  newNode.setHead(node);
				  node.setNext(newNode);
				  if (null == node.getPrev() || null == node.getNext()) {
					  updateHeight(node);
				  }
				  return newNode;
			  } else {
				  return addElement(node.getNext(), number);
			  }
		  }
	}
	
	private void updateHeight(MyLinkedList<Integer> node) {
		if (null == node) {
			return;
		} else {
			node.setHeight(node.getHeight() + 1);
			updateHeight(node.getHead());
		}
	}
	
	private MyLinkedList<Integer> checkTreeBalanced(MyLinkedList<Integer> node) {
		if (null == node) {
			return null;
		} else {
			int balance = calculateBalance(node);
			
			if (balance > 1 || balance < -1) {
				return node;
			} else {
				return checkTreeBalanced(node.getHead());
			}
		}
	}
	
	private int calculateBalance(MyLinkedList<Integer> node) {
		int leftChildHeight = -1;
		int rightChildHeight = -1;
		
		if (null == node) {
			return -1;
		}
		
		if (null != node.getPrev()) {
			leftChildHeight = node.getPrev().getHeight();
		}
		
		if (null != node.getNext()) {
			rightChildHeight = node.getNext().getHeight();
		}
		
		return leftChildHeight - rightChildHeight;
	}
	
	private void rotate(MyLinkedList<Integer> node) {
		int balance = calculateBalance(node);
		
		if (balance > 1) {
			int leftGrandChildHeight = getHeight(node.getPrev().getPrev());
			int rightGrandChildHeight = getHeight(node.getPrev().getNext());
			
			if (leftGrandChildHeight >= rightGrandChildHeight) {
				// Left left condition
				rightRotate(node);
			} else {
				// Left right condition
				leftRotate(node.getPrev());
				rightRotate(node);
			}
		} else if (balance < -1) {
			int leftGrandChildHeight = getHeight(node.getNext().getPrev());
			int rightGrandChildHeight = getHeight(node.getNext().getNext());
			
			if (rightGrandChildHeight >= leftGrandChildHeight) {
				// Right right condition
				leftRotate(node);
			} else {
				//Right left condition
				rightRotate(node.getNext());
				leftRotate(node);
			}
		}
	}
	
	private void rightRotate(MyLinkedList<Integer> node) {
		if (root.equals(node)) {
			root = node.getPrev();
			node.setPrev(node.getPrev().getNext());
			root.setNext(node);
			if (null != node.getPrev()) {
				node.getPrev().setHead(node);
			}
			node.setHead(root);
			root.setHead(null);
			node.setHeight(calculateNewHeightUsingChildeNodes(node));
			root.setHeight(calculateNewHeightUsingChildeNodes(root));
		} else {
			node.getPrev().setHead(node.getHead());
			node.getPrev().setNext(node);
			if (node.equals(node.getHead().getPrev())) {
				node.getHead().setPrev(node.getPrev());
			} else {
				node.getHead().setNext(node.getPrev());
			}
			node.setHead(node.getPrev());
			node.setPrev(null);
			node.setNext(null);
			node.setHeight(0);
			node.getHead().setHeight(calculateNewHeightUsingChildeNodes(node.getHead()));
			calculateNewHeightOfParents(node.getHead().getHead());
		}
	}
	
	private void leftRotate(MyLinkedList<Integer> node) {
		if (root.equals(node)) {
			root = node.getNext();
			node.setNext(node.getNext().getPrev());
			root.setPrev(node);
			if (null != node.getNext()) {
				node.getNext().setHead(node);
			}
			node.setHead(root);
			root.setHead(null);
			node.setHeight(calculateNewHeightUsingChildeNodes(node));
			root.setHeight(calculateNewHeightUsingChildeNodes(root));
		} else {
			node.getNext().setHead(node.getHead());
			node.getNext().setPrev(node);
			if (node.equals(node.getHead().getPrev())) {
				node.getHead().setPrev(node.getNext());
			} else {
				node.getHead().setNext(node.getNext());
			}
			node.setHead(node.getNext());
			node.setPrev(null);
			node.setNext(null);
			node.setHeight(0);
			node.getHead().setHeight(calculateNewHeightUsingChildeNodes(node.getHead()));
			calculateNewHeightOfParents(node.getHead().getHead());
		}
	}
	
	private int getHeight(MyLinkedList<Integer> node) {
		if (null != node) {
			return node.getHeight();
		} else {
			return -1;
		}
	}
	
	private int calculateNewHeightUsingChildeNodes(MyLinkedList<Integer> node) {
		int leftChildHeight = 0;
		int rightChildHeight = 0;
		
		if (null == node.getPrev() && null == node.getNext()) {
			return 0;
		}
		
		if (null != node.getPrev()) {
			leftChildHeight = node.getPrev().getHeight();
		}
		
		if (null != node.getNext()) {
			rightChildHeight = node.getNext().getHeight();
		}
		
		if (leftChildHeight >= rightChildHeight) {
			return leftChildHeight + 1;
		} else {
			return rightChildHeight + 1;
		}
	}
	
	private void calculateNewHeightOfParents(MyLinkedList<Integer> node) {
		if (null == node) {
			return;
		} else {
			int leftChildHeight = 0;
			int rightChildHeight = 0;
			
			if (null != node.getPrev()) {
				leftChildHeight = node.getPrev().getHeight();
			}
			
			if (null != node.getNext()) {
				rightChildHeight = node.getNext().getHeight();
			}
			
			if (leftChildHeight >= rightChildHeight) {
				node.setHeight(leftChildHeight + 1);
			} else {
				node.setHeight(rightChildHeight + 1);
			}
			calculateNewHeightOfParents(node.getHead());
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
		List<MyLinkedList<Integer>> nodeList = new ArrayList<>();
		getInOrderTraversalList(root, nodeList);
		for (int i = 0; i < nodeList.size(); i++) {
			if (node.equals(nodeList.get(i))) {
				return nodeList.get(i + 1);
			}
		}
		return null;
	}
	
	private void getInOrderTraversalList(MyLinkedList<Integer> node, List<MyLinkedList<Integer>> nodeList) {
		if (null == node) {
			return;
		} else {
			getInOrderTraversalList(node.getPrev(), nodeList);
			nodeList.add(node);
			getInOrderTraversalList(node.getNext(), nodeList);
		}
	}
}
