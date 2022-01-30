package com.vaibhavshindetech.exception;

import lombok.Data;

@Data
public class AppError {
	private String errorCode;
	private String errorMessage;
}
