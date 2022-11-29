package com.subhas.todo.business.exception;

public class TodoNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TodoNotFoundException(Long id) {
	super(String.format("Could not find Todo with id %d", id));
    }

}
