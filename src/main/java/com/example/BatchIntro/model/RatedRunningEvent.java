package com.example.BatchIntro.model;

public class RatedRunningEvent {
    private String name;
    private int distance;
    private double prize;
    private double prizePerKm;
    private double rate;

    public RatedRunningEvent() {
    }

    public RatedRunningEvent(String name, int distance, double prize, double prizePerKm, double rate) {
        this.name = name;
        this.distance = distance;
        this.prize = prize;
        this.prizePerKm = prizePerKm;
        this.rate = rate;
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

    public double getPrizePerKm() {
        return prizePerKm;
    }

    public void setPrizePerKm(double prizePerKm) {
        this.prizePerKm = prizePerKm;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "RatedRunningEvent{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", prize=" + prize +
                ", prizePerKm=" + prizePerKm +
                ", rate=" + rate +
                '}';
    }
}
