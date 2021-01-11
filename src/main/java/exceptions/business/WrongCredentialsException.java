package exceptions.business;

public class WrongCredentialsException extends Throwable {
    public WrongCredentialsException() {
        super("Incorrect username or password. Please try again or sign up!");
    }
}
