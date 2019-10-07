package com.ds.impl;

public class MyLinkedListTrie<E> {
	private TrieNode<Character> root;
	
	public boolean add(String s) {
		if (null == s || 0 == s.trim().length()) {
			return false;
		}
		char[] charArr = s.toCharArray();
		TrieNode<Character> node = null;
		TrieNode<Character> tempNode = root;
		TrieNode<Character> prev = null;
		boolean isCharAdded = false;
		for(int i = 0; i < charArr.length; i++) {
			node = new TrieNode<>();
			node.setData(charArr[i]);
			if (null == root) {
				isCharAdded = true;
				root = new TrieNode<>();
				MyLinkedList<TrieNode<Character>> charNodeList = new MyLinkedList<>();
				root.setCharNodeList(charNodeList);
				root.getCharNodeList().add(node);
				tempNode = root;
			} else if (null != tempNode) {
				if (null != tempNode.getCharNodeList()) {
					boolean isContain = tempNode.getCharNodeList().contains(node);
					
					if (isContain) {
						prev = tempNode;
						tempNode = getNextNode(tempNode, node);
						continue;
					} else {
						isCharAdded = true;
						if (i > 1) {
							TrieNode<Character> existingNode = tempNode.getCharNodeList()
									.get(tempNode.getCharNodeList().getSize() - 1);
							node.setHead(existingNode.getHead());
						}
						tempNode.getCharNodeList().add(node);
					}
				} else {
					isCharAdded = true;
					connectPrevNodeInSequence(charArr, i, prev, node);
					MyLinkedList<TrieNode<Character>> charNodeList = new MyLinkedList<>();
					tempNode.setCharNodeList(charNodeList);
					tempNode.getCharNodeList().add(node);
				}
			} else {
				isCharAdded = true;
				connectPrevNodeInSequence(charArr, i, prev, node);
				tempNode = new TrieNode<>();
				prev.setNext(tempNode);
				tempNode.setHead(prev);
				MyLinkedList<TrieNode<Character>> charNodeList = new MyLinkedList<>();
				tempNode.setCharNodeList(charNodeList);
				tempNode.getCharNodeList().add(node);
			}
			prev = tempNode;
			tempNode = getNextNode(tempNode, node);
		}
		
		if (isCharAdded) {
			TrieNode<Character> lastNode = new TrieNode<>();
			connectPrevNodeInSequence(charArr, charArr.length, prev, lastNode);
			lastNode.setEndOfString(true);
			prev.setNext(lastNode);
			lastNode.setHead(prev);
			return true;
		} else {
			return false;
		}
	}
	
	private TrieNode<Character> getNextNode(TrieNode<Character> tempNode, TrieNode<Character> node) {
		if (null != tempNode.getCharNodeList() && tempNode.getCharNodeList().getSize() > 1) {
			TrieNode<Character> currCharNode = tempNode.getCharNodeList().get(node);
			return currCharNode.getNext();
		} else {
			return tempNode.getNext();
		}
	}
	
	private void connectPrevNodeInSequence(char[] charArr, int index, TrieNode<Character> prev,
			TrieNode<Character> node) {
		TrieNode<Character> prevChar = new TrieNode<>();
		prevChar.setData(charArr[index - 1]);
		TrieNode<Character> prevCharNode = prev.getCharNodeList().get(prevChar);
		prevCharNode.setNext(node);
		node.setHead(prevCharNode);
	}
	
	public boolean search(String s) {
		if (null == root) {
			return false;
		}
		
		TrieNode<Character> tempNode = root;
		TrieNode<Character> node = null;
		TrieNode<Character> temp = null;
		
		for (char c : s.toCharArray()) {
			node = new TrieNode<>();
			node.setData(c);
			if (null != tempNode.getCharNodeList() && !tempNode.getCharNodeList().contains(node)) {
				return false;
			}
			
			temp = tempNode;
			tempNode = getNextNode(tempNode, node);
			if (null == tempNode && null != temp && null != temp.getCharNodeList()) {
				TrieNode<Character> currCharNode = temp.getCharNodeList().get(node);
				tempNode = currCharNode.getNext();
			}
			
			if (null == tempNode) {
				return false;
			}
		}
		
		if (tempNode.isEndOfString()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean remove(String s) {
		if (null == root) {
			return false;
		}
		
		TrieNode<Character> tempNode = root;
		TrieNode<Character> outerNode = root;
		TrieNode<Character> node = null;
		char[] charArr = s.toCharArray();
		
		for (char c : charArr) {
			node = new TrieNode<>();
			node.setData(c);
			if (null != tempNode.getCharNodeList() && !tempNode.getCharNodeList().contains(node)) {
				return false;
			}
			
			if (null != outerNode.getNext() && null != outerNode.getNext().getCharNodeList()
					&& outerNode.getNext().getCharNodeList().contains(node)) {
				outerNode = outerNode.getNext();
			}
			tempNode = getNextNode(tempNode, node);
			
			if (null == tempNode) {
				return false;
			}
		}
		
		if (!tempNode.isEndOfString()) {
			return false;
		}
		
		TrieNode<Character> head = tempNode.getHead();
		int count = s.length();
		boolean isShiftedToOuterNode = false;
		
		while (count > -1) {
			if (count < s.length() && !isShiftedToOuterNode) {
				isShiftedToOuterNode = true;
				TrieNode<Character> tempCharNode = new TrieNode<>();
				tempCharNode.setData(charArr[count]);
				if (outerNode.getCharNodeList().contains(tempCharNode)) {
					tempNode = outerNode;
					head = outerNode.getHead();
				}
			}
			if (null == tempNode.getCharNodeList() || tempNode.getCharNodeList().isEmpty()) {
				if (null != head) {
					head.setNext(null);
				}
			} else if (1 == tempNode.getCharNodeList().getSize() && !tempNode.isEndOfString()) {
				if (null != head) {
					head.setNext(null);
				}
			} else {
				if (count == s.length() && tempNode.isEndOfString()) {
					tempNode.setEndOfString(false);
				} else {
					TrieNode<Character> nodeToRemove = new TrieNode<>();
					nodeToRemove.setData(charArr[count]);
					tempNode.getCharNodeList().remove(nodeToRemove);
				}
				return true;
			}
			tempNode = head;
			
			if (null != head) {
				head = head.getHead();
			} else {
				root = null;
			}
			count--;
		}
		 
		return true;
	}
	
}
