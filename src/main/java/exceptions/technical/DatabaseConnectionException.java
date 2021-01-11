package exceptions.technical;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException() {
        super("Check that you are able to connect to database");
    }
}
