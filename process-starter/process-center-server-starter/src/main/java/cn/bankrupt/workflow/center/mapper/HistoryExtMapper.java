/**
 * @filename:WorkFlowCopyMapper 2024年7月2日
 * @project workflow  V1.0.0
 * Copyright(c) 2020 王鹏飞 Co. Ltd. 
 * All right reserved. 
 */
package cn.bankrupt.workflow.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.bankrupt.workflow.center.domain.vo.VariableVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 流程抄送数据访问层</p>
 * @version: V1.0.0
 * @author: 王鹏飞
 * 
 */
@Mapper
public interface HistoryExtMapper extends BaseMapper{

    /**
     * 查询历史变量
     * @param latestTime
     * @return
     */
    List<VariableVo> latestData(@Param("processInstanceId")String processInstanceId,@Param("latestTime") Date latestTime);

}
