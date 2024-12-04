/**
 * @filename:WorkFlowFormCategoryController 2024年6月24日
 * @project workflow  V1.0.000
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package com.pansome.workflow.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pansome.workflow.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 表单分类API接口层</P>
 * @version: V1.0.000
 * @author:  王鹏飞
 * @time     2024年6月24日
 */
public class AbstractController<S extends IService<T>,T>{
	
	@Autowired
    protected S baseService;

	/**
	 * @explain 查询对象  <swagger GET请求>
	 * @param   @PathVariable：id
	 * @return  ResultBean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@GetMapping("/getById/{id}")
	@ApiOperation(value = "获取对象", notes = "作者：王鹏飞")
	@ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
	public ResultBean<T> getById(@PathVariable("id")Long id){
		T obj=baseService.getById(id);
		if (null!=obj ) {
			return ResultBean.ofSuccess(obj);
		}
		return ResultBean.ofError("查询对象不存在！");
	}
	
	/**
	 * @explain 删除对象
	 * @param   @PathVariable：id
	 * @return  ResultBean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@PostMapping("/deleteById")
	@ApiOperation(value = "删除", notes = "作者：王鹏飞")
	@ApiImplicitParam(paramType="query", name = "id", value = "对象id", required = true, dataType = "Long")
	public ResultBean<Boolean> deleteById(Long id){
		T obj=baseService.getById(id);
		if (null!=obj) {
		  boolean rsg = baseService.removeById(id);
		  if (rsg) {
			  return ResultBean.ofSuccess(rsg);
		  }else {
			  return ResultBean.ofError("删除失败！");
		  }
		}
		return ResultBean.ofError("查询对象不存在！");
	}
	
	/**
	 * @explain 添加
	 * @param   @RequestBody ：T
	 * @return  Boolean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@PostMapping("/insert")
	@ApiOperation(value = "添加", notes = "作者：王鹏飞")
	public ResultBean<T> insert(@RequestBody T entity){
		if (null!=entity) {
			boolean rsg = baseService.save(entity);
			if (rsg) {
				return ResultBean.ofSuccess(entity,"添加成功");
			  }else {
				return ResultBean.ofError("添加失败！");
			  }
		}
		return ResultBean.ofError("请传入正确参数！");
	}
	
	/**
	 * @explain 修改
	 * @param   @RequestBody ：T
	 * @return  Boolean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "作者：王鹏飞")
	public ResultBean<T> update(@RequestBody T entity){
		if (null!=entity) {
			boolean rsg = baseService.updateById(entity);
			if (rsg) {
				return ResultBean.ofSuccess(entity,"修改成功");
			  }else {
				return ResultBean.ofError("修改失败！");
			  }
		}
		return ResultBean.ofError("请传入正确参数！");
	}

	/**
	 * @explain 保存或更新
	 * @param   @RequestBody ：T
	 * @return  Boolean
	 * @author  王鹏飞
	 * @time    2024年6月24日
	 */
	@PutMapping("/insertOrUpdate")
	@ApiOperation(value = "保存或更新", notes = "作者：王鹏飞")
	public ResultBean<T> insertOrUpdate(@RequestBody T entity) {
		if (null!=entity) {
			boolean rsg = baseService.saveOrUpdate(entity);
			if (rsg) {
				return ResultBean.ofSuccess(entity,"修改成功");
			}else {
				return ResultBean.ofError("修改失败！");
			}
		}
		return ResultBean.ofError("请传入正确参数！");
	}

	/**
	 * 分页查询所有数据
	 *
	 * @param pageIndex     页码
	 * @param pageSize      页长
	 * @param t 查询实体
	 * @return 所有数据
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：王鹏飞")
	@GetMapping("/getPages")
	public ResultBean<List<T>> getPages(T t,@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
									@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true);
		List<T> list = baseService.list(new QueryWrapper<>(t));
		PageInfo<T> pageInfo = new PageInfo<>(list);
		return ResultBean.ofSuccess(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
	}
}
