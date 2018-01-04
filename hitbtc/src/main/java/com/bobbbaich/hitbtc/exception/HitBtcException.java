package com.bobbbaich.hitbtc.exception;

public class HitBtcException extends Exception {
    public HitBtcException() {
        super();
    }

    public HitBtcException(String message) {
        super(message);
    }

    public HitBtcException(String message, Throwable cause) {
        super(message, cause);
    }

    public HitBtcException(Throwable cause) {
        super(cause);
    }

    protected HitBtcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
