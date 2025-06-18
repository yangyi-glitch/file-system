package com.minio.console.enums;

public enum ContentTypeEnums {
    PDF("pdf", "application/pdf"),
    DOC("doc", "application/msword"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLS("xls", "application/vnd.ms-excel"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    PPT("ppt", "application/vnd.ms-powerpoint"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");

    private String suffix;
    private String contentType;

    ContentTypeEnums(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public static String getContentType(String suffix) {
        for (ContentTypeEnums value : ContentTypeEnums.values()) {
            if (value.suffix.equals(suffix)) {
                return value.contentType;
            }
        }
        return null;
    }
}
