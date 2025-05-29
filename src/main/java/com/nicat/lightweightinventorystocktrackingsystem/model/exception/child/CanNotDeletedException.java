package com.nicat.lightweightinventorystocktrackingsystem.model.exception.child;

public class CanNotDeletedException extends RuntimeException {
  public CanNotDeletedException(String message) {
    super(message);
  }
}
