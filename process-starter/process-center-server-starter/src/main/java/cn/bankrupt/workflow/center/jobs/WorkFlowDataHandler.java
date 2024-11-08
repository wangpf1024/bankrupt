package cn.bankrupt.workflow.center.jobs;


import cn.bankrupt.workflow.center.domain.vo.FiledVo;
import cn.bankrupt.workflow.center.domain.vo.VariableVo;
import cn.bankrupt.workflow.center.service.HistoryExtService;
import cn.bankrupt.workflow.enums.ModelingModule;
import cn.bankrupt.workflow.model.entity.WorkFlowFormModelInfo;
import cn.bankrupt.workflow.model.service.WorkFlowFormModelInfoService;
import cn.bankrupt.workflow.modeling.service.FormService;
import cn.bankrupt.workflow.scheduled.ScheduledTask;
import cn.bankrupt.workflow.scheduled.domain.dto.TaskDto;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Component
public class WorkFlowDataHandler implements ScheduledTask {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    HistoryExtService historyExtService;

    @Autowired
    WorkFlowFormModelInfoService workFlowFormModelInfoService;

    @Autowired
    FormService formService;

    protected ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            throw new InvalidRequestException(Response.Status.BAD_REQUEST, "No process engine available");
        }
        return processEngine;
    }

    @Override
    public String getJobName() {
        return "WorkFlowDataHandler";
    }

    @Override
    public void execute(TaskDto param){

        List<VariableVo> variableVos = historyExtService.latestData(null,param.getLatestTime());

        Map<String, List<VariableVo>> groupedItems = variableVos.stream()
                .collect(Collectors.groupingBy(item -> item.getProcessInstanceId()));

        groupedItems.keySet().forEach( processKey -> {
            List<VariableVo> vos = groupedItems.get(processKey);
            // 处理表单字典
            Map<String, List<FiledVo>> filed = procVariableVos(vos);
            filed.keySet().forEach( key ->{
                List<FiledVo> list = filed.get(key);
                String sql = insertSql(list);
                formService.insertDML(sql);
            });
        });

    }

    /**
     * 处理 insert sql
     * @param filedVos
     * @return
     */
    private String insertSql(List<FiledVo> filedVos){
        String insertFields = IntStream.range(0, filedVos.size()).boxed().filter(i ->  !filedVos.get(i).getName().equals("formId")).map(i -> String.format("`%s`", filedVos.get(i).getName())).collect(Collectors.joining(","));
        String insertValues = IntStream.range(0, filedVos.size()).boxed().filter(i ->  !filedVos.get(i).getName().equals("formId")).map(i -> String.format("'%s'", filedVos.get(i).getValue())).collect(Collectors.joining(","));
        FiledVo c = filedVos.stream().filter(i -> i.getName().equals("formId")).findFirst().get();
        WorkFlowFormModelInfo modelInfo = workFlowFormModelInfoService.getById(c.getValue());
        String tableName = ModelingModule.buildEntityTableName( modelInfo.getFormKey());
        String sql = String.format("INSERT INTO %s(%s) VALUES(%s)",tableName, insertFields, insertValues);
        return sql;
    }

    /**
     * {
     * 	"1": [{
     * 		"name": "startDate",
     * 		"value": "2024-07-11"
     * 	    }, {
     * 		"name": "formId",
     * 		"value": "5"
     *    }, {
     * 		"name": "endDate",
     * 		"value": "2024-07-31"
     *    }, {
     * 		"name": "createTime",
     * 		"value": "2024-07-11 15:28:24"
     *    }, {
     * 		"name": "pictures",
     * 		"value": "http://localhost:3000/public/uploadFile/test/17206829037513d6f8064605044c3aa7e4c47dffc3da3.png"
     *    }, {
     * 		"name": "leaveDays",
     * 		"value": "10"
     *    }, {
     * 		"name": "applicant",
     * 		"value": "关云长"
     *    }]
     * }
     * 处理参数：-> 列对象
     * @param variableVos
     * @return
     */
    private Map<String, List<FiledVo>> procVariableVos(List<VariableVo> variableVos){
        Map<String, List<FiledVo>> returnMap = new HashMap<>();

        //  _2_*
        Map<String, List<VariableVo>> groupedItems = variableVos.stream()
                .collect(Collectors.groupingBy(item ->extractPattern(item.getName())));

        // 转换分组结果为自定义对象
        groupedItems.keySet().forEach( key ->{
            List<FiledVo> filedVos = groupedItems.get(key).stream().map( variableVo -> {
                FiledVo filedVo = new FiledVo();
                String[] name = variableVo.getName().split(key);
                filedVo.setName(name[1]);
                filedVo.setValue(variableVo.getValue());
                return filedVo;
            }).collect(Collectors.toList());
            returnMap.put(key,filedVos);
        });
        return returnMap;
    }


    // 提取模式部分的函数
    private static String extractPattern(String item) {
        // 匹配以 _N_ 开头，后面跟着一个或多个数字的模式
        Pattern pattern = Pattern.compile("_(\\d+)_");
        Matcher matcher = pattern.matcher(item);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}
