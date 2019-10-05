package com.ds.exception;

public class EmptyTreeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6388631227252306910L;
	
	public EmptyTreeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public EmptyTreeException(String errorMessage) {
        super(errorMessage);
    }

}
