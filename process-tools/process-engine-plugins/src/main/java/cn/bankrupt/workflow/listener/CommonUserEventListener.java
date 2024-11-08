package cn.bankrupt.workflow.listener;

import cn.bankrupt.workflow.service.UserTaskService;
import cn.bankrupt.workflow.enums.EventEnum;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import cn.bankrupt.workflow.event.UserTaskEventInterface;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;


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
        // Task event handling logic
        String taskId = delegateTask.getId();
        String eventName = delegateTask.getEventName();
        TaskMsgDataDto dto = super.prams(delegateTask);
        if(EventEnum.create.getName().equals(eventName)){
            createEvent.handleEvent(dto);
        }else if(EventEnum.complete.getName().equals(eventName)){
            completeEvent.handleEvent(dto);
        }else if(EventEnum.assignment.getName().equals(eventName)){
            assignmentEvent.handleEvent(dto);
        }else{
            logger.info("Task ID: " + taskId + " triggered by event: " + eventName);
        }


        //附加业务
        if(EventEnum.create.getName().equals(eventName)){
            dto.setEventCode(ProcessWorkFlowBaseEventEnum.user_next_task.getCode());
            handleNextUserTask(delegateTask,dto);
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

}
