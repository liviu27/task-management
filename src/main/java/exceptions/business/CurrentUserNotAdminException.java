package exceptions.business;

public class CurrentUserNotAdminException extends RuntimeException {
    public CurrentUserNotAdminException(String username) {
        super("Curent username: " + username + " does not have admin privileges");
    }
}
