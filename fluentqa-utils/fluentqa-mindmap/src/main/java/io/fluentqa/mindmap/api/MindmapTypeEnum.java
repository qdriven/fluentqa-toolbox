package io.fluentqa.mindmap.api;

public enum MindmapTypeEnum {
    FREEMIND("mm"),  //FREEMIND file
    XMIND("xmind") //XMIND file
    ;

    MindmapTypeEnum(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    private String fileSuffix;

    public String getFileSuffix() {
        return fileSuffix;
    }
}
