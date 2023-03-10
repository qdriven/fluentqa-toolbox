package io.fluentqa.qabox.bytedance.common.enums;

/**
 * 字节跳动各宿主端对应关系
 * @Authorn
 * @date 2020/09/23
 **/
public enum HostEnum {

    今日头条("toutiao"),
    今日头条极速版("tt_lite"),
    抖音("douyin"),
    西瓜视频("xigua"),
    抖音火山版("huoshan");

    private String enName;

    HostEnum(String enName) {
        this.enName = enName;
    }
}
