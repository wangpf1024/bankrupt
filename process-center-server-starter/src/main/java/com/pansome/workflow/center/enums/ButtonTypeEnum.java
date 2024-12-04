package com.pansome.workflow.center.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum ButtonTypeEnum {

    TODO("TODO","待办按钮"),
    DONE("DONE","已办按钮"),
    PROCESS("PROCESS","流程按钮");

    @EnumValue
    private String key;

    @JsonValue
    private String display;

    ButtonTypeEnum(String key, String display) {
        this.key = key;
        this.display = display;
    }

    public static ButtonTypeEnum getButtonTypeEnumByKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        for (ButtonTypeEnum item : ButtonTypeEnum.values()) {
            if (key.equals(item.getKey()) ) {
                return item;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
