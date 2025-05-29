package com.nicat.lightweightinventorystocktrackingsystem.model.exception.child;

public class AlreadyExistName extends RuntimeException {
    public AlreadyExistName(String message) {
        super(message);
    }
}
