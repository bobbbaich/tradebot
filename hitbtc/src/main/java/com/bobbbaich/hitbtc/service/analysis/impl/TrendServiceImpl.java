package com.bobbbaich.hitbtc.service.analysis.impl;

import com.bobbbaich.hitbtc.model.MovingAverage;
import com.bobbbaich.hitbtc.model.Ticker;
import com.bobbbaich.hitbtc.repository.TickerRepository;
import com.bobbbaich.hitbtc.service.analysis.TrendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrendServiceImpl implements TrendService {
    private final TickerRepository tickerRepository;

    @Override
    public void movingAvgSignal(MovingAverage mAvg) {
        Double exp = mAvg.getExponential();
        Double simple = mAvg.getSimple();

        if (Math.abs(exp - simple) > 0.000001) {
            log.debug("exp - simple = {} --- timestamp: {}", exp - simple, mAvg.getTimestamp());
            Ticker tickerByMovingAverageId = tickerRepository.findTickerByMovingAverageId(mAvg.getId());
            log.debug("tickerByMovingAverageId: {}", tickerByMovingAverageId);
        }
    }
}