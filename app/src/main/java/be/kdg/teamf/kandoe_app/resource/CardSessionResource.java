package be.kdg.teamf.kandoe_app.resource;

import java.io.Serializable;

/**
 * Created by admin on 14/03/2016.
 */
public class CardSessionResource implements Serializable {

    private Integer id;
    private Integer distanceToCenter;
    private String card;
    private String image;

    public CardSessionResource() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDistanceToCenter() {
        return distanceToCenter;
    }

    public void setDistanceToCenter(Integer distanceToCenter) {
        this.distanceToCenter = distanceToCenter;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

