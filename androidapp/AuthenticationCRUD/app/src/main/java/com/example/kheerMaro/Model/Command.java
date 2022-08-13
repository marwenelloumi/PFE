package com.example.kheerMaro.Model;

public class Command {
    private String plantID,userEmail, recptionDate,plantName;
    private Boolean commandeSysteme;

    public Command() {

    }

    public Command(String plantID, String userEmail, String recptionDate, Boolean commandeSysteme , String plantName) {
        this.plantID = plantID;
        this.userEmail = userEmail;
        this.recptionDate = recptionDate;
        this.commandeSysteme = commandeSysteme;
        this.plantName = plantName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantID() {
        return plantID;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRecptionDate() {
        return recptionDate;
    }

    public void setRecptionDate(String recptionDate) {
        this.recptionDate = recptionDate;
    }

    public Boolean getCommandeSysteme() {
        return commandeSysteme;
    }

    public void setCommandeSysteme(Boolean commandeSysteme) {
        this.commandeSysteme = commandeSysteme;
    }
}
