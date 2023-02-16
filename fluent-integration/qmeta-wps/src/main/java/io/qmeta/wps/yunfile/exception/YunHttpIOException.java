package io.qmeta.wps.yunfile.exception;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class YunHttpIOException extends YunException {

    private boolean bSocketTimeout = false;
    private boolean bSocket = false;
    private boolean bConnect = false;


    /**
     * eg:
     * java.net.UnknownHostException
     * java.net.ConnectException
     * java.net.SocketTimeoutException
     * java.net.SocketException
     *
     * @param e
     */
    public YunHttpIOException(IOException e) {
        super(e);
        if (e instanceof SocketTimeoutException)
            bSocketTimeout = true;
        else if (e instanceof ConnectException)
            bConnect = true;
        else if (e instanceof SocketException)
            bSocket = true;
    }

    @Override
    public boolean needRetry() {
        return true;
    }

    @Override
    public boolean isSocketTimeoutException() {
        return bSocketTimeout;
    }

    @Override
    public boolean isSocketException() {
        return bSocket;
    }

    @Override
    public boolean isConnectException() {
        return bConnect;
    }
}
