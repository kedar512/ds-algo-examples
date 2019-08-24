package com.practice;

import java.util.Scanner;
import java.util.Stack;

public class DsAlgoPractice {
	public static void main(String[] args) {
		
		Stack<Integer> stack = new Stack<>();
		Integer max = 0;

		try (Scanner in = new Scanner(System.in)) {
			Integer noOfInputs = 0;
			int count = -1;
			
			while (in.hasNext() && noOfInputs >= count) {
				if (-1 == count) {
					noOfInputs = Integer.valueOf(in.nextLine());
					count++;
				} else {
					String s = in.nextLine();
					if ("1".equals(s.split("\\s+")[0])) {
						Integer temp = Integer.valueOf(s.split("\\s+")[1]);
						if (stack.isEmpty() || temp > max) {
							max = temp;
						}
						stack.push(temp);
					} else if ("2".equals(s.split("\\s+")[0])) {
						stack.pop();
						if (stack.isEmpty()) {
							max = 0;
						} else {
							max = stack.stream().max(Integer::compare).get();
						}
					} else {
						System.out.println(max);
					}
					count++;
				}
			}
		}
	}
}
