package com.stathis.cardcostapi.controllers;

import com.stathis.cardcostapi.error.ApiError;
import com.stathis.cardcostapi.exception.ResourceConstraintViolationException;
import com.stathis.cardcostapi.exception.ResourceNotDeletedException;
import com.stathis.cardcostapi.exception.ResourceNotFoundException;
import com.stathis.cardcostapi.exception.ResourceNotUpdatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class,
            ResourceNotDeletedException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(buildApiError(exception, request, HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceConstraintViolationException.class,
            ResourceNotUpdatedException.class})
    public ResponseEntity<Object> handleBadRequest(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(buildApiError(exception, request, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private ApiError buildApiError(Exception exception, HttpServletRequest request, HttpStatus httpStatus) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
