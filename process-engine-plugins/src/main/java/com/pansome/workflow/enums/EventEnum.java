package com.pansome.workflow.enums;

public enum EventEnum {

    create("create"),
    complete("complete"),
    assignment("assignment");

    private String name;

    EventEnum(String event) {
        this.name = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
