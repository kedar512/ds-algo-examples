package com.ds.impl;

import java.util.ArrayList;
import java.util.List;

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
			char c = charArr[i];
			node = new TrieNode<>();
			node.setData(c);
			
			if (null == root) {
				isCharAdded = true;
				root = new TrieNode<>();
				List<TrieNode<Character>> charNodeList = new ArrayList<>();
				root.setCharNodeList(charNodeList);
				root.getCharNodeList().add(node);
				prev = node;
				tempNode = node.getNext();
			} else if (null == tempNode) {
				isCharAdded = true;
				TrieNode<Character> parentNode = new TrieNode<>();
				List<TrieNode<Character>> currCharNodeList = new ArrayList<>();
				currCharNodeList.add(node);
				parentNode.setCharNodeList(currCharNodeList);
				prev.setNext(parentNode);
				parentNode.setHead(prev);
				prev = node;
			} else {
				List<TrieNode<Character>> charNodeList = tempNode.getCharNodeList();
				
				if (null == charNodeList || charNodeList.isEmpty()) {
					isCharAdded = true;
					List<TrieNode<Character>> currCharNodeList = new ArrayList<>();
					currCharNodeList.add(node);
					tempNode.setCharNodeList(currCharNodeList);
					prev = node;
					tempNode = node.getNext();
				} else {
					TrieNode<Character> currNode = charNodeList.stream().filter(t -> c == t.getData()).findFirst()
							.orElse(null);
					
					if (null != currNode) {
						prev = currNode;
						tempNode = currNode.getNext();
					} else {
						isCharAdded = true;
						charNodeList.add(node);
						prev = node;
						tempNode = node.getNext();
					}
				}
			}
		}
		
		if (isCharAdded) {
			TrieNode<Character> lastNode = new TrieNode<>();
			lastNode.setEndOfString(true);
			prev.setNext(lastNode);
			lastNode.setHead(prev);
			return true;
		} else if (!tempNode.isEndOfString()) {
			tempNode.setEndOfString(true);
			return true;
		} else {
			return false;
		}
	}
	
	private TrieNode<Character> getNextNode(TrieNode<Character> tempNode, TrieNode<Character> node) {
		TrieNode<Character> nextNode = null;
		if (null != tempNode.getCharNodeList() && tempNode.getCharNodeList().size() > 1) {
			TrieNode<Character> currCharNode = tempNode.getCharNodeList().stream()
					.filter(e -> node.getData().equals(e.getData())).findFirst().orElse(new TrieNode<>());
			nextNode = currCharNode.getNext();
		} else {
			nextNode = tempNode.getNext();
		}
		
		if (null == nextNode && null != tempNode && null != tempNode.getCharNodeList()) {
			TrieNode<Character> currCharNode = tempNode.getCharNodeList().stream()
					.filter(e -> node.getData().equals(e.getData())).findFirst().orElse(new TrieNode<>());
			return currCharNode.getNext();
		}
		return nextNode;
	}
	
	public boolean search(String s) {
		if (null == root) {
			return false;
		}
		
		TrieNode<Character> tempNode = root;
		
		for (char c : s.toCharArray()) {
			List<TrieNode<Character>> charNodeList = tempNode.getCharNodeList();
			
			if (null == charNodeList || charNodeList.isEmpty()) {
				return false;
			}
			
			TrieNode<Character> currNode = charNodeList.stream().filter(t -> c == t.getData()).findFirst()
					.orElse(null);
			
			if (null == currNode) {
				return false;
			}
			
			tempNode = currNode.getNext();
			
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
	
	public int findPartialMatchCount(String s) {
		if (null == root) {
			return 0;
		}
		
		TrieNode<Character> tempNode = root;
		TrieNode<Character> node = null;
		
		for (char c : s.toCharArray()) {
			node = new TrieNode<>();
			node.setData(c);
			if (null != tempNode.getCharNodeList() && !tempNode.getCharNodeList().contains(node)) {
				return 0;
			}
			
			tempNode = getNextNode(tempNode, node);
			
			if (null == tempNode) {
				return 0;
			}
		}
		List<Integer> countList = new ArrayList<>();
		countList.add(0);
		return findRecursiveMatchCount(tempNode, countList);
	}
	
	private int findRecursiveMatchCount(TrieNode<Character> node, List<Integer> countList) {
		if (null != node.getNext() && node.getNext().isEndOfString()) {
			Integer count = countList.get(0);
			countList.remove(0);
			countList.add(count + 1);
		}
		
		if (null != node.getCharNodeList() && node.getCharNodeList().size() > 1) {
			for (TrieNode<Character> temp : node.getCharNodeList()) {
				findRecursiveMatchCount(temp, countList);
			}
		} else if (null != node.getNext()) {
			findRecursiveMatchCount(node.getNext(), countList);
		}
		return countList.get(0);
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
				TrieNode<Character> tempCharNode = new TrieNode<>();
				tempCharNode.setData(charArr[count]);
				if (outerNode.getCharNodeList().contains(tempCharNode)) {
					isShiftedToOuterNode = true;
					tempNode = outerNode;
					head = outerNode.getHead();
				}
			}
			if (null == tempNode.getCharNodeList() || tempNode.getCharNodeList().isEmpty()) {
				if (null != head) {
					head.setNext(null);
				}
			} else if (1 == tempNode.getCharNodeList().size() && !tempNode.isEndOfString()) {
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
