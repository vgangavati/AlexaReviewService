package com.signify.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.signify.review.exception.AlexaReviewException;

@ControllerAdvice
public class AlexaReviewControllerAdvice {
	@ResponseBody
	@ExceptionHandler(AlexaReviewException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String serviceFailed(AlexaReviewException ex) {
		return ex.getMessage();
	}
}
