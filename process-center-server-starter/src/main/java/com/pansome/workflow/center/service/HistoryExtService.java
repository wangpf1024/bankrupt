package com.pansome.workflow.center.service;


import com.pansome.workflow.center.domain.vo.VariableVo;

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
