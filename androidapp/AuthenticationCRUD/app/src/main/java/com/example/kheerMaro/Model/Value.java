package com.example.kheerMaro.Model;

public class Value {
    private String id,plantID,temperature,humidity,soilMoisture;
    private Boolean waterLevel , wateringActivated ;


    public Value() {
    }


    public Value(String id, String plantID, String temperature, String humidity, String soilMoisture, Boolean waterLevel,Boolean wateringActivated) {
        this.id = id;
        this.plantID = plantID;
        this.temperature = temperature;
        this.humidity = humidity;
        this.soilMoisture = soilMoisture;
        this.waterLevel = waterLevel;
        this.wateringActivated = wateringActivated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public Value(String id) {
        this.id = id;
    }

    public Value(String id, String plantID) {
        this.id = id;
        this.plantID = plantID;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setSoilMoisture(String soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public void setWaterLevel(Boolean waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getId() {
        return id;
    }

    public String getPlantID() {
        return plantID;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSoilMoisture() {
        return soilMoisture;
    }

    public Boolean getWaterLevel() {
        return waterLevel;
    }

    public Boolean getWateringActivated() {
        return wateringActivated;
    }

    public void setWateringActivated(Boolean wateringActivated) {
        this.wateringActivated = wateringActivated;
    }
}
