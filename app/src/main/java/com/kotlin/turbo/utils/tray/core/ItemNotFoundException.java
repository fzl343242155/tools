package com.kotlin.turbo.utils.tray.core;


public class ItemNotFoundException extends TrayException {

    public ItemNotFoundException() {
    }

    public ItemNotFoundException(final String detailMessage) {
        super(detailMessage);
    }

    public ItemNotFoundException(final String detailMessage, final Object... args) {
        super(detailMessage, args);
    }

    public ItemNotFoundException(final String detailMessage, final Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ItemNotFoundException(final Throwable throwable) {
        super(throwable);
    }
}
