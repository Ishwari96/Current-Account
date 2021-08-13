package com.ishwari.accountapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    /**
	 * serialVersion
	 */
	private static final long serialVersionUID = 1L;
	private static final String standardMessage = "Account not found";

    public AccountNotFoundException() { super(standardMessage);}

}
