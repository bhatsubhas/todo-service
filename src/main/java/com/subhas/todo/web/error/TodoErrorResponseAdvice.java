package com.subhas.todo.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.subhas.todo.business.exception.TodoNotFoundException;

@ControllerAdvice
public class TodoErrorResponseAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TodoNotFoundException.class)
    ErrorResponse todoNotFoundHandler(TodoNotFoundException ex) {
	return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
