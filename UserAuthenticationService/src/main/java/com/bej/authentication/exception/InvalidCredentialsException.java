package com.bej.authentication.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Use the@ResponseStatus annotation to set the exception message and status
@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "Invalid Credential")
public class InvalidCredentialsException extends Exception{
}
