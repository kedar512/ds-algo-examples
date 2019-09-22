package com.ds.exception;

public class TreeOverflowException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9114576095046653131L;
	
	public TreeOverflowException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public TreeOverflowException(String errorMessage) {
        super(errorMessage);
    }

}
