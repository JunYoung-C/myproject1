package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomFormException;

public class IncorrectPasswordException extends CustomFormException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, String field) {
        super(message, field);
    }

    public IncorrectPasswordException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
