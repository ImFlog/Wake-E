package com.wake_e.exceptions;

public class NoRouteFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public NoRouteFoundException() {
    }

    public NoRouteFoundException(String detailMessage) {
	super(detailMessage);
    }

    public NoRouteFoundException(Throwable throwable) {
	super(throwable);
    }

    public NoRouteFoundException(String detailMessage, Throwable throwable) {
	super(detailMessage, throwable);
    }

}
