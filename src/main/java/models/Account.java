package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Account {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String type;

    @Override
    public String toString() {
        return "Account " + id +
                "[name: " + name +
                ", username: " + username +
                ", email: " + email +
                ']';
    }

}
