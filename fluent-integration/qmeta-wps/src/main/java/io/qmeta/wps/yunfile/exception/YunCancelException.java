package io.qmeta.wps.yunfile.exception;

public class YunCancelException extends YunException {

    public YunCancelException(String message) {
        super(message);
    }

    @Override
    public boolean isCancelException() {
        return true;
    }
}
