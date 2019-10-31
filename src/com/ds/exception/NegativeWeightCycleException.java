package com.ds.exception;

public class NegativeWeightCycleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6732711199427797729L;
	
	public NegativeWeightCycleException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
    public NegativeWeightCycleException(String errorMessage) {
        super(errorMessage);
    }

}
