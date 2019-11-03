package com.ds.impl;

import java.util.ArrayList;
import java.util.List;

public class MyDisjointSet<E> {
	List<MySet<E>> sets;

	public List<MySet<E>> getSets() {
		return sets;
	}

	public void setSets(List<MySet<E>> sets) {
		this.sets = sets;
	}
	
	public boolean makeSet(List<E> elements) {
		if (null == sets) {
			sets = new ArrayList<>();
		}
		
		for (E e: elements) {
			List<E> elemList = new ArrayList<>();
			elemList.add(e);
			
			MySet<E> set = new MySet<>();
			
			set.setElements(elemList);
			sets.add(set);
		}
		return true;
	}
	
	public boolean union(E firstSet, E secondSet) {
		MySet<E> set1 = null;
		MySet<E> set2 = null;
		boolean isFound = false;
		int set1Index = 0;
		int set2Index = 0;
		
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i).getElements().contains(firstSet)) {
				set1 = sets.get(i);
				set1Index = i;
			}
			
			if (sets.get(i).getElements().contains(secondSet)) {
				set2 = sets.get(i);
				set2Index = i;
			}
			
			if (null != set1 && null != set2) {
				isFound = true;
				break;
			}
		}
		
		if (isFound) {
			if (set1.getElements().size() >= set2.getElements().size()) {
				for (E e : set2.getElements()) {
					if (!set1.getElements().contains(e)) {
						set1.getElements().add(e);
					}
				}
				sets.remove(set2Index);
			} else {
				for (E e : set1.getElements()) {
					if (!set2.getElements().contains(e)) {
						set2.getElements().add(e);
					}
				}
				sets.remove(set1Index);
			}
			return true;
		}
		return false;
	}
	
	public MySet<E> findSet(E element) {
		for (MySet<E> set : sets) {
			if (set.getElements().contains(element)) {
				return set;
			}
		}
		return null;
	}
}
