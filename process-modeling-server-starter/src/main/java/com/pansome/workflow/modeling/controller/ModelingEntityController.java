package com.pansome.workflow.modeling.controller;

import com.pansome.workflow.ResultBean;
import com.pansome.workflow.modeling.domain.params.ModelingEntityExt;
import com.pansome.workflow.modeling.domain.params.ModelingFieldDefExt;
import com.pansome.workflow.modeling.entity.ModelingFieldDef;
import com.pansome.workflow.modeling.service.ModelingEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>动态表单</p>
 *
 * <p>说明： 动态表单API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月2日
 *
 */
@Api(description = "动态表单",value="动态表单" )
@RestController
@RequestMapping("/process-modeling-server/modeling/form")
public class ModelingEntityController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ModelingEntityService modelingEntityService;

    /**
     * @explain 新增表单  <swagger POST请求>
     * @param   @RequestBody：ModelingEntityDef
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月2日
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增表单", notes = "作者：王鹏飞")
    public ResultBean<Void> addForm(@Valid @RequestBody ModelingEntityExt param) {
         return modelingEntityService.addForm(param);
    }

    /**
     * @explain 新增字段  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月3日
     */
    @PostMapping("/field/add")
    @ApiOperation(value = "新增字段", notes = "作者：王鹏飞")
    public ResultBean<Void> addField(@Validated @RequestBody ModelingFieldDefExt param) {
        return  modelingEntityService.addField(param);
    }


    /**
     * @explain 更新字段  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月4日
     */
    @PostMapping("/field/update")
    @ApiOperation(value = "更新字段", notes = "作者：王鹏飞")
    public ResultBean<Void> updateField(@Validated @RequestBody ModelingFieldDefExt param) {
        return  modelingEntityService.updateField(param);
    }


    /**
     * @explain 删除字段  <swagger POST请求>
     * @param   @RequestBody：ModelingFieldDefExt
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月4日
     */
    @PostMapping("/field/delete/{id}")
    @ApiOperation(value = "删除字段", notes = "作者：王鹏飞")
    public ResultBean<Void> deleteField(@PathVariable("id") Long id) {
        return  modelingEntityService.deleteField(id);
    }

    /**
     * @explain 表字段集合  <swagger GOT请求>
     * @param   @RequestBody：mKey
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月4日
     */
    @GetMapping("/column/list/{mKey}")
    @ApiOperation(value = "表字段集合", notes = "作者：王鹏飞")
    public ResultBean<List<ModelingFieldDef>> getTableColumnList(@PathVariable("mKey") String mKey) {
        return modelingEntityService.getTableColumnList(mKey);
    }


    /**
     * @explain 检查formKey  <swagger POST请求>
     * @param   @RequestBody：ModelingEntityDef
     * @return  Void
     * @author  王鹏飞
     * @time    2024年7月2日
     */
    @GetMapping("/checkMKey/{mKey}")
    @ApiOperation(value = "检查formKey", notes = "作者：王鹏飞")
    public ResultBean<String> checkMKey(@PathVariable("mKey") String mKey) {
        return modelingEntityService.checkMKey(mKey);
    }


}
