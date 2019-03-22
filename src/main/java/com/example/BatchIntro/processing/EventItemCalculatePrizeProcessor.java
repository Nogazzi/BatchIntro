package com.example.BatchIntro.processing;

import com.example.BatchIntro.model.InputRunningEvent;
import com.example.BatchIntro.model.OutputRatedRunningEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class EventItemCalculatePrizeProcessor implements ItemProcessor<InputRunningEvent, OutputRatedRunningEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EventItemCalculatePrizeProcessor.class);

    @Override
    public OutputRatedRunningEvent process(InputRunningEvent inputRunningEvent) throws Exception {
        double prizePerKilometer = getPrizePerKilometer(inputRunningEvent);
//        double eventRate = getEventRate(inputRunningEvent);
        OutputRatedRunningEvent outputRatedRunningEvent = new OutputRatedRunningEvent(inputRunningEvent, prizePerKilometer, 0.0d);
        logger.info("Converted: " + inputRunningEvent + " into: " + outputRatedRunningEvent);
        return outputRatedRunningEvent;
    }

    public double getEventRate(InputRunningEvent runningEvent) {
        return 100.0d - runningEvent.getPrize()/runningEvent.getDistance();
    }


    public double getPrizePerKilometer(InputRunningEvent runningEvent) {
        return runningEvent.getPrize()/runningEvent.getDistance();
    }
}
