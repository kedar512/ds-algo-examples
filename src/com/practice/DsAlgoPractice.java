package com.practice;

import com.ds.impl.MyLinkedList;

public class DsAlgoPractice {
	public static void main(String[] args) {
		MyLinkedList<Integer> list = new MyLinkedList<>();
		
		list.add(20, 2);
		System.out.println(list.getSize());
		list.add(21, 2);
		System.out.println(list.getSize());
		list.add(22, 2);
		System.out.println(list.getSize());
		System.out.println(list.contains(22));
	}
}
