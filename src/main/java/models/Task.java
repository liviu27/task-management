package models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Task {

    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private int userId;
    private int projectId;
    private int workedHours;


}
