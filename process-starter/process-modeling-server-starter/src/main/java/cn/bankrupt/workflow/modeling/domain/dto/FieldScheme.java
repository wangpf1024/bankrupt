package cn.bankrupt.workflow.modeling.domain.dto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cn.bankrupt.workflow.enums.FieldType;
import cn.bankrupt.workflow.modeling.entity.ModelingFieldDef;

@JsonSubTypes({
        @JsonSubTypes.Type(value = NumberInputFieldScheme.class, name = "number"),
        @JsonSubTypes.Type(value = TextInputFieldScheme.class, name = "text"),
        @JsonSubTypes.Type(value = DateFieldScheme.class, name = "date"),
})
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
public class FieldScheme {

    private FieldType type;

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    @JsonIgnore
    public static FieldScheme getFieldScheme(ModelingFieldDef fieldDef) {
        if (FieldType.number.name().equals(fieldDef.getType())) {
            return JSONUtil.toBean(fieldDef.getScheme(), NumberInputFieldScheme.class);
        }else if (FieldType.date.name().equals(fieldDef.getType())) {
            return JSONUtil.toBean(fieldDef.getScheme(), DateFieldScheme.class);
        }else if (FieldType.text.name().equals(fieldDef.getType())) {
            return JSONUtil.toBean(fieldDef.getScheme(), TextInputFieldScheme.class);
        }else if (FieldType.radio.name().equals(fieldDef.getType())) {
            return JSONUtil.toBean(fieldDef.getScheme(), RedioFieldScheme.class);
        }else if (FieldType.select.name().equals(fieldDef.getType())) {
            return JSONUtil.toBean(fieldDef.getScheme(), SelectFieldScheme.class);
        }else if (FieldType.options.name().equals(fieldDef.getType())) {
            return JSONUtil.toBean(fieldDef.getScheme(), OptionsFieldScheme.class);
        }
        return null;
    }

    @JsonIgnore
    public static String getDbType(int width, FieldScheme scheme) {
        String dbType = "varchar(" + width + ")";
        if (scheme instanceof TextInputFieldScheme
                || scheme instanceof RedioFieldScheme
                || scheme instanceof SelectFieldScheme
                || scheme instanceof OptionsFieldScheme) {
            dbType = "varchar(" + width + ")";
        }else if (scheme instanceof NumberInputFieldScheme) {
            NumberInputFieldScheme numberInputFieldScheme = (NumberInputFieldScheme)scheme;
            if (Integer.valueOf(0).equals(numberInputFieldScheme.getPrecision())) {
                dbType = "bigint";
            } else {
                dbType = "DECIMAL("+width+"," + numberInputFieldScheme.getPrecision() + ")";
            }
        }else if (scheme instanceof DateFieldScheme) {
            DateFieldScheme numberInputFieldScheme = (DateFieldScheme)scheme;
            dbType = "datetime";
            //YYYY-MM-DD
            if(!StrUtil.isEmpty(numberInputFieldScheme.getFormat())
                    && numberInputFieldScheme.getFormat().length() <= 10 ){
                dbType = "date";
            }
        }
        return dbType;
    }
}
