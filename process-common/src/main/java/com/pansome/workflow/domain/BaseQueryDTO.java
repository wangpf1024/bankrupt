package com.pansome.workflow.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseQueryDTO implements Serializable {

    private Integer pageIndex =1;

    private Integer pageSize =10;
}
