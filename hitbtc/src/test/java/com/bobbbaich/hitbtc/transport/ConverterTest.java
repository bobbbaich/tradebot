//package com.bobbbaich.hitbtc.transport;
//
//import com.bobbbaich.hitbtc.model.Symbol;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ConverterTest {
//    @Autowired
//    private Converter<JsonElement> converter;
//
//    private Symbol src;
//    private JsonObject json;
//
//    @Before
//    public void setUp() throws Exception {
//        src = getSymbol();
//        json = getJsonSymbol();
//    }
//
//    @Test
//    public void from() {
//        JsonElement actual = converter.from(src, Symbol.class);
//        System.out.println(actual);
//        Assert.assertEquals(json, actual);
//    }
//
//    @Test
//    public void to() {
//        Symbol actual = converter.to(json, Symbol.class);
//        System.out.println(actual);
//        Assert.assertEquals(src, actual);
//    }
//
//
//    private Symbol getSymbol() {
//        Symbol symbol = new Symbol();
//
//        symbol.setId("ETHBTC");
//        symbol.setBaseCurrency("ETH");
//        symbol.setQuoteCurrency("ETH");
//        symbol.setQuantityIncrement("BTC");
//        symbol.setTickSize("0.001");
//        symbol.setTakeLiquidityRate("0.000001");
//        symbol.setProvideLiquidityRate("-0.0001");
//        symbol.setFeeCurrency("BTC");
//
//        return symbol;
//    }
//
//    private JsonObject getJsonSymbol() {
//        JsonObject jsonSymbol = new JsonObject();
//
//        jsonSymbol.addProperty("id", "ETHBTC");
//        jsonSymbol.addProperty("baseCurrency", "ETH");
//        jsonSymbol.addProperty("quoteCurrency", "ETH");
//        jsonSymbol.addProperty("quantityIncrement", "BTC");
//        jsonSymbol.addProperty("tickSize", "0.001");
//        jsonSymbol.addProperty("takeLiquidityRate", "0.000001");
//        jsonSymbol.addProperty("provideLiquidityRate", "-0.0001");
//        jsonSymbol.addProperty("feeCurrency", "BTC");
//
//        return jsonSymbol;
//    }
//}