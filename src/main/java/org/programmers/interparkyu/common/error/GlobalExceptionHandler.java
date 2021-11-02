package org.programmers.interparkyu.common.error;

import javax.validation.ConstraintViolationException;
import org.programmers.interparkyu.common.dto.ApiResponse;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception ex) {
        return ApiResponse.fail(ErrorResponse.builder()
            .errorMessage(ex.getMessage())
            .requestUri("")
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<ErrorResponse> notFoundException(NotFoundException ex) {
        return ApiResponse.fail(ErrorResponse.builder()
            .errorMessage(ex.getMessage())
            .requestUri("")
            .status(HttpStatus.NOT_FOUND)
            .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ErrorResponse> invalidRequestHandler(MethodArgumentNotValidException ex) {
        return ApiResponse.fail(ErrorResponse.builder()
            .errorMessage(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
            .requestUri("")
            .status(HttpStatus.BAD_REQUEST)
            .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<ErrorResponse> invalidRequestHandler(ConstraintViolationException ex) {
        return ApiResponse.fail(ErrorResponse.builder()
            .errorMessage(ex.getMessage())
            .requestUri("")
            .status(HttpStatus.BAD_REQUEST)
            .build());
    }

}
