package com.bobbbaich.hitbtc.batch;

import com.bobbbaich.hitbtc.model.Symbol;
import com.bobbbaich.hitbtc.service.market.MarketService;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.kurento.jsonrpc.message.Response;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SymbolItemReader implements ItemStreamReader<Symbol> {

    private MarketService publicService;

    @Override
    public Symbol read() throws Exception {
        Response<JsonElement> symbols = publicService.getSymbols();
        log.debug("{}", symbols.getResult());

        Response<JsonElement> symbol = publicService.getSymbol("ETHBTC");
        log.debug("{}", symbol.getResult());
        return new Symbol();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        log.debug("open");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        log.debug("update");
    }

    @Override
    public void close() throws ItemStreamException {
        log.debug("close");
    }

    @Autowired
    public void setPublicService(MarketService publicService) {
        this.publicService = publicService;
    }
}
