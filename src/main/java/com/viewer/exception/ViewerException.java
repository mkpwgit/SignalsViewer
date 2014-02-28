package com.viewer.exception;

/**
 * A common exception for project.
 */
public class ViewerException extends RuntimeException {

    public ViewerException() {
        super();
    }

    public ViewerException(final String message) {
        super(message);
    }
}
