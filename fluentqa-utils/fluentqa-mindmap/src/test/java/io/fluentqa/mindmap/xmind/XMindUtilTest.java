package io.fluentqa.mindmap.xmind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMindUtilTest {

    @Test
    void readXMindFile() {
        XmindRawData data = XMindUtil.readXMindFile("./XmindZen.xmind");
        System.out.println(data);
    }
}