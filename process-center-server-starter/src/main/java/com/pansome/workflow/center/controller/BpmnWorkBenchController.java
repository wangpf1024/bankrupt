package com.pansome.workflow.center.controller;


import com.pansome.workflow.center.service.WorkBenchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>自定义接口</p>
 *
 * <p>说明： 流程模型服务API接口层</P>
 * @version: V1.0.0
 * @author: 王鹏飞
 * @time    2024年7月5日
 *
 */
@Api(description = "流程模型",value="流程模型" )
@RestController
@RequestMapping("/process-center-service/bpmn")
public class BpmnWorkBenchController {


    @Autowired
    WorkBenchService workBenchService;


}
