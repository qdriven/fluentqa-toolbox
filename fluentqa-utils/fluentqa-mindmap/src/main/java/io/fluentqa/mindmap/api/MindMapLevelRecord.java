package io.fluentqa.mindmap.api;

import cn.hutool.core.bean.BeanUtil;
import io.fluentqa.qabox.ReflectionUtils;
import lombok.Data;

import java.lang.reflect.Field;
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
        paths.forEach((t) -> records.add(fromMindMapPath(t, extractValueFunc)));
        return records;
    }

    public <T> T toBean(Class<T> clazz, MindMapLevelConfig config) {
        T instance = ReflectionUtils.newInstance(clazz);
        for (MindMapLevelConfig.LevelConfig levelConfig : config.getConfigs()) {
            BeanUtil.setFieldValue(instance, levelConfig.getKey(), this.mindMapLevels.get(levelConfig.getLevel()));
        }
        return instance;
    }

    public <T> T toBean(Class<T> clazz) {
        T instance = ReflectionUtils.newInstance(clazz);
        Field[] fields = ReflectionUtils.getFields(clazz);
        for (Field field : fields) {
            NodeLevel a = field.getAnnotation(NodeLevel.class);
            if (a != null) {
                ReflectionUtils.setFieldValue(instance, field, this.mindMapLevels.get(a.value()));
            }
        }
        return instance;
    }
}
