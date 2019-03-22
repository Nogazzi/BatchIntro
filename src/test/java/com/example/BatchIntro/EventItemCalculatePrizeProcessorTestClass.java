package com.example.BatchIntro;

import com.example.BatchIntro.model.InputRunningEvent;
import com.example.BatchIntro.processing.EventItemCalculatePrizeProcessor;
import org.junit.Assert;
import org.junit.Test;

public class EventItemCalculatePrizeProcessorTestClass {

    EventItemCalculatePrizeProcessor eventItemCalculatePrizeProcessor = new EventItemCalculatePrizeProcessor();

    @Test
    public void getEventRateTest(){
        InputRunningEvent event = new InputRunningEvent("ABC", 10, 10.0d);
        double eventRate = eventItemCalculatePrizeProcessor.getEventRate(event);
        Assert.assertEquals(99.0d, eventRate, 0.0d);
    }

    @Test
    public void getEventRateTest_2(){
        InputRunningEvent event = new InputRunningEvent("ABC", 10, 129);
        double eventRate = eventItemCalculatePrizeProcessor.getEventRate(event);
        Assert.assertEquals(87.1d, eventRate, 0.0d);
    }

    @Test
    public void getPrizePerKilometer() {
        InputRunningEvent event = new InputRunningEvent("ABC", 10, 200);
        double prizePerKilometer = eventItemCalculatePrizeProcessor.getPrizePerKilometer(event);
        System.out.println(prizePerKilometer);
        Assert.assertEquals(20.0d, prizePerKilometer, 0.0d);
    }
}
