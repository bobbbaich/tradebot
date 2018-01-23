package com.bobbbaich.hitbtc.exception;

public class TradeRuntimeException extends RuntimeException {
    public TradeRuntimeException() {
        super();
    }

    public TradeRuntimeException(String message) {
        super(message);
    }

    public TradeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeRuntimeException(Throwable cause) {
        super(cause);
    }

    protected TradeRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
