package com.ds.exception;

public class QueueOverflowException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7342912675015818568L;
	
	public QueueOverflowException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public QueueOverflowException(String errorMessage) {
        super(errorMessage);
    }

}
