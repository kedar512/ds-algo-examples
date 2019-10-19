package com.practice.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

public class DsAlgoUtils {
	
	public static void readMultipleArgumentsFromStdin() {
		try (Scanner in = new Scanner(System.in)) {
			Integer noOfInputs = 0;
			int count = -1;
			
			while (in.hasNext() && noOfInputs >= count) {
				if (-1 == count) {
					noOfInputs = Integer.valueOf(in.nextLine());
					count++;
				} else {
					String s = in.nextLine();
					System.out.println(s);
					// logic here
					count++;
				}
			}
		}
	}
	
	public static void readMultipleArgumentsFromFile() {
		String file = "src/input.txt";
		int count = 0;
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null)
			if (0 == count) {
				System.out.println("Number of inputs: " + line);
				count++;
			} else {
				// Logic here
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Integer findMaxInt(Stack<Integer> stack) {
		return stack.stream().max(Integer::compare).get();
	}
}
