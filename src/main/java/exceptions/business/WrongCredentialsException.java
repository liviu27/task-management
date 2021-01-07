package exceptions.business;

public class WrongCredentialsException extends Throwable {
    public WrongCredentialsException() {
        super("Username or password are incorrect. Please try again or sign up!");
    }
}
