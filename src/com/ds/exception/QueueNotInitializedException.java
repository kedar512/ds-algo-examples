package com.ds.exception;

public class QueueNotInitializedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4095198793673044392L;
	
	public QueueNotInitializedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public QueueNotInitializedException(String errorMessage) {
        super(errorMessage);
    }

}
