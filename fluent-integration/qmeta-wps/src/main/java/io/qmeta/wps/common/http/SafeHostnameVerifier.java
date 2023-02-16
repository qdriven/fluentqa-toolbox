package io.qmeta.wps.common.http;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.Collections;
import java.util.List;


public class SafeHostnameVerifier implements HostnameVerifier {
    private final List<String> hostWhiteList = Collections.singletonList("localhost");

    @Override
    public boolean verify(String hostname, SSLSession session) {

        for (String host : hostWhiteList) {
            if (host.equalsIgnoreCase(hostname)) {
                return true;
            }
        }
        return false;
    }
}
