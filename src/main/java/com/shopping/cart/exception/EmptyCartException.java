package com.shopping.cart.exception;

public class EmptyCartException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060277836443174531L;
	
	public EmptyCartException(String errorMessage) {
		super(errorMessage);
	}

}
