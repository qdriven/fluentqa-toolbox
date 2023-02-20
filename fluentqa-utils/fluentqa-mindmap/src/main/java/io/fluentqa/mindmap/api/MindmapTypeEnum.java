package io.fluentqa.mindmap.api;

import io.fluentqa.qabox.io.FileUtils;

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


    public MindmapTypeEnum parse(String fileName){
        String suffix = FileUtils.getSuffix(fileName);
        for (MindmapTypeEnum value : values()) {
            if(value.getFileSuffix().equalsIgnoreCase(suffix)){
                return value;
            }
        }
        return XMIND;
    }
}
