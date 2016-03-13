package be.kdg.teamf.kandoe_app.resource;

/**
 * Created by admin on 10/03/2016.
 */
public class UserResourcePost {
    public String username;
    public String password;

    public UserResourcePost(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

