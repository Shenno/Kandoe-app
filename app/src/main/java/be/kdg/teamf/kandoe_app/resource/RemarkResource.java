package be.kdg.teamf.kandoe_app.resource;

import java.io.Serializable;


/**
 * Created by admin on 14/03/2016.
 */
public class RemarkResource implements Serializable {
    private String text;

    private Integer[] timeStamp;

    private String username;

    public RemarkResource() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer[] getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer[] timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

