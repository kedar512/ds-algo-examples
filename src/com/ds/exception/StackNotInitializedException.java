package com.ds.exception;

public class StackNotInitializedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3646964514908664920L;
	
	public StackNotInitializedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public StackNotInitializedException(String errorMessage) {
        super(errorMessage);
    }

}
