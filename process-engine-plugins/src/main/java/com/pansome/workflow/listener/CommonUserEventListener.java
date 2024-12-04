package com.pansome.workflow.listener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pansome.workflow.enums.EventEnum;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowBaseEventEnum;
import com.pansome.workflow.enums.workflow.ProcessWorkFlowStatusEnum;
import com.pansome.workflow.event.UserTaskEventInterface;
import com.pansome.workflow.domain.workflow.TaskMsgDataDto;
import com.pansome.workflow.service.ExtendCommonService;
import com.pansome.workflow.service.UserTaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class CommonUserEventListener extends CommonDelegate implements TaskListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    @Qualifier("createEventImpl")
    UserTaskEventInterface createEvent;

    @Autowired
    @Qualifier("completeEventImpl")
    UserTaskEventInterface completeEvent;

    @Autowired
    @Qualifier("assignmentEventImpl")
    UserTaskEventInterface assignmentEvent;

    @Autowired
    UserTaskService userTaskService;

    @Autowired
    List<ExtendCommonService> extendCommonServices;

    @Override
    public void notify(DelegateTask delegateTask) {

        TaskMsgDataDto dto = super.prams(delegateTask);
        // Task event handling logic
        String eventName = delegateTask.getEventName();
        //获取设置参数
        for (int i = 0; i < extendCommonServices.size(); i++) {
            dto = extendCommonServices.get(i).getWorkFlowModelInfo(dto);
        }

        //前置-附加业务-1
        if(EventEnum.create.getName().equals(eventName)){
            //将用户与分组信息对应
            handleUserTaskGroup(delegateTask,dto);
        }

        //前置-附加业务-2
        if(EventEnum.complete.getName().equals(eventName)){
            //配置最后审批人
            delegateTask.setVariable(ProcessWorkFlowBaseEventEnum.variable_last_completed_by.getCode(), delegateTask.getAssignee());
            //审批意见透传
            boolean hasComment = delegateTask.hasVariable(ProcessWorkFlowBaseEventEnum.variable_comment.getCode());
            if(hasComment){
               String comment = (String) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_comment.getCode());
               dto.setEventComment(comment);
            }
        }

        Boolean checkSkip = (Boolean) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name());
        //跳过已通过
        if(checkSkip){
            boolean skip = handleSkipProcess(delegateTask);
            // 返回
            if(skip){
                return;
            }
        }

        //处理任务
        handleCommonProcess(dto,eventName);

        //后置-附加业务-1 (查询下一个任务用户)
        if(EventEnum.create.getName().equals(eventName)){
            dto.setEventCode(ProcessWorkFlowBaseEventEnum.user_next_task.getCode());
            handleNextUserTask(delegateTask,dto);
        }

        logger.info("event : "+ eventName  + "  dto   " + JSONUtil.toJsonStr(dto));

    }

    /**
     * 处理跳过已通过任务
     * @param delegateTask
     */
    private boolean handleSkipProcess(DelegateTask delegateTask){
        // Task event handling logic
        String eventName = delegateTask.getEventName();
        //任务名称
        String taskKey = delegateTask.getTaskDefinitionKey();
        //获取执行任务定义集合
        String completedTasksListKey = String.format(ProcessWorkFlowBaseEventEnum.in_process_completed_tasks.getCode());

        //通过的审批的任务
        List<String> completedTasksList;
        if(delegateTask.hasVariable(completedTasksListKey)){
            completedTasksList = (List<String>) delegateTask.getVariable(completedTasksListKey);
        }else{
            //初始化一个数组
            completedTasksList = new ArrayList<>();
        }

        // 创建新任务
        if(EventEnum.create.getName().equals(eventName)){
            //是否自动完成
            Boolean autoCompleted = false;
            //是否存在驳回动作
            if(delegateTask.hasVariable(ProcessWorkFlowBaseEventEnum.variable_reject_times.getCode())){
                // 当前任务存在于已完成任务集合中
                if(!CollectionUtils.isEmpty(completedTasksList)
                        && completedTasksList.contains(taskKey)){
                    //自动跳过
                    autoCompleted = true;
                    //删除当前任务，并且复制一个新的已完成任务集合
                    List<String> newCompletedTasksList = new ArrayList<>(completedTasksList.size() - 1);
                    boolean isFilter = false;
                    for (int i = 0; i < completedTasksList.size(); i++) {
                        //首次跳过,出现第二次允许添加
                        if(completedTasksList.get(i).equals(taskKey)
                                && !isFilter){
                            isFilter = true;
                            continue;
                        }
                        newCompletedTasksList.add(completedTasksList.get(i));
                    }
                    completedTasksList = newCompletedTasksList;
                }
                //更新最新已完成任务
                delegateTask.setVariable(completedTasksListKey,completedTasksList);
                if(autoCompleted){
                    delegateTask.complete();
                    List<String> taskIds;
                    if(delegateTask.hasVariable(ProcessWorkFlowBaseEventEnum.variable_auto_tasks.getCode())){
                        taskIds = (List<String>)delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_auto_tasks.getCode());
                    }else{
                        taskIds = new ArrayList<>();
                    }
                    taskIds.add(delegateTask.getId());
                    delegateTask.setVariable(ProcessWorkFlowBaseEventEnum.variable_auto_tasks.getCode(),taskIds);
                    return true;
                }
                //没有自动完成任务将驳回至复原
                delegateTask.setVariable(ProcessWorkFlowBaseEventEnum.variable_camunda_pass_status.getCode(),Integer.valueOf(1));
            }
        }
        else if(EventEnum.complete.getName().equals(eventName)){
            //完成审核后,将完成的任务增加至完成任务列表
            completedTasksList.add(taskKey);
            //设置跳过表单，配置项,camunda_skip is a value form model design， 1 pass ,2 reject
            if(delegateTask.hasVariable(ProcessWorkFlowBaseEventEnum.variable_camunda_pass_status.getCode())){
                Object object = delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_camunda_pass_status.getCode());
                Integer camundaSkip;
                if(object instanceof String){
                    camundaSkip = Integer.valueOf((String) object);
                }
                else
                {
                    camundaSkip = (Integer) object;
                }
                // 驳回任务本次任务，将本次任务移除完成任务集合列表
                if(camundaSkip !=null
                        && camundaSkip > 1){
                    // 过滤掉上次通过，但是本次次驳回的任务
                    completedTasksList = completedTasksList.stream().filter(i -> !i.equals(taskKey)).collect(Collectors.toList());
                }
            }
            delegateTask.setVariable(completedTasksListKey,completedTasksList);
            if(delegateTask.hasVariable(ProcessWorkFlowBaseEventEnum.variable_auto_tasks.getCode())){
                List<String> taskIds =  (List<String>)delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_auto_tasks.getCode());;
                if(taskIds.contains(delegateTask.getId())){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 处理普通任务
     * @param dto
     * @param eventName
     */
    private void handleCommonProcess(TaskMsgDataDto dto,String eventName){
        if(EventEnum.create.getName().equals(eventName))
        {
            createEvent.handleEvent(dto);
        }
        else if(EventEnum.complete.getName().equals(eventName))
        {
            completeEvent.handleEvent(dto);
        }
        else if(EventEnum.assignment.getName().equals(eventName))
        {
            assignmentEvent.handleEvent(dto);
        }
        else
        {
            logger.info("Triggered by event: " + eventName);
        }
    }



    /**
     * 处理下一项任务信息的基本信息
     * @param delegateTask
     */
    private void handleNextUserTask(DelegateTask delegateTask,TaskMsgDataDto dto){
        // Get the all actives
        Map<String, TaskDefinition> actives = ((TaskEntity) delegateTask).getProcessDefinition().getTaskDefinitions();
        userTaskService.handleNextUserTask(dto,actives);
    }

    /**
     * 处理任务对应分组新增
     * @param delegateTask
     */
    private void handleUserTaskGroup(DelegateTask delegateTask,TaskMsgDataDto dto){
        // Get the all actives
        Map<String, TaskDefinition> actives = ((TaskEntity) delegateTask).getProcessDefinition().getTaskDefinitions();
        userTaskService.handleUserTaskGroup(dto,actives);
    }

}
