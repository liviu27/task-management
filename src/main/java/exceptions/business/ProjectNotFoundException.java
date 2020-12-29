package exceptions.business;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(int id) {
        super("Project not found using id: " + id);
    }
}
