package com.maxbit.assignment.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationResponse<T> {

	private HttpStatus statusCode;
	private String message;
	private T data;

	public ApplicationResponse(HttpStatus statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

}
