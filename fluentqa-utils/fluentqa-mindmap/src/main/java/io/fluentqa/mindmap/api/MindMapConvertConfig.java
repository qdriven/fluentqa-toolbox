package io.fluentqa.mindmap.api;

import lombok.Data;

import java.util.List;

@Data
public class MindMapConvertConfig {
    private List<LevelConfig> configs;

    @Data
    public static class LevelConfig{
        private String key;
        private Integer level;
    }
}
