package com.pansome.workflow.modeling.service;


import com.pansome.workflow.modeling.domain.params.ModelingFieldDefExt;
import com.pansome.workflow.modeling.entity.ModelingEntityDef;
import com.pansome.workflow.modeling.entity.ModelingFieldDef;

public interface FormService {


    /***
     * 创建form表
     * @param entity
     */
    void createFormTable(ModelingEntityDef entity);

    /**
     * 创建form表列
     * @param param
     */
    void alterFormTableColumn(ModelingFieldDefExt param);

    /**
     * 修改form表列
     * @param param
     */
    void modifyFormTableColumn(ModelingFieldDefExt param);

    /**
     * 删除form表列
     * @param param
     */
    void dropFormTableColumn(ModelingFieldDefExt param);

    /**
     * 增加数据
     * @param sql
     */
    void insertDML(String sql);
}
