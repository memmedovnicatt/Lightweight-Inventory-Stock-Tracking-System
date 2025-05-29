package com.nicat.lightweightinventorystocktrackingsystem.model.exception;

import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.AlreadyDeletedException;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.AlreadyExistName;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.CanNotDeletedException;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException notFoundException) {
        log.error("NotFoundException ->  {}", notFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handleNAlreadyDeletedException(AlreadyDeletedException alreadyDeletedException) {
        log.error("AlreadyDeletedException ->  {}", alreadyDeletedException.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alreadyDeletedException.getMessage());
    }

    @ExceptionHandler(AlreadyExistName.class)
    public ResponseEntity<String> handleNAlreadyExistException(AlreadyExistName alreadyExistName) {
        log.error("AlreadyExistException ->  {}", alreadyExistName.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alreadyExistName.getMessage());
    }

    @ExceptionHandler(CanNotDeletedException.class)
    public ResponseEntity<String> handleCanNotDeletedException(CanNotDeletedException canNotDeletedException) {
        log.error("CanNotDeletedException ->  {}", canNotDeletedException.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(canNotDeletedException.getMessage());
    }
}