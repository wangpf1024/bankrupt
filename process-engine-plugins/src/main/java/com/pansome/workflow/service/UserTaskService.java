package com.pansome.workflow.service;

import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
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

    /**
     * 处理用户与分组关系
     * @param dto
     */
    void handleUserTaskGroup(TaskMsgDataDto dto, Map<String, TaskDefinition> actives);
}
