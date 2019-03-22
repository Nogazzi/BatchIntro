package com.example.BatchIntro.model;

public class BaseRunningEvent {
    private String name;
    private int distance;
    private double prize;

    public BaseRunningEvent() {

    }

    public BaseRunningEvent(String name, int distance, double prize) {
        this.name = name;
        this.distance = distance;
        this.prize = prize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    @Override
    public String toString() {
        return "BaseRunningEvent{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", prize=" + prize +
                '}';
    }
}
