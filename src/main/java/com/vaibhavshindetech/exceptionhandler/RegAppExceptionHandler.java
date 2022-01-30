package com.vaibhavshindetech.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vaibhavshindetech.constans.AppConstants;
import com.vaibhavshindetech.exception.AppError;
import com.vaibhavshindetech.exception.RegAppException;

@RestControllerAdvice
public class RegAppExceptionHandler {

	@ExceptionHandler(value = RegAppException.class)
	public ResponseEntity<AppError> handleAppException(RegAppException regAppException) {
		AppError appError = new AppError();
		appError.setErrorCode(AppConstants.REGAPP101);
		appError.setErrorMessage(regAppException.getMessage());
		return new ResponseEntity<AppError>(appError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
