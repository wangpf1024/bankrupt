package cn.bankrupt.workflow.center.service.impl;

import cn.bankrupt.workflow.center.domain.vo.VariableVo;
import cn.bankrupt.workflow.center.mapper.HistoryExtMapper;
import cn.bankrupt.workflow.center.service.HistoryExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryExtServiceImpl implements HistoryExtService {

    @Autowired
    HistoryExtMapper historyExtMapper;

    @Override
    public List<VariableVo> latestData(String processInstanceId, Date latestTime) {
        return historyExtMapper.latestData(processInstanceId,latestTime);
    }
}
