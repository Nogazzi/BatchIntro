package com.example.BatchIntro;

import com.example.BatchIntro.model.BaseRunningEvent;
import com.example.BatchIntro.processing.EventItemCalculatePrizeProcessor;
import org.junit.Assert;
import org.junit.Test;

public class EventItemCalculatePrizeProcessorTestClass {

    EventItemCalculatePrizeProcessor eventItemCalculatePrizeProcessor = new EventItemCalculatePrizeProcessor();


    @Test
    public void getPrizePerKilometer() {
        BaseRunningEvent event = new BaseRunningEvent("ABC", 10, 200);
        double prizePerKilometer = eventItemCalculatePrizeProcessor.getPrizePerKilometer(event);
        System.out.println(prizePerKilometer);
        Assert.assertEquals(20.0d, prizePerKilometer, 0.0d);
    }
}
