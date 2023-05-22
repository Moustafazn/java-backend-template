package io.task.template.services.exceptions;


import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private final String code;
    private final HttpStatus httpStatus;

    private BusinessException(final String message, final String code, final HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public static BusinessException of(final String message) {
        return new BusinessException(message, null, null);
    }

    public static BusinessException of(final String message, final String code) {
        return new BusinessException(message, code, null);
    }

    public static BusinessException of(final String message, final String code, final HttpStatus httpStatus) {
        return new BusinessException(message, code, httpStatus);
    }

    public static BusinessException badRequest(final String message, final String code) {
        return of(message, code, HttpStatus.BAD_REQUEST);
    }

    public static BusinessException unAuthorizedRequest(final String message, final String code) {
        return of(message, code, HttpStatus.UNAUTHORIZED);
    }

    public String getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}