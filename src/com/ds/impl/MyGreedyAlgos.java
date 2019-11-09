package com.ds.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyGreedyAlgos {
	
	private static final List<Integer> DENOMINATIONS = new ArrayList<>(Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200, 500, 2000));
	
	public static void activitySelectionProblem(List<Activity> activities) {
		activities.sort((a1, a2) -> a1.getEnd() - a2.getEnd());
		Activity temp = activities.get(0);
		
		System.out.print(temp.getName());
		for (int i = 1; i < activities.size(); i++) {
			if (activities.get(i).getStart() >= temp.getEnd()) {
				System.out.print("-->" + activities.get(i).getName());
				temp = activities.get(i);
			}
		}
	}
	
	public static int coinChangeProblem(int value) {
		int remaining = value;
		int count = 0;
		
		while (remaining > 0) {
			Integer largestThatRemaining = findLargestDenominationSmallerThan(remaining);
			remaining -= largestThatRemaining;
			count++;
		}
		return count;
	}
	
	private static Integer findLargestDenominationSmallerThan(int remaining) {
		for (int i = DENOMINATIONS.size() - 1; i >= 0; i--) {
			if (DENOMINATIONS.get(i) <= remaining) {
				return DENOMINATIONS.get(i);
			}
		}
		return 0;
	}
	
	public static void knapsackProblem(int capacity, List<Item> items) {
		items.sort((i1, i2) ->  {
			double diff = i2.getDensity() - i1.getDensity();
			
			if (diff > 0) {
				return 1;
			} else if (diff < 0) {
				return -1;
			}
			return 0;
		});
		int remainingCapacity = capacity;
		double totalValue = 0;
		
		for (Item item : items) {
			if (item.getWeight() <= remainingCapacity) {
				totalValue += item.getValue();
				remainingCapacity -= item.getWeight();
			} else {
				totalValue += remainingCapacity * item.getDensity();
				break;
			}
		}
		System.out.println("Total value: " + totalValue);
	}
}
