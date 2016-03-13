package be.kdg.teamf.kandoe_app.resource;

/**
 * Created by admin on 10/03/2016.
 */
public class UserResource
{
    // User properties
    private Integer id;

    private String username;

    private String firstName;

    private String lastName;

   /* public UserResource(User user)
    {
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
