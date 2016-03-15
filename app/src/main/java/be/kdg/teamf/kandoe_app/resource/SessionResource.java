package be.kdg.teamf.kandoe_app.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 14/03/2016.
 */
public class SessionResource {
    private Integer id;
    private Integer currentUser;
    private List<CardSessionResource> cardSessionResources;
    private boolean problem;
    private boolean gameOver;
    private int amountOfCircles;
    private List<RemarkResource> remarks;
    private String nameSession;
    private int amountOfUsers;

    public SessionResource() {
        cardSessionResources = new ArrayList<>();
        remarks = new ArrayList<>();
    }

    public List<CardSessionResource> getCardSessionResources() {
        return cardSessionResources;
    }

    public void setCardSessionResources(List<CardSessionResource> cardSessionResources) {
        this.cardSessionResources = cardSessionResources;
    }

    public List<RemarkResource> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<RemarkResource> remarks) {
        this.remarks = remarks;
    }

    public Integer getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Integer currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isProblem() {
        return problem;
    }

    public void setProblem(boolean problem) {
        this.problem = problem;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getAmountOfCircles() {
        return amountOfCircles;
    }

    public void setAmountOfCircles(int amountOfCircles) {
        this.amountOfCircles = amountOfCircles;
    }

    public String getNameSession() {
        return nameSession;
    }

    public void setNameSession(String nameSession) {
        this.nameSession = nameSession;
    }

    public int getAmountOfUsers() {
        return amountOfUsers;
    }

    public void setAmountOfUsers(int amountOfUsers) {
        this.amountOfUsers = amountOfUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
