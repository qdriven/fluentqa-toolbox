package io.fluentqa.mindmap.freemind;

import io.fluentqa.mindmap.api.MindMapLevelRecord;
import io.fluentqa.mindmap.api.MindMapPath;
import io.fluentqa.mindmap.freemind.model.Node;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;


public class FreeMindConverterTest {
    @BeforeEach
    public void setup(){

    }
    @org.junit.jupiter.api.Test
    void testToList() {
        FreeMindTransformer converter = new FreeMindTransformer();
        List<MindMapPath<Node>> result =  converter.convertFromFileContent("./t2.mm");
        System.out.println(result);

        List<MindMapLevelRecord> records = MindMapLevelRecord.fromMindMapPaths(
                result, Node::getTEXT
        );
        System.out.println(records);
    }

}