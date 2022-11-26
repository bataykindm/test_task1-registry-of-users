package bdm.soap.exception;

public class RoleNotExistsException extends RuntimeException {
    public RoleNotExistsException(StringBuilder sbOfUnavailableRoles) {
        super("Role " + sbOfUnavailableRoles + "can't be optioned");
    }
}
