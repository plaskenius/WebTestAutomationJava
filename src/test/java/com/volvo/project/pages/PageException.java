package com.project.project.pages;

public class PageException extends RuntimeException {

    private static final long serialVersionUID = -1554365434537109298L;

    public PageException() {
        super();
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(String message) {
        super(message);
    }

    public PageException(Throwable cause) {
        super(cause);
    }

}
