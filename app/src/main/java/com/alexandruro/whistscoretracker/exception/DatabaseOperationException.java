package com.alexandruro.whistscoretracker.exception;

/**
 * An exception that happened as a result of a database operation giving an unexpected result.
 * E.g. a database operation resulted in an ExecutionException
 */
public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
