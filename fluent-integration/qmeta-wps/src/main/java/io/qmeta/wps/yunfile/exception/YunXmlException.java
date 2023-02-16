package io.qmeta.wps.yunfile.exception;

public class YunXmlException extends YunResultException {

    public YunXmlException(String result, String xmlstring, int code) {
        super(result, xmlstring, code, null);
    }

    @Override
    public boolean needRetry() {
        String result = getResult();
        return (result == null || result.length() == 0) ? true : false;
    }
}
