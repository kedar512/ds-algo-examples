package com.practice.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public static void readDataForHackerrankInput() throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		int n = Integer.parseInt(reader.readLine());

		String[] unsorted = new String[n];

		for (int i = 0; i < n; i++) {
			String unsortedItem = reader.readLine();
			unsorted[i] = unsortedItem;
		}

		// calling sample method
		String[] result = bigSorting(unsorted);

		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(result[i]);

			if (i != result.length - 1) {
				bufferedWriter.write("\n");
			}
		}

		bufferedWriter.newLine();
		bufferedWriter.close();
		reader.close();
	}

	private static String[] bigSorting(String[] unsorted) {
		return unsorted;
	}

	public static Integer findMaxInt(Stack<Integer> stack) {
		return stack.stream().max(Integer::compare).get();
	}
}
