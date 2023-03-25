package com.lifung.task.management.exception;

public class PermissionDeny extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PermissionDeny(String message) {
        super(message);
    }
}
