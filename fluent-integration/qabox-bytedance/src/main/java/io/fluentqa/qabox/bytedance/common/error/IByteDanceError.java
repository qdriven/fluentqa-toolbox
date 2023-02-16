package io.fluentqa.qabox.bytedance.common.error;

/**
 * @Authorn
 * @date 2021/06/19
 **/
public interface IByteDanceError {

    /**
     * 获取错误编码
     * @return
     */
    Integer errorCode();

    /**
     * 获取错误信息
     * @return
     */
    String errorMessage();

    Boolean checkSuccess();
}
