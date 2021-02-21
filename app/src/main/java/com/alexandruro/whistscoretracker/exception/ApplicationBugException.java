package com.alexandruro.whistscoretracker.exception;

/**
 * Represents an exception that should not have happened.
 */
public class ApplicationBugException extends RuntimeException {

    public ApplicationBugException(String message) {
        super(message);
    }
}
