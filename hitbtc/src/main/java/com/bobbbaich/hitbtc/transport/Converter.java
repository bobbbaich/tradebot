package com.bobbbaich.hitbtc.transport;

public interface Converter<C> {
    <F> C from(F src, Class<F> type);

    <T> T to(C src, Class<T> type);
}