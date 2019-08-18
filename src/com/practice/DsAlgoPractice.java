package com.practice;

import com.ds.impl.MyLinkedListStack;

public class DsAlgoPractice {
	public static void main(String[] args) {
		MyLinkedListStack<Integer> stack = new MyLinkedListStack<>();
		
		System.out.println(stack.isEmpty());

		stack.push(20);
		System.out.println(stack.isEmpty());
		System.out.println(stack.peek());
		System.out.println(stack.peek());
		stack.push(21);
		System.out.println(stack.pop());
		stack.pop();
		stack.delete();
	}
}
