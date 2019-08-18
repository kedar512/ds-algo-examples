package com.ds.exception;

public class LinkedListNotInitializedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5948960117647923045L;
	
	public LinkedListNotInitializedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public LinkedListNotInitializedException(String errorMessage) {
        super(errorMessage);
    }

}
