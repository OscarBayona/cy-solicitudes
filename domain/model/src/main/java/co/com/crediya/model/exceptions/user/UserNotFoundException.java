package co.com.crediya.model.exceptions.user;

import co.com.crediya.model.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
