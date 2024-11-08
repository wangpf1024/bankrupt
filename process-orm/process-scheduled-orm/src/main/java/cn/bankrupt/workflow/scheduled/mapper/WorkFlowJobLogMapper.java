/**
 * @filename:WorkFlowJobLogMapper 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.bankrupt.workflow.scheduled.entity.WorkFlowJobLog;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 任务执行器日志数据访问层</p>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Mapper
public interface WorkFlowJobLogMapper extends BaseMapper<WorkFlowJobLog> {
	
}
