package bdm.soap.exception;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException() {
        super("User with such login doesn't exist.");
    }
}
