package io.fluentqa.qabox.bytedance.common.error;

/**
 * @Authorn
 * @date 2020/07/23
 **/
public class InvalidAuthorizerRefreshToken extends RuntimeException {

    public InvalidAuthorizerRefreshToken() {
    }

    public InvalidAuthorizerRefreshToken(String message) {
        super(message);
    }
}
