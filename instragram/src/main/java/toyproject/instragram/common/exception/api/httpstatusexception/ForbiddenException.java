package toyproject.instragram.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import toyproject.instragram.common.exception.api.CustomApiException;

public class ForbiddenException extends CustomApiException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, HttpStatus status) {
        super(message, status);
    }
}
