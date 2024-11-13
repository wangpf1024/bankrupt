package cn.bankrupt.workflow.center.service;


import cn.bankrupt.workflow.center.domain.vo.VariableVo;

import java.util.Date;
import java.util.List;

public interface HistoryExtService {

    /**
     * 查询历史变量数据
     * @param processInstanceId
     * @param latestTime
     * @return
     */
    List<VariableVo> latestData(String processInstanceId,Date latestTime);
}
