package com.bobbbaich.hitbtc.batch;

import com.bobbbaich.hitbtc.model.Symbol;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SymbolItemProcessor implements ItemProcessor<Symbol, Symbol> {

    @Override
    public Symbol process(Symbol item) throws Exception {
        return null;
    }
}
