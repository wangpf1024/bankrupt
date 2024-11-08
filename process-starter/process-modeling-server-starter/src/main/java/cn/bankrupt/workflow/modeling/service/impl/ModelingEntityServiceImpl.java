package cn.bankrupt.workflow.modeling.service.impl;

import cn.bankrupt.workflow.modeling.service.*;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.ResultBean;
import cn.bankrupt.workflow.enums.*;
import cn.bankrupt.workflow.modeling.domain.params.ModelingEntityExt;
import cn.bankrupt.workflow.modeling.domain.params.ModelingFieldDefExt;
import cn.bankrupt.workflow.modeling.entity.ModelingEntityDef;
import cn.bankrupt.workflow.modeling.entity.ModelingEntityField;
import cn.bankrupt.workflow.modeling.entity.ModelingFieldDef;
import cn.bankrupt.workflow.modeling.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ModelingEntityServiceImpl implements ModelingEntityService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ModelingEntityDefService modelingEntityDefService;

    @Autowired
    ModelingFieldDefService modelingFieldDefService;

    @Autowired
    ModelingEntityFieldService modelingEntityFieldService;

    @Autowired
    FormService formService;

    @Override
    @Transactional
    public ResultBean<Void> addForm(ModelingEntityExt param) {

        if (param.getMKey() == null) {
            return ResultBean.ofError( "标识不可为空");
        }

        long i = modelingEntityDefService.count(Wrappers.<ModelingEntityDef>lambdaQuery()
                .eq(ModelingEntityDef::getMKey, param.getMKey()));

        if (i  > 0) {
            return ResultBean.ofError( param.getMKey() + "标识已存在");
        }

        ModelingEntityDef entity = new ModelingEntityDef();
        BeanUtils.copyProperties(param, entity);
        entity.setStatus(0);

        modelingEntityDefService.save(entity);

        formService.createFormTable(entity);

        return ResultBean.ofSuccess();
    }

    @Override
    public ResultBean<Void> addField(ModelingFieldDefExt param) {
        if (param.getMKey() == null) {
            return ResultBean.ofError( "标识不可为空");
        }
        ModelingFieldDef modelingField = new ModelingFieldDef();
        BeanUtils.copyProperties(param,modelingField);
        modelingField.setScope(FieldScope.ENTITY_PRIVATE.name());

        modelingFieldDefService.save(modelingField);

        ModelingEntityField entityField = new ModelingEntityField();

        entityField.setMKey(param.getMKey());
        entityField.setFieldId(modelingField.getId());

        modelingEntityFieldService.save(entityField);

        formService.alterFormTableColumn(param);

        return ResultBean.ofSuccess();
    }

    @Override
    public ResultBean<Void> updateField(ModelingFieldDefExt param) {

        ModelingFieldDef originalField = modelingFieldDefService.getById(param.getId());
        if (originalField == null) {
            return ResultBean.ofError( "字段不存在");
        }

        ModelingFieldDef modelingField = new ModelingFieldDef();
        BeanUtils.copyProperties(param,modelingField);

        modelingFieldDefService.updateById(modelingField);

        // 宽度变化 更新表定义
        if (!param.getWidth().equals(originalField.getWidth())) {
            // 查询引用此字段的 相关模型
            List<ModelingEntityField> modelingFieldRefs = modelingEntityFieldService.list(Wrappers.<ModelingEntityField>lambdaQuery()
                    .eq(ModelingEntityField::getFieldId, param.getId()));

            for (ModelingEntityField modelingFieldRef : modelingFieldRefs) {
                ModelingFieldDef def =  modelingFieldDefService.getById(modelingFieldRef.getId());
                ModelingFieldDefExt ext = new ModelingFieldDefExt();
                BeanUtils.copyProperties(def,ext);
                ext.setMKey(modelingFieldRef.getMKey());
                formService.modifyFormTableColumn(ext);
            }
        }
        return ResultBean.ofSuccess();
    }

    @Override
    public ResultBean<Void> deleteField(Long id) {
        ModelingFieldDef originalField = modelingFieldDefService.getById(id);
        if (originalField == null) {
            return ResultBean.ofError( "字段不存在");
        }
        if (originalField.getScope().equals(FieldScope.ENTITY_DEFAULT.name())) {
            return ResultBean.ofError( "禁止删除默认字段");
        }

        modelingFieldDefService.removeById(id);

        if (originalField.getScope().equals(FieldScope.ENTITY_PRIVATE.name())) {

            // 私有字段不可能重复
            ModelingEntityField modelingFieldRef = modelingEntityFieldService.getOne(Wrappers.<ModelingEntityField>lambdaQuery().eq(ModelingEntityField::getFieldId, id));

            modelingEntityFieldService.remove(Wrappers.<ModelingEntityField>lambdaQuery().eq(ModelingEntityField::getFieldId, id));

            ModelingFieldDefExt ext = new ModelingFieldDefExt();
            BeanUtils.copyProperties(originalField,ext);
            ext.setMKey(modelingFieldRef.getMKey());

            formService.dropFormTableColumn(ext);

        }
        return ResultBean.ofSuccess();
    }

    @Override
    public ResultBean<List<ModelingFieldDef>> getTableColumnList(String mKey) {
        List<ModelingEntityField> modelingFieldRefs = modelingEntityFieldService.list(Wrappers.<ModelingEntityField>lambdaQuery().eq(ModelingEntityField::getMKey, mKey));
        List<ModelingFieldDef> defaultEntityFields = modelingFieldDefService.list(Wrappers.<ModelingFieldDef>lambdaQuery()
                .eq(ModelingFieldDef::getScope, FieldScope.ENTITY_DEFAULT));
        modelingFieldRefs.forEach( i ->{
            ModelingFieldDef def  = modelingFieldDefService.getById(i.getFieldId());
            defaultEntityFields.add(def);
        });
        return ResultBean.ofSuccess(defaultEntityFields);
    }

    @Override
    public ResultBean<String> checkMKey(String mKey) {
        long i = modelingEntityDefService.count(Wrappers.<ModelingEntityDef>lambdaQuery()
                .eq(ModelingEntityDef::getMKey, mKey));

        if (i  > 0) {
            return ResultBean.ofError( mKey + "标识已存在");
        }
        return ResultBean.ofSuccess(mKey);
    }
}
