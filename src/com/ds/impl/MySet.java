package com.ds.impl;

import java.util.List;

public class MySet<E> {
	List<E> elements;
	MySet<E> movedToSet;

	public List<E> getElements() {
		return elements;
	}

	public void setElements(List<E> elements) {
		this.elements = elements;
	}

	public MySet<E> getMovedToSet() {
		return movedToSet;
	}

	public void setMovedToSet(MySet<E> movedToSet) {
		this.movedToSet = movedToSet;
	}
}
