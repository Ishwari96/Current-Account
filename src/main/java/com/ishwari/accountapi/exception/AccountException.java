package com.ishwari.accountapi.exception;

public class AccountException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountException() {
        super();
    }

    public AccountException(final String message) {
        super(message);
    }
}
