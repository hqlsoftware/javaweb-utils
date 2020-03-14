package com.heqilin.util.exception;

public class InstantiationException extends RuntimeException {

    public InstantiationException(String message) {
        super(message);
    }

    public InstantiationException(String s, Throwable t) {
        super(s, t);
    }
}
