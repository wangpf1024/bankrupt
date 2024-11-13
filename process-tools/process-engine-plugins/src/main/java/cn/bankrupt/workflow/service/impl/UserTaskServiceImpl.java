package cn.bankrupt.workflow.service.impl;

import cn.bankrupt.workflow.service.UserTaskService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("userTaskService")
public class UserTaskServiceImpl implements UserTaskService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;

    protected ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            throw new InvalidRequestException(Response.Status.BAD_REQUEST, "No process engine available");
        }
        return processEngine;
    }

    @Autowired
    ProcessRedisCache processRedisCache;

    @Override
    public List<String> getGroupUser(String params) {

        String[] ps = params.split(",");

        List<String> idList = Arrays.asList(ps);

        IdentityService identityService = processEngine.getIdentityService();

        List<String> userIds = new ArrayList<>();

        idList.forEach(i->{
            List<User> users = identityService.createUserQuery().memberOfGroup(i).list();
            List<String> ids = users.stream().map(User::getId).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(ids)){
                userIds.addAll(ids);
            }
        });

        return userIds;
    }

    @Override
    @Async
    public void handleNextUserTask(TaskMsgDataDto dto, Map<String, TaskDefinition> actives) {
        // Get the BPMN model instance
        BpmnModelInstance modelInstance = getProcessEngine().getRepositoryService().getBpmnModelInstance(dto.getProcessDefinitionId());
        //有下级任务
        if(actives != null && actives.keySet().size() >= 1){
            Iterator<String> it = actives.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                //当前执行表单入口
                if(dto.getTaskDefinitionKey().equals(key)){
                    FlowNode currentFlowNode = modelInstance.getModelElementById(key);
                    getNextUserTask(dto,currentFlowNode);
                }
            }
        }
        String msg = JSONUtil.toJsonStr(dto);
        //创建用户执行任务
        processRedisCache.enqueueMessageWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode(), msg);
    }

    private TaskMsgDataDto getNextUserTask(TaskMsgDataDto dto,FlowNode currentFlowNode){

        if(currentFlowNode == null) return dto;

        //结束节点
        if(currentFlowNode instanceof EndEvent)  return dto;

        if(dto.getNextTaskUser() == null){
            dto.setNextTaskUser(new ArrayList<>());
        }

        //当前下级全部节点
        List<FlowNode> nextFlowNode = currentFlowNode.getSucceedingNodes().list();

        if(CollectionUtils.isEmpty(nextFlowNode)) return dto;

        for (int i = 0; i < nextFlowNode.size(); i ++ ) {
            FlowNode next = nextFlowNode.get(i);
            //第一个用户任务
            if (next instanceof UserTask) {
                UserTask userTask = (UserTask) next;
                //并行用户任务
                if(userTask.getLoopCharacteristics() instanceof MultiInstanceLoopCharacteristics){
                    MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
                    String expression  =  loopCharacteristics.getCamundaCollection();
                    if(!StrUtil.isEmpty(expression)){
                        Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\.(\\w+)\\(\"([^\"]*)\"\\)}");
                        Matcher matcher = pattern.matcher(expression);
                        if (matcher.find()) {
                            // Extracted components from the expression
                            String beanName = matcher.group(1); // e.g., "userTaskService"
                            String methodName = matcher.group(2); // e.g., "getGroupUser"
                            String parameter = matcher.group(3); // e.g., "13522921120,1111"
                            List<String> userIds = getGroupUser(parameter);
                            dto.getNextTaskUser().addAll(userIds);
                        }
                    }else{
                        dto.getNextTaskUser().add(userTask.getCamundaAssignee());
                    }
                }else{
                    //串行用户任务（only for single user）
                    String expression  =  userTask.getCamundaAssignee();
                    if(!StrUtil.isEmpty(expression)){
                        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
                        Matcher matcher = pattern.matcher(expression);
                        if (matcher.find()) {
                            String attr = matcher.group(1);
                            if(!StrUtil.isEmpty(attr)){
                                dto.getNextTaskUser().add(userTask.getAttributeValue(attr));
                            }
                        }else {
                            dto.getNextTaskUser().add(expression);
                        }
                    }
                }
            }else{
               return getNextUserTask(dto,next);
            }
        };
        return dto;
    };



    @Override
    public void handleUserTaskGroup(TaskMsgDataDto dto, Map<String, TaskDefinition> actives) {
        // Get the BPMN model instance
        BpmnModelInstance modelInstance = getProcessEngine().getRepositoryService().getBpmnModelInstance(dto.getProcessDefinitionId());
        if(dto.getCurrentGroupIds() == null){
            dto.setCurrentGroupIds(new ArrayList<>());
        }
        //有下级任务
        if(actives != null && actives.keySet().size() >= 1){
            Iterator<String> it = actives.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                //当前执行表单
                if(dto.getTaskDefinitionKey().equals(key)){
                    FlowNode currentFlowNode = modelInstance.getModelElementById(key);
                    UserTask userTask = (UserTask) currentFlowNode;
                    //并行用户任务
                    if(userTask.getLoopCharacteristics() instanceof MultiInstanceLoopCharacteristics){
                        MultiInstanceLoopCharacteristics loopCharacteristics = (MultiInstanceLoopCharacteristics)userTask.getLoopCharacteristics();
                        String expression  =  loopCharacteristics.getCamundaCollection();
                        if(!StrUtil.isEmpty(expression)){
                            Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\.(\\w+)\\(\"([^\"]*)\"\\)}");
                            Matcher matcher = pattern.matcher(expression);
                            if (matcher.find()) {
                                // Extracted components from the expression
                                String beanName = matcher.group(1); // e.g., "userTaskService"
                                String methodName = matcher.group(2); // e.g., "getGroupUser"
                                String parameter = matcher.group(3); // e.g., "13522921120,1111"
                                String[] groupIds = parameter.split("\\,");
                                for (int i = 0; i < groupIds.length; i++) {
                                    String groupId = groupIds[i];
                                    List<String> userIds = getGroupUser(groupId);
                                    if(userIds.contains(dto.getAssignee())){
                                        dto.getCurrentGroupIds().add(groupId);
                                    }
                                }
                            }
                        }
                    }else {
                        //串行用户任务（only for single user）
                        String expression  =  userTask.getCamundaAssignee();
                        if(!StrUtil.isEmpty(expression)){
                            Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
                            Matcher matcher = pattern.matcher(expression);
                            if (matcher.find()) {
                                String attr = matcher.group(1);
                                if(!StrUtil.isEmpty(attr)){
                                    dto.getCurrentGroupIds().add(userTask.getAttributeValue(attr));
                                }
                            } else {
                                dto.getCurrentGroupIds().add(expression);
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

}
