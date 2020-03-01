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
				node.setHead(prev);
				prev = node;
			} else {
				List<TrieNode<Character>> charNodeList = tempNode.getCharNodeList();
				
				if (null == charNodeList || charNodeList.isEmpty()) {
					isCharAdded = true;
					List<TrieNode<Character>> currCharNodeList = new ArrayList<>();
					currCharNodeList.add(node);
					tempNode.setCharNodeList(currCharNodeList);
					node.setHead(prev);
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
						node.setHead(prev);
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
	
	public boolean remove(String s) {
		if (null == root) {
			return false;
		}
		
		TrieNode<Character> tempNode = null;
		char[] charArr = s.toCharArray();
		
		tempNode = getLastNode(s);
		
		if (null == tempNode) {
			return false;
		}
		
		TrieNode<Character> head = tempNode.getHead();
		int count = s.length();
		
		while (count > -1) {
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
			if (null != head && null != head.getHead()) {
				tempNode = head.getHead().getNext();
			} else {
				tempNode = root;
			}
			
			if (null != head) {
				head = head.getHead();
			} else {
				root = null;
			}
			count--;
		}
		return true;
	}
	
	public TrieNode<Character> getLastNode(String s) {
		if (null == root) {
			return null;
		}
		
		TrieNode<Character> tempNode = root;
		
		for (char c : s.toCharArray()) {
			List<TrieNode<Character>> charNodeList = tempNode.getCharNodeList();
			
			if (null == charNodeList || charNodeList.isEmpty()) {
				return null;
			}
			
			TrieNode<Character> currNode = charNodeList.stream().filter(t -> c == t.getData()).findFirst()
					.orElse(null);
			
			if (null == currNode) {
				return null;
			}
			
			tempNode = currNode.getNext();
			
			if (null == tempNode) {
				return null;
			}
		}
		
		if (tempNode.isEndOfString()) {
			return tempNode;
		} else {
			return null;
		}
	}
	
}
