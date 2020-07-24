package com.xiaoanjujia.common.model.http;

/**
 * Created by xp on 2017/3/10.
 */

public class NoNetWorkException extends RuntimeException {
    public NoNetWorkException() {
    }

    public NoNetWorkException(String message) {
        super(message);
    }
}
