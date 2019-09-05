package com.practice;

import com.ds.impl.MyLinkedListQueue;

public class DsAlgoPractice {
	public static void main(String[] args) {
		MyLinkedListQueue<Integer> q = new MyLinkedListQueue<>();
		
		q.add(0);
		q.add(1);
		System.out.println(q.poll());
		System.out.println(q.poll());
		q.poll();
	}
	
}
