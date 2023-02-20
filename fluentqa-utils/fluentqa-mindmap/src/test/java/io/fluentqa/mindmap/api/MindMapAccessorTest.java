package io.fluentqa.mindmap.api;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MindMapAccessorTest {
    MindMapAccessor accessor = new MindMapAccessor();
    @Test
    public void testMindMapAccessorTest(){
        String xmindFile = "./Xmind10+.xmind";
        String freeMindfile = "./元件管理用例.mm";
        List<DemoBean> demos = accessor.readMindMapToBean(xmindFile,DemoBean.class);
        List<DemoBean> freeMindBeans = accessor.readMindMapToBean(freeMindfile,DemoBean.class);
        System.out.println(demos);
        System.out.println(freeMindBeans);
    }
}
