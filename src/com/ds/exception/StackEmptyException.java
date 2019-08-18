package com.ds.exception;

public class StackEmptyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1330958819337960154L;
	
	public StackEmptyException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public StackEmptyException(String errorMessage) {
        super(errorMessage);
    }

}
