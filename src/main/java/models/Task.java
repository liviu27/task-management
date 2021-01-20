package models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Task {

    private int id;
    private String title;
    private String description;
    private TaskCategory category;
    private TaskStatus status;
    private int userId;
    private int projectId;
    private int workedHours;

    @Override
    public String toString() {
        return "Task no. " + id
                + ("\ntitle: " + title).indent(3)
                + ("description: " + description).indent(3)
                + ("category: " + category).indent(3)
                + ("status: " + status).indent(3)
                + ("assigned to: " + userId).indent(3)
                + ("for project: " + projectId).indent(3)
                + ("worked_time: " + workedHours + " hour(s).").indent(3);
    }
}
