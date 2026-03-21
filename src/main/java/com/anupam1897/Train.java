package com.anupam1897;
public class Train {
    private String model;
    private String trainId;

    public Train(String model, String trainId) {
        this.model = model;
        this.trainId = trainId;
    }

    public String getModel() { return model; }
    public String getTrainId() { return trainId; }

    @Override
    public String toString() {
        return "Train[" + trainId + "] - " + model;
    }
}