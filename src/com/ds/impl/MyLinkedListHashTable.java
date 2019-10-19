package com.ds.impl;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedListHashTable {
	
	private Object[] arr;
	private float loadFactor = 0.75f;
	private int size;
	
	public MyLinkedListHashTable() {
		arr = new Object[16];
	}
	
	public boolean add(String str) {
		
		if (null == str) {
			return false;
		}
		
		int load = (int) (loadFactor * arr.length);
		if (size > load) {
			rehash();
		}
		
		add(str, arr);
		size++;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean contains(String str) {
		
		if (null == str) {
			return false;
		}
		
		int hashCode = hashCode(str, arr.length);
		
		if (arr[hashCode] instanceof String && str.equals(arr[hashCode])) {
			return true;
		} else if (arr[hashCode] instanceof List) {
			List<String> strList = (List<String>) arr[hashCode];
			
			for (String temp : strList) {
				if (temp.equals(str)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean remove(String str) {
		if (null == str) {
			return false;
		}
		
		int hashCode = hashCode(str, arr.length);
		
		if (arr[hashCode] instanceof String && str.equals(arr[hashCode])) {
			arr[hashCode] = null;
			size--;
			return true;
		} else if (arr[hashCode] instanceof List) {
			List<String> strList = (List<String>) arr[hashCode];
			boolean isRemoved = false;
			for (String temp : strList) {
				if (temp.equals(str)) {
					strList.remove(str);
					isRemoved = true;
					break;
				}
			}
			if (isRemoved) {
				size--;
				if (strList.isEmpty()) {
					arr[hashCode] = null;
				} else if (1 == strList.size()) {
					arr[hashCode] = strList.get(0);
				}
				return true;
			}
		}
		return false;
	}
	
	private int hashCode(String str, int size) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((str == null) ? 0 : str.hashCode());
		return Math.abs(result % size);
	}
	
	@SuppressWarnings("unchecked")
	private void rehash() {
		Object[] temp = new Object[arr.length * 2];
		
		for(int i = 0; i < arr.length; i++) {
			if (null != arr[i]) {
				if (arr[i] instanceof String) {
					add(arr[i].toString(), temp);
				} else {
					List<String> strLinkedList = (List<String>) arr[i];
					for (String str : strLinkedList) {
						add(str, temp);
					}
				}
			}
		}
		arr = temp;
	}
	
	@SuppressWarnings("unchecked")
	private void add(String str, Object[] arr) {
		int hashCode = hashCode(str, arr.length);
		
		if (null == arr[hashCode]) {
			arr[hashCode] = str;
		} else if (arr[hashCode] instanceof List) {
			List<String> strList = (List<String>) arr[hashCode];
			
			if (!strList.contains(str)) {
				strList.add(str);
			}
		} else {
			if (!arr[hashCode].toString().equals(str)) {
				List<String> strList = new LinkedList<>();
				
				strList.add(str);
				strList.add(arr[hashCode].toString());
				arr[hashCode] = strList;
			}
		}
	}
}
