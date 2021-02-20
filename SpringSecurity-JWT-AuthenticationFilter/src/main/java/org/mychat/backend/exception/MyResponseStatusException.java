package org.mychat.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

public class MyResponseStatusException extends ResponseStatusException {


    public MyResponseStatusException(HttpStatus status) {
        super(status, null, null);
    }


    public MyResponseStatusException(HttpStatus status, @Nullable String reason) {
        super(status, reason, null);
    }


    public MyResponseStatusException(HttpStatus status, @Nullable String reason, @Nullable Throwable cause) {
        super(status, reason, cause);
    }



    public static MyResponseStatusException asForbidden(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.FORBIDDEN);
    }
    public static MyResponseStatusException asBadRequest(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.BAD_REQUEST);
    }
    public static MyResponseStatusException asBadRequest(String msg, Throwable throwable) {
        return new MyResponseStatusException( HttpStatus.BAD_REQUEST,msg,throwable);
    }

    public static MyResponseStatusException asServerError(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   public static MyResponseStatusException asNoContent(String msg) {
        return asExceptionFromHttpStatus(msg, HttpStatus.BAD_REQUEST);
    }


    public static MyResponseStatusException asConstraintViolation(ConstraintViolationException e) {

        return  new MyResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
    }

    public static MyResponseStatusException asExceptionFromHttpStatus(String msg, HttpStatus httpStatus) {
        return new MyResponseStatusException(httpStatus, msg);
    }


}
