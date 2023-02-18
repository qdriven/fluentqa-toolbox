package io.fluentqa.mindmap.api;

import java.util.List;

public interface MindMapTransformer<T> {

    public List<MindMapPath<T>> convertFromFileContent(String mindMapContent);
    public List<MindMapPath<T>> convertFromFilePath(String mindMapFilePath);

}
