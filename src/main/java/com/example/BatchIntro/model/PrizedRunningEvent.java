package com.example.BatchIntro.model;

public class PrizedRunningEvent {
    private String name;
    private int distance;
    private double prize;
    private double prizePerKm;

    public PrizedRunningEvent() {

    }

    public PrizedRunningEvent(String name, int distance, double prize, double prizePerKm) {
        this.name = name;
        this.distance = distance;
        this.prize = prize;
        this.prizePerKm = prizePerKm;
    }

    public PrizedRunningEvent(BaseRunningEvent runningEvent, double prizePerKm) {
        this.name = runningEvent.getName();
        this.distance = runningEvent.getDistance();
        this.prize = runningEvent.getPrize();
        this.prizePerKm = prizePerKm;
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


    @Override
    public String toString() {
        return "PrizedRunningEvent{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", prize=" + prize +
                ", prizePerKm=" + prizePerKm +
                '}';
    }
}
