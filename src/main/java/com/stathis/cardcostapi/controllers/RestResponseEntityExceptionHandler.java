package com.stathis.cardcostapi.controllers;

import com.stathis.cardcostapi.error.ApiError;
import com.stathis.cardcostapi.exception.ResourceConstraintViolationException;
import com.stathis.cardcostapi.exception.ResourceNotDeletedException;
import com.stathis.cardcostapi.exception.ResourceNotFoundException;
import com.stathis.cardcostapi.exception.ResourceNotUpdatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class,
            ResourceNotDeletedException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(buildApiError(exception.getMessage(), request, HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceConstraintViolationException.class,
            ResourceNotUpdatedException.class})
    public ResponseEntity<Object> handleBadRequest(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(buildApiError(exception.getMessage(), request, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        return new ResponseEntity<>(buildApiError(errorMessage, httpServletRequest, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private ApiError buildApiError(String errorMessage, HttpServletRequest request, HttpStatus httpStatus) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(errorMessage)
                .path(request.getRequestURI())
                .build();
    }
}
