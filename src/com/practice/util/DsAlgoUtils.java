package com.practice.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
	
	public static List<Integer> primeFactors(int n) {
		// Print the number of 2s that divide n
		List<Integer> factors = new ArrayList<>();

		while (n % 2 == 0) {
			factors.add(2);
			n /= 2;
		}

		// n must be odd at this point. So we can
		// skip one element (Note i = i +2)
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			// While i divides n, print i and divide n
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}

		// This condition is to handle the case whien
		// n is a prime number greater than 2
		if (n > 2) {
			factors.add(n);
		}
		return factors;
	}

	private static String[] bigSorting(String[] unsorted) {
		return unsorted;
	}

	public static Integer findMaxInt(Stack<Integer> stack) {
		return stack.stream().max(Integer::compare).get();
	}
}
