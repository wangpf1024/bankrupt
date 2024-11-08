package cn.bankrupt.workflow.service;

import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import org.camunda.bpm.engine.impl.task.TaskDefinition;

import java.util.List;
import java.util.Map;

public interface UserTaskService {


    /**
     *  Multi-instance: task
     *  collection setting
     * ${userTaskService.getGroupUser("13522921120,1111")}
     * 获取分组用户
     * @param params
     * @return
     */
    List<String> getGroupUser(String params);

    /**
     * 处理用户NEXT任务
     * @param dto
     */
    void handleNextUserTask(TaskMsgDataDto dto, Map<String, TaskDefinition> actives);

}
