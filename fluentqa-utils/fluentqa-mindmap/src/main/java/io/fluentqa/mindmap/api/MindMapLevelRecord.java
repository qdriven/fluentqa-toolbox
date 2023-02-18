package io.fluentqa.mindmap.api;

import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.function.Function;


@Data
public class MindMapLevelRecord {
    private Map<Integer, String> mindMapLevels = new HashMap<>();
    private Integer currentLevel = 0;

    private synchronized void increaseTreeLevel() {
        this.currentLevel++;
    }

    public void add(String value) {
        increaseTreeLevel();
        mindMapLevels.put(this.currentLevel, value);
    }

    public Map<Integer, String> getMindMapLevels() {
        return mindMapLevels;
    }

    public static <T> MindMapLevelRecord fromMindMapPath(MindMapPath<T> path,
                                                         Function<T, String> extractValueFunc) {
        MindMapLevelRecord record = new MindMapLevelRecord();
        for (T pathNode : path.getPathNodes()) {
            record.add(extractValueFunc.apply(pathNode));
        }
        return record;
    }

    public static <T> List<MindMapLevelRecord> fromMindMapPaths(List<MindMapPath<T>> paths,
                                                         Function<T, String> extractValueFunc) {
        List<MindMapLevelRecord> records = new ArrayList<>();
        paths.forEach((t)->records.add(fromMindMapPath(t,extractValueFunc)));
        return records;
    }
}
