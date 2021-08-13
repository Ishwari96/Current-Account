package com.ishwari.accountapi.controller.advice;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ishwari.accountapi.exception.AccountException;
import com.ishwari.accountapi.exception.AccountNotFoundException;
import com.ishwari.accountapi.exception.CustomerNotFoundException;
import com.ishwari.accountapi.exception.TransactionException;

@ControllerAdvice
public class AccountControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccountControllerAdvice.class);

    /**
     * Handle AccountNotFoundException exception from web service controller methods.
     * Return a response with ExceptionData and HTTP status code 404 (not found)
     *
     * @param ex AccountNotFoundException instance
     * @return A ResponseEntity with ExceptionDetail and HTTPStatus 404
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(final AccountNotFoundException ex, final WebRequest request) {
        logger.info("RestExceptionHandler-handleAccountNotFoundException: build custom exception data");
        logger.info("- AccountNotFoundException: ", ex);
        final ExceptionData detail = ExceptionData.builder()
                .exceptionClassType(AccountNotFoundException.class.getName())
                .exceptionMessage(ex.getMessage())
                .exceptionTime(Instant.now())
                .path(request.getDescription(false))
                .responseStatusCode(HttpStatus.NOT_FOUND.value())
                .responseStatusText(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handle CustomerNotFoundException exception from web service controller methods.
     * Return a response with ExceptionData and HTTP status code 404 (not found)
     *
     * @param ex CustomerNotFoundException instance
     * @return A ResponseEntity with ExceptionDetail and HTTPStatus 404
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(final CustomerNotFoundException ex, final WebRequest request) {
        logger.info("RestExceptionHandler-handleCustomerNotFoundException: build custom exception data");
        logger.info("- CustomerNotFoundException: ", ex);
        final ExceptionData detail = ExceptionData.builder()
                .exceptionClassType(CustomerNotFoundException.class.getName())
                .exceptionMessage(ex.getMessage())
                .exceptionTime(Instant.now())
                .path(request.getDescription(false))
                .responseStatusCode(HttpStatus.NOT_FOUND.value())
                .responseStatusText(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handle AccountException exception from web service controller methods.
     * Return a response with ExceptionData and HTTP status code 500 (internal server error)
     *
     * @param ex AccountException instance
     * @return A ResponseEntity with ExceptionDetail and HTTPStatus 500
     */
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> handleAccountException(final AccountException ex, final WebRequest request) {
        logger.info("RestExceptionHandler-handleAccountException: build custom exception data");
        logger.info("- AccountException: ", ex);
        final ExceptionData detail = ExceptionData.builder()
                .exceptionClassType(AccountException.class.getName())
                .exceptionMessage(ex.getMessage())
                .exceptionTime(Instant.now())
                .path(request.getDescription(false))
                .responseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseStatusText(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handle TransactionException exception from web service controller methods.
     * Return a response with ExceptionData and HTTP status code 500 (internal server error)
     *
     * @param ex TransactionException instance
     * @return A ResponseEntity with ExceptionDetail and HTTPStatus 500
     */
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleTransactionException(final TransactionException ex, final WebRequest request) {
        logger.info("RestExceptionHandler-handleTransactionException: build custom exception data");
        logger.info("- TransactionException: ", ex);
        final ExceptionData detail = ExceptionData.builder()
                .exceptionClassType(TransactionException.class.getName())
                .exceptionMessage(ex.getMessage())
                .exceptionTime(Instant.now())
                .path(request.getDescription(false))
                .responseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseStatusText(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handles all Exceptions not addressed by more specific <code>@ExceptionHandler</code> methods.
     * Return a response with ExceptionData and HTTP status code 500 (internal server error)
     *
     * @param ex Exception instance
     * @return A ResponseEntity with ExceptionDetail and HTTPStatus 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(final Exception ex, final WebRequest request) {
        logger.info("RestExceptionHandler-handleGeneralException: build custom exception data");
        logger.error("- Exception: ", ex);
        final ExceptionData detail = ExceptionData.builder()
                .exceptionClassType(TransactionException.class.getName())
                .exceptionMessage(ex.getMessage())
                .exceptionTime(Instant.now())
                .path(request.getDescription(false))
                .responseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseStatusText(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
