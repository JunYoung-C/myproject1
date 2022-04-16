package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomFormException;

public class NotFoundAccountByPhoneNumberException extends CustomFormException {
    public NotFoundAccountByPhoneNumberException(String message) {
        super(message);
    }

    public NotFoundAccountByPhoneNumberException(String message, String field) {
        super(message, field);
    }

    public NotFoundAccountByPhoneNumberException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
