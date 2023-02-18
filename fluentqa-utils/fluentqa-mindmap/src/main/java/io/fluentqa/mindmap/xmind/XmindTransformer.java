package io.fluentqa.mindmap.xmind;

import cn.hutool.json.JSONUtil;
import io.fluentqa.mindmap.api.MindMapPath;
import io.fluentqa.mindmap.api.MindMapTransformer;
import io.fluentqa.mindmap.xmind.model.Attached;
import io.fluentqa.mindmap.xmind.model.JsonRootBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. READ XMIND Files
 * 2. Convert to List
 */
public class XmindTransformer implements MindMapTransformer<Attached> {


    @Override
    public List<MindMapPath<Attached>> convertFromFileContent(String mindMapContent) {
        return convertToList(mindMapContent);
    }

    @Override
    public List<MindMapPath<Attached>> convertFromFilePath(String mindMapFilePath) {
        return convertToList(mindMapFilePath);
    }

    private List<MindMapPath<Attached>> convertToList(String filePathOrContent) {
        XmindRawData rawData = XMindUtil.readXMindFile(filePathOrContent);
        List<JsonRootBean> root = JSONUtil.toList(rawData.getContentJson(), JsonRootBean.class);
        Attached rootNode = root.get(0).getRootTopic().getChildren().getAttached().get(0);
        XMindNode xmindNode = new XMindNode(rootNode);
        List<MindMapPath<Attached>> result = new ArrayList<>();
        populateNodes(List.of(xmindNode), result);
        return result;
    }

    /**
     * ->Node1
     * currentNode |
     * ->Node2
     * convert to:
     * [ currentNode->Node1,
     * currentNode->Node2]
     *
     * @param current: Current Node
     * @return OUT: A List of MindMapPath
     */
    private List<MindMapPath<Attached>> populateNextLevel(MindMapPath<Attached> current) {
        Attached lastNode = current.pathNodes().getLast();
        List<MindMapPath<Attached>> mindMapPaths = new ArrayList<>();
        for (Attached o : lastNode.getChildren().getAttached()) {
            MindMapPath<Attached> path = current.clone();
            if (o != null) {
                path.pathNodes().add(o);
            }
            mindMapPaths.add(path);
        }

        return mindMapPaths;
    }


    private void populateNodes(List<MindMapPath<Attached>> nodes, List<MindMapPath<Attached>> resultPaths) {

        for (MindMapPath<Attached> current : nodes) {
            if (current.pathNodes().getLast().getChildren() == null) {
                resultPaths.add(current);
            } else {
                populateNodes(populateNextLevel(current), resultPaths);
            }
        }
    }
}
