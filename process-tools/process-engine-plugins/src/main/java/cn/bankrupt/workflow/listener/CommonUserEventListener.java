package cn.bankrupt.workflow.listener;

import cn.bankrupt.workflow.enums.EventEnum;
import cn.bankrupt.workflow.event.UserTaskEventInterface;
import cn.bankrupt.workflow.service.UserTaskService;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
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


    @Override
    public void notify(DelegateTask delegateTask) {

        TaskMsgDataDto dto = super.prams(delegateTask);

        // Task event handling logic
        String eventName = delegateTask.getEventName();
        Boolean checkSkip = (Boolean) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.function_range_skip_pass.name());


        //前置-附加业务-1 (配置用户组)--需要更新 DelegateTask 参数变量
        if(EventEnum.create.getName().equals(eventName)){
            //将用户与分组信息对应
            handleUserTaskGroup(delegateTask,dto);
        }
        //前置-附加业务-2 (配置最后审批人)
        if(EventEnum.complete.getName().equals(eventName)){
            delegateTask.setVariable(ProcessWorkFlowBaseEventEnum.variable_last_completed_by.getCode(), delegateTask.getAssignee());
        }

        //跳过已通过
        if(checkSkip){
            handleSkipProcess(delegateTask,dto);
        }else{
            handleCommonProcess(dto,eventName);
        }


        //后置-附加业务-1 (查询下一个任务用户)
        if(EventEnum.create.getName().equals(eventName)){
            dto.setEventCode(ProcessWorkFlowBaseEventEnum.user_next_task.getCode());
            handleNextUserTask(delegateTask,dto);
        }


    }

    /**
     * 处理跳过已通过任务
     * @param dto
     */
    private void handleSkipProcess(DelegateTask delegateTask,TaskMsgDataDto dto){
        // Task event handling logic
        String eventName = delegateTask.getEventName();
        //任务名称
        String taskKey = delegateTask.getTaskDefinitionKey();
        //获取执行任务定义集合
        String completedTasksListKey = String.format(ProcessWorkFlowBaseEventEnum.in_process_completed_tasks.getCode());
        //通过的审批的任务
        List<String> completedTasksList = (List<String>) delegateTask.getVariable(completedTasksListKey);
        //初始化一个数组
        if(completedTasksList == null){
            completedTasksList = new ArrayList<>();
        }
        //设置跳过表单，配置项,camunda_skip is a value form model design， 1 pass ,2 reject
        Integer camundaSkip = (Integer) delegateTask.getVariable(ProcessWorkFlowBaseEventEnum.variable_camunda_pass_status.getCode());
        // 创建任务
        if(EventEnum.create.getName().equals(eventName)){
            Boolean complete = false;
            // 已通过审核的任务
            if(!CollectionUtils.isEmpty(completedTasksList)
                    && completedTasksList.contains(taskKey)){
                complete = true;
                //删除历史中完成task位置
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
            //更新最新参数
            delegateTask.setVariable(completedTasksListKey,completedTasksList);
            if(complete){
                delegateTask.complete();
            }else{
                handleCommonProcess(dto,eventName);
            }
        }
        else if(EventEnum.complete.getName().equals(eventName)){
            // 驳回任务
            if(camundaSkip !=null
                    && camundaSkip > 1){
                // 过滤掉上次通过，但是本次次驳回的任务
                completedTasksList = completedTasksList.stream().filter(i -> !i.equals(taskKey)).collect(Collectors.toList());
            }else{
                //通过
                completedTasksList.add(taskKey);
            }
            delegateTask.setVariable(completedTasksListKey,completedTasksList);
            handleCommonProcess(dto,eventName);
        }
    }


    /**
     * 处理普通任务
     * @param dto
     * @param eventName
     */
    private void handleCommonProcess(TaskMsgDataDto dto,String eventName){
        if(EventEnum.create.getName().equals(eventName)){
            createEvent.handleEvent(dto);
        }else if(EventEnum.complete.getName().equals(eventName)){
            completeEvent.handleEvent(dto);
        }else if(EventEnum.assignment.getName().equals(eventName)){
            assignmentEvent.handleEvent(dto);
        }else{
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
