package io.fluentqa.mindmap.xmind;

import io.fluentqa.mindmap.api.MindMapLevelRecord;
import io.fluentqa.mindmap.api.MindMapPath;
import io.fluentqa.mindmap.xmind.model.Attached;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class XmindTransformerTest {
    XmindTransformer xmindTransformer = new XmindTransformer();

    @Test
    void convertFromFileContent() {
    }

    @Test
    void convertFromFilePath() {
        List<MindMapPath<Attached>> result = xmindTransformer.convertFromFilePath("./XmindZen.xmind");
        System.out.println(result);
        List<MindMapLevelRecord> t = MindMapLevelRecord.fromMindMapPaths(result, Attached::getTitle);
        System.out.println(t);
    }
}