package com.practice;

import com.ds.impl.MyStack;

public class DsAlgoPractice {
	public static void main(String[] args) {
		MyStack stack = new MyStack(3);
		
		stack.push(20);
		System.out.println(stack.peek());
		stack.push(30);
		System.out.println(stack.peek());
		stack.pop();
		System.out.println(stack.peek());
	}
}
