package io.mohami.test.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author andrey
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotATextException  extends RuntimeException {

    public NotATextException() {
    }
    
}
