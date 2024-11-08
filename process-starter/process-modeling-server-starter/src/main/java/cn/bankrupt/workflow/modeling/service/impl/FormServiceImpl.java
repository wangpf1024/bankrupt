package cn.bankrupt.workflow.modeling.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bankrupt.workflow.enums.FieldScope;
import cn.bankrupt.workflow.enums.ModelingModule;
import cn.bankrupt.workflow.modeling.domain.dto.FieldScheme;
import cn.bankrupt.workflow.modeling.domain.params.ModelingFieldDefExt;
import cn.bankrupt.workflow.modeling.entity.ModelingEntityDef;
import cn.bankrupt.workflow.modeling.entity.ModelingFieldDef;
import cn.bankrupt.workflow.modeling.mapper.ModelingEntityDefExtMapper;
import cn.bankrupt.workflow.modeling.service.FormService;
import cn.bankrupt.workflow.modeling.service.ModelingFieldDefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormServiceImpl implements FormService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ModelingFieldDefService modelingFieldDefService;

    @Autowired
    ModelingEntityDefExtMapper modelingEntityDefExtMapper;

    private final static String COLUMN_TEMPLATE =
            "`%s` " +
                    "%s " +
                    "%s " +
                    "%s " +
                    "COMMENT " +
                    "'%s'";

    @Override
    public void createFormTable(ModelingEntityDef entity) {
        List<ModelingFieldDef> defaultEntityFields = modelingFieldDefService.list(Wrappers.<ModelingFieldDef>lambdaQuery()
                .eq(ModelingFieldDef::getScope, FieldScope.ENTITY_DEFAULT));

        List<String> columnDefs = new ArrayList<>();

        Set<String> notNullFields = new HashSet<>(Arrays.asList("id"));

        String primaryField = "id";

        for (ModelingFieldDef field : defaultEntityFields) {
            FieldScheme scheme = FieldScheme.getFieldScheme(field);
            String dbType =  FieldScheme.getDbType(field.getWidth(), scheme);
            String nullable = "DEFAULT NULL";
            String primaryKey = "";
            if (primaryField.equals(field.getName())) {
                primaryKey = "AUTO_INCREMENT";
            }
            if (notNullFields.contains(field.getName())) {
                nullable = "NOT NULL";
            }
            String columnDef = String.format(COLUMN_TEMPLATE,
                    field.getName(),
                    dbType,
                    nullable,
                    primaryKey,
                    field.getLabel());
            columnDefs.add(columnDef);
        }

        String tableName = ModelingModule.buildEntityTableName(entity.getMKey());

        // TODO: 防止SQL注入
        String createDDL = columnDefs.stream().collect(
                Collectors.joining(",\n",
                        "CREATE TABLE `" + tableName + "` (\n"  ,
                        ", \n PRIMARY KEY (`id`) \n)ENGINE=InnoDB COMMENT '" + entity.getRemark() + "'"));

        logger.info("create table ddl: {}", createDDL);

        modelingEntityDefExtMapper.createTable(createDDL);
    }

    @Override
    public void alterFormTableColumn(ModelingFieldDefExt param) {

        String nullable = "DEFAULT NULL";

        String tableName = ModelingModule.buildEntityTableName(param.getMKey());

        ModelingFieldDef def = new ModelingFieldDef();
        BeanUtils.copyProperties(param,def);
        FieldScheme scheme = FieldScheme.getFieldScheme(def);

        String dbType =  FieldScheme.getDbType(param.getWidth(), scheme);

        String columnDef = String.format(COLUMN_TEMPLATE,
                param.getName(),
                dbType,
                nullable,
                "",
                param.getLabel());

        String  alterDDL = "ALTER TABLE  "+tableName+" ADD COLUMN " + columnDef;

        logger.info("alter table ddl: {}", alterDDL);

        modelingEntityDefExtMapper.createFormTableColumn(alterDDL);

    }

    @Override
    public void modifyFormTableColumn(ModelingFieldDefExt param) {

        String nullable = "DEFAULT NULL";

        String tableName = ModelingModule.buildEntityTableName(param.getMKey());

        ModelingFieldDef def = new ModelingFieldDef();
        BeanUtils.copyProperties(param,def);

        FieldScheme scheme = FieldScheme.getFieldScheme(def);

        String dbType =  FieldScheme.getDbType(param.getWidth(), scheme);

        String columnDef = String.format(COLUMN_TEMPLATE,
                param.getName(),
                dbType,
                nullable,
                "",
                param.getLabel());

        String  modifyDDL = "ALTER TABLE  "+tableName+" MODIFY COLUMN " + columnDef;

        logger.info("modify table ddl: {}", modifyDDL);

        modelingEntityDefExtMapper.modifyFormTableColumn(modifyDDL);

    }

    @Override
    public void dropFormTableColumn(ModelingFieldDefExt param) {

        String tableName = ModelingModule.buildEntityTableName(param.getMKey());

        String  dropDDL = "ALTER TABLE  "+tableName+" DROP COLUMN " + param.getName();

        logger.info("drop table ddl: {}", dropDDL);

        modelingEntityDefExtMapper.dropFormTableColumn(dropDDL);

    }

    @Override
    public void insertDML(String sql) {
        logger.info("insert table dml: {}", sql);
        modelingEntityDefExtMapper.insertDML(sql);
    }
}
