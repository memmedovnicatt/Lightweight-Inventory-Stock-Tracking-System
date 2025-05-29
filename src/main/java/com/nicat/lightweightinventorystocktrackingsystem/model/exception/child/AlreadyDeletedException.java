package com.nicat.lightweightinventorystocktrackingsystem.model.exception.child;

public class AlreadyDeletedException extends RuntimeException {
    public AlreadyDeletedException(String message) {
        super(message);
    }
}
