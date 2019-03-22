package com.example.BatchIntro.processing;

import com.example.BatchIntro.model.BaseRunningEvent;
import com.example.BatchIntro.model.PrizedRunningEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class EventItemCalculatePrizeProcessor implements ItemProcessor<BaseRunningEvent, PrizedRunningEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EventItemCalculatePrizeProcessor.class);

    @Override
    public PrizedRunningEvent process(BaseRunningEvent baseRunningEvent) throws Exception {
        double prizePerKilometer = getPrizePerKilometer(baseRunningEvent);
//        double eventRate = getEventRate(baseRunningEvent);
        PrizedRunningEvent prizedRunningEvent = new PrizedRunningEvent(baseRunningEvent, prizePerKilometer);
        logger.info("Converted: " + baseRunningEvent + " into: " + prizedRunningEvent);
        return prizedRunningEvent;
    }


    public double getPrizePerKilometer(BaseRunningEvent runningEvent) {
        return runningEvent.getPrize()/runningEvent.getDistance();
    }
}
