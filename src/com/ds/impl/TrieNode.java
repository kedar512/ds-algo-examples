package com.ds.impl;

public class TrieNode<E> {
	private TrieNode<E> head;
	private E data;
	private int size;
	private TrieNode<E> next;
	private boolean endOfString;
	private MyLinkedList<TrieNode<Character>> charNodeList;
	
	@Override
	public String toString() {
		if (null != data) {
			return data.toString();
		} else {
			return null;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		TrieNode<E> other = (TrieNode<E>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	public MyLinkedList<TrieNode<Character>> getCharNodeList() {
		return charNodeList;
	}
	public void setCharNodeList(MyLinkedList<TrieNode<Character>> charNodeList) {
		this.charNodeList = charNodeList;
	}
	public boolean isEndOfString() {
		return endOfString;
	}
	public void setEndOfString(boolean endOfString) {
		this.endOfString = endOfString;
	}
	public TrieNode<E> getHead() {
		return head;
	}
	public void setHead(TrieNode<E> head) {
		this.head = head;
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
	public TrieNode<E> getNext() {
		return next;
	}
	public void setNext(TrieNode<E> next) {
		this.next = next;
	}
	
}
