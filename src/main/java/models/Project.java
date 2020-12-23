package models;

public class Project {

    private int id;
    private String name;
    private double budget;
    private String currency;


    public Project(int id, String name, double budget, String currency) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Project " +id +
                ": " + name +
                " - budget = " + budget +
                ' ' + currency;
    }
}
