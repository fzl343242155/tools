package com.kotlin.turbo.utils;

/**
 * 文件名：ReflectException
 * 作者：Turbo
 * 时间：2019-09-26 15:20
 * 开心对待每一天，真心对待每一个
 */
public class ReflectException extends RuntimeException {

    private static final long serialVersionUID = -6654702552823551870L;

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException() {
        super();
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
