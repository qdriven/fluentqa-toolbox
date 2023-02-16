package io.qmeta.wps.yunfile.exception;

public class YunHtmlException extends YunResultException {

    public YunHtmlException(String htmlstring, int code) {
        super(null, htmlstring, code, null);
    }

    @Override
    public boolean needRetry() {
        String result = getResult();
        return (result == null || result.length() == 0) ? true : false;
    }
}
