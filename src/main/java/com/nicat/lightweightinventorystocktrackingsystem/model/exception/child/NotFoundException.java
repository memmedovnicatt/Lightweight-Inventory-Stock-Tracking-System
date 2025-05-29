package com.nicat.lightweightinventorystocktrackingsystem.model.exception.child;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
