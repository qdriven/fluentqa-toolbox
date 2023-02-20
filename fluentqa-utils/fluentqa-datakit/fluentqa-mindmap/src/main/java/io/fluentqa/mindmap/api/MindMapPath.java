package io.fluentqa.mindmap.api;

import io.fluentqa.mindmap.freemind.FreeMindNode;
import io.fluentqa.mindmap.freemind.model.Node;
import lombok.Data;

import java.util.LinkedList;

@Data
public class MindMapPath<T> implements Cloneable {
    private T root;

    private LinkedList<T> pathNodes = new LinkedList<>();

    public MindMapPath(T root) {
        this.root = root;
        this.pathNodes.add(root);
    }

    public T root() {
        return root;
    }

    public LinkedList<T> pathNodes() {
        return this.pathNodes;
    }

    @Override
    public MindMapPath<T> clone() {
        MindMapPath<T> path = new MindMapPath<T>(this.getRoot());
        path.setPathNodes((LinkedList<T>) this.pathNodes.clone());
        return path;
    }
}
