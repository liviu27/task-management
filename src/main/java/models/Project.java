package models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Project {

    private int id;
    private String name;
    private double budget;
    private String currency;


    @Override
    public String toString() {
        return "Project " +id +
                ": " + name +
                " - budget = " + budget +
                ' ' + currency;
    }
}
