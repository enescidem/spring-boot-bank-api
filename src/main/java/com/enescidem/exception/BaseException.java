package com.enescidem.exception;

public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
    }
}
