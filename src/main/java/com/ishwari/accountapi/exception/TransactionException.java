package com.ishwari.accountapi.exception;

public class TransactionException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public TransactionException(final String message) {
        super(message);
    }

}

