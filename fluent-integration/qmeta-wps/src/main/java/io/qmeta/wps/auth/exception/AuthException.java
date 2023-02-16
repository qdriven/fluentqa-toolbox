package io.qmeta.wps.auth.exception;

public class AuthException extends RuntimeException {

    public AuthException() {

    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Exception e) {
        super(e);
    }

    public AuthException(String message, Exception e) {
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
