package io.qmeta.wps.yunfile.exception;

public class YunException extends Exception {

    public YunException() {

    }

    public YunException(String message) {
        super(message);
    }

    public YunException(Exception e) {
        super(e);
    }

    public YunException(String message, Exception e) {
        super(message, e);
    }

    public int getCode() {
        return 0;
    }

    public String getResult() {
        return null;
    }

    public boolean needRetry() {
        return false;
    }

    public boolean isSocketTimeoutException() {
        return false;
    }

    public boolean isConnectTimeoutException() {
        return false;
    }

    public boolean isSocketException() {
        return false;
    }

    public boolean isConnectException() {
        return false;
    }

    public boolean isCancelException() {
        return false;
    }

    public String getSimpleName() {
        if (super.getCause() != null) {
            return super.getCause().getClass().getSimpleName();
        }
        return getClass().getSimpleName();
    }
}
