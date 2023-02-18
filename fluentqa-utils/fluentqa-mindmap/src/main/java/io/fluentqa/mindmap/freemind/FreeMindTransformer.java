package io.fluentqa.mindmap.freemind;

import io.fluentqa.mindmap.api.MindMapPath;
import io.fluentqa.mindmap.api.MindMapTransformer;
import io.fluentqa.mindmap.freemind.model.Map;
import io.fluentqa.mindmap.freemind.model.Node;
import io.fluentqa.qabox.xml.XmlUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. READ FreeMind Files
 * 2. Convert
 */
public class FreeMindTransformer implements MindMapTransformer<Node> {


    @Override
    public List<MindMapPath<Node>> convertFromFileContent(String mindMapContent) {
        return convertToList(mindMapContent);
    }

    @Override
    public List<MindMapPath<Node>> convertFromFilePath(String mindMapFilePath) {
        return convertToList(mindMapFilePath);
    }

    private List<MindMapPath<Node>> convertToList(String filePathOrContent) {
        Map freeMindMap = XmlUtils.readXmlToObject(XmlUtils.readXML(filePathOrContent), Map.class);
        FreeMindNode root = new FreeMindNode(freeMindMap.getNode());
        List<MindMapPath<Node>> result = new ArrayList<>();
        populateNodes(List.of(root), result);
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
    private List<MindMapPath<Node>> populateNextLevel(MindMapPath<Node> current) {
        Node lastNode = current.pathNodes().getLast();
        List<MindMapPath<Node>> mindMapPaths = new ArrayList<>();
        for (Object o : lastNode.getArrowlinkOrAttributeOrAttributeLayout()) {
            MindMapPath<Node> path = current.clone();
            if (o instanceof Node) {
                path.pathNodes().add((Node) o);
            }
            mindMapPaths.add(path);
        }

        return mindMapPaths;
    }


    private void populateNodes(List<MindMapPath<Node>> nodes, List<MindMapPath<Node>> resultPaths) {

        for (MindMapPath<Node> current : nodes) {
            if (current.pathNodes().getLast().getArrowlinkOrAttributeOrAttributeLayout().size() == 0) {
                resultPaths.add(current);
            } else {
                populateNodes(populateNextLevel(current), resultPaths);
            }
        }
    }
}
