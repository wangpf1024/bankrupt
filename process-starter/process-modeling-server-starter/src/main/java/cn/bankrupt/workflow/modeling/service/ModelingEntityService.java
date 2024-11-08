package cn.bankrupt.workflow.modeling.service;

import cn.bankrupt.workflow.modeling.domain.params.ModelingEntityExt;
import cn.bankrupt.workflow.modeling.domain.params.ModelingFieldDefExt;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.modeling.entity.ModelingFieldDef;

import java.util.List;

public interface ModelingEntityService {
    /**
     * 创建实体信息同时建表
     * @param param
     * @return
     */
    ResultBean<Void> addForm(ModelingEntityExt param);

    /**
     * 新增字段
     * @param param
     * @return
     */
    ResultBean<Void> addField(ModelingFieldDefExt param);

    /**
     * 更新字段
     * @param param
     * @return
     */
    ResultBean<Void> updateField(ModelingFieldDefExt param);

    /**
     * 删除字段
     * @param id
     * @return
     */
    ResultBean<Void> deleteField(Long id);

    /**
     * 查询表单列信息
     * @param mKey
     * @return
     */
    ResultBean<List<ModelingFieldDef>> getTableColumnList(String mKey);

    /**
     * 检查表命名
     * @param mKey
     * @return
     */
    ResultBean<String> checkMKey(String mKey);
}
