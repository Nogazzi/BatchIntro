package com.example.BatchIntro.processing;

import com.example.BatchIntro.model.PrizedRunningEvent;
import com.example.BatchIntro.model.RatedRunningEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class EventItemRatingProcessor implements ItemProcessor<PrizedRunningEvent, RatedRunningEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EventItemRatingProcessor.class);

    @Override
    public RatedRunningEvent process(PrizedRunningEvent inputEvent) {
        RatedRunningEvent ratedRunningEvent = new RatedRunningEvent(
                inputEvent.getName(),
                inputEvent.getDistance(),
                inputEvent.getPrize(),
                inputEvent.getPrizePerKm(),
                getEventRate(inputEvent.getPrize(), inputEvent.getDistance()));
        logger.info("Converted: " + inputEvent + " into: " + ratedRunningEvent);
        return ratedRunningEvent;
    }

    public double getEventRate(double prize, double distance) {
        return 100.0d - prize/distance;
    }
}
