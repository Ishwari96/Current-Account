package com.ishwari.accountapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ishwari.accountapi.utility.Constants;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

    /**
	 * serialVersion
	 */
	private static final long serialVersionUID = 1L;

    public CustomerNotFoundException() {
        super(Constants.STANDRED_MESSAGE);
    }
}
