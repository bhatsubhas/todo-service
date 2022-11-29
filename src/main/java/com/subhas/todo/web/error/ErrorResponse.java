package com.subhas.todo.web.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus status;
    private String errorMessage;

    public ErrorResponse(HttpStatus status, String errorMessage) {
	this.status = status;
	this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
	return status;
    }

    public String getErrorMessage() {
	return errorMessage;
    }
}
