package com.pansome.workflow.enums;


public enum ModelingModule {
    ENTITY;
    public static String buildEntityTableName(String key) {
        return "ps_" + key;
    }
}