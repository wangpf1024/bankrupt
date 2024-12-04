# workflow
后端基于 camunda 7.21 二次开发
提供流程模型编辑到部署。


## 系统模块

~~~
bankrupt
├── process-admin-web-start         // 执行流程adminweb平台  
├── process-cache         // 缓存模块
├── process-center-orm  // 核心系统-核心模块ORM
├── process-center-server-starter  // 核心系统-ResfultAPI
├── process-common            // 核心系统-通用模块
├── process-engine-plugins         // 核心系统-Camunda扩展插件
├── process-framework             //  核心系统-Spring BOOT框架
├── process-idm-orm             // 用户管理-ORM
├── process-idm-server-starter  // 用户管理ORM-ResfultAPI
├── process-model-orm         //流程模型管理-ORM
├── process-model-server-starter         //流程模型管理-ResfultAPI
├── process-modeling-orm         //动态表单管理-ORM
├── process-modeling-server-starter         //动态表单管理-ResfultAPI
├── process-msg-subcriber         //消息订阅
├── process-mybatis-generator      //生成数据库基础代码 
├── process-open-api-sdk      //开放API  
├── process-scheduled-orm      //定时任务-ORM 
├── process-scheduled-server-starter     //定时任务-业务处理                                               
├── pom.xml                // POM管理
~~~


## 系统架构
![系统架构.png](/doc/images/系统架构.png)

## camunda 数据库表
~~~ 
ACT_RE_*: 'RE’表示流程资源存储，这个前缀的表包含了流程定义和流程静态资源（图片，规则等）
ACT_RU_*: 'RU’表示流程运行时。 这些运行时的表，包含流程实例，任务，变量，Job等运行中的数据。 Camunda只在流程实例执行过程中保存这些数据，在流程结束时就会删除这些记录， 这样运行时表的数据量最小，可以最快运行。
ACT_ID_*: 'ID’表示组织用户信息，比如用户，组等。
ACT_HI_*: 'HI’表示流程历史记录。 这些表包含历史数据，比如历史流程实例，变量，任务等。
ACT_GE_*: ‘GE’表示流程通用数据。
~~~

| 分类             | 表名称                   | 描述              |
|------------------|--------------------------|-----------------|
| 流程资源存储     | act_re_case_def          | CMMN案例管理模型定义表   |
| 流程资源存储     | act_re_decision_def      | DMN决策模型定义表      |
| 流程资源存储     | act_re_decision_req_def  | 待确定                |
| 流程资源存储     | act_re_deployment        | 流程部署表           |
| 流程资源存储     | act_re_procdef           | BPMN流程模型定义表     |
| 流程运行时       | act_ru_authorization     | 流程运行时收取表        |
| 流程运行时       | act_ru_batch             | 流程执行批处理表        |
| 流程运行时       | act_ru_case_execution    | CMMN案例运行执行表     |
| 流程运行时       | act_ru_case_sentry_part  | 待确定             |
| 流程运行时       | act_ru_event_subscr      | 流程事件订阅表         |
| 流程运行时       | act_ru_execution         | BPMN流程运行时记录表    |
| 流程运行时       | act_ru_ext_task          | 流程任务消息执行表       |
| 流程运行时       | act_ru_filter            | 流程定义查询配置表       |
| 流程运行时       | act_ru_identitylink      | 运行时流程人员表        |
| 流程运行时       | act_ru_incident          | 运行时异常事件表        |
| 流程运行时       | act_ru_job               | 流程运行时作业表        |
| 流程运行时       | act_ru_jobdef            | 流程作业定义表         |
| 流程运行时       | act_ru_meter_log         | 流程运行时度量日志表      |
| 流程运行时       | act_ru_task              | 流程运行时任务表        |
| 流程运行时       | act_ru_task_meter_log    | 流程运行时任务日志表      |
| 流程运行时       | act_ru_variable          | 流程运行时变量表        |
| 组织用户信息     | act_id_group             | 群组信息表           |
| 组织用户信息     | act_id_info              | 用户扩展信息表         |
| 组织用户信息     | act_id_membership        | 用户群组关系表         |
| 组织用户信息     | act_id_tenant            | 租户信息表           |
| 组织用户信息     | act_id_tenant_member     | 用户租户关系表         |
| 组织用户信息     | act_id_user              | 用户信息表           |
| 流程历史记录     | act_hi_actinst           | 历史的活动实例表        |
| 流程历史记录     | act_hi_attachment        | 历史的流程附件表        |
| 流程历史记录     | act_hi_batch             | 历史的批处理记录表       |
| 流程历史记录     | act_hi_caseactinst       | 历史的CMMN活动实例表    |
| 流程历史记录     | act_hi_caseinst          | 历史的CMMN实例表      |
| 流程历史记录     | act_hi_comment           | 历史的流程审批意见表      |
| 流程历史记录     | act_hi_dec_in            | 历史的DMN变量输入表     |
| 流程历史记录     | act_hi_dec_out           | 历史的DMN变量输出表     |
| 流程历史记录     | act_hi_decinst           | 历史的DMN实例表       |
| 流程历史记录     | act_hi_detail            | 历史的流程运行时变量详情记录表 |
| 流程历史记录     | act_hi_ext_task_log      | 历史的流程任务消息执行表    |
| 流程历史记录     | act_hi_identitylink      | 历史的流程运行过程中用户关系  |
| 流程历史记录     | act_hi_incident          | 历史的流程异常事件记录表    |
| 流程历史记录     | act_hi_job_log           | 历史的流程作业记录表      |
| 流程历史记录     | act_hi_op_log            | 待确定             |
| 流程历史记录     | act_hi_procinst          | 历史的流程实例         |
| 流程历史记录     | act_hi_taskinst          | 历史的任务实例         |
| 流程历史记录     | act_hi_varinst           | 历史的流程变量记录表      |
| 流程通用数据     | act_ge_bytearray         | 流程引擎二进制数据表      |
| 流程通用数据     | act_ge_property          | 流程引擎属性配置表       |
| 流程通用数据     | act_ge_schema_log        | 数据库脚本执行日志表      |


## 自定义扩展表


| 分类             | 表名称                   | 描述            |
|------------------|--------------------------|---------------|
| 自定义扩展     | open_api          | CMMN案例管理模型定义表 |
| 自定义扩展     | work_flow_tenant          | 业务租户信息        |
| 自定义扩展     | work_flow_form_model          | 表单信息          |
| 自定义扩展     | work_flow_form_model_info          | 表单关联模型信息      |
| 自定义扩展     | work_flow_model          | 模型信息（BPMN）    |
| 自定义扩展     | work_flow_model_info          | 模型扩展信息        |
| 自定义扩展     | work_flow_model_category          | 模型分类信息        |
| 自定义扩展     | work_flow_model_deployment          | 模型部署信息        |
| 自定义扩展     | work_flow_service          | 流程运行实例        |
| 自定义扩展     | work_flow_job_log          | 定时任务日志        |

## 部署方式

### 1.源码打包（dev/uat/test/prod）

>  mvn clean install -Dmaven.test.skip=true -P uat

#### Window 版本

process-admin-web-starter-1.0.0-win.zip（admin管理后台）

process-center-server-starter-1.0.0-win.zip（流程引擎执行服务）

#### Linux 版本

process-admin-web-starter-1.0.0-linux.tar.gz（admin管理后台）

process-center-server-starter-1.0.0-linux.tar.gz（流程引擎执行服务）


### 2.解压文件 process-center-server-starter-1.0.0-linux.tar.gz

~~~
process-center-server-starter-1.0.0 
├── config         
├───── application.yml #服务配置
├───── application-druid.yml  #数据库
├───── logback.xml #日志
├── database
├───── 1_mysql_engine_7.21.0.sql
├───── 2_mysql_identity_7.21.0.sql
├───── 3_process-engine.sql
├───── 4_process-engine-data.sql
├── lib 
├───── *.jar
├── logs
├── process-center-server-starter-1.0.0.jar
├── startup.sh
~~~

### 2.数据库脚步构建

> 1.新建引擎数据库，如：pansome_flow_form_uat
> 
> 2.执行sql脚步：按照前缀编码顺序执行。目录：database
>

### 3.服务配置及启动

> 1.数据库及程序配置。目录 config
> 
> 2.执行启动脚步 startup.sh start
> 
> 3.检查启动日志 process-center-server-starter.log











