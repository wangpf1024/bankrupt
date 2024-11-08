# Bankrupt Workflow

后端基于 Camunda 7.21 二次开发

> Still working on coding ,soner or later will update the README file. Thinks

## 系统模块
~~~
├── process-common            // 核心系统-通用模块
├── process-framework             //  核心系统-Spring BOOT框架
├── process-orm         // 数据库ORM
├────── process-center-orm  // 核心系统-核心模块ORM
├────── process-idm-orm     // 用户管理-ORM
├────── process-model-orm   //流程模型管理-ORM
├────── process-modeling-orm //动态表单管理-ORM
├────── process-scheduled-orm   //定时任务-ORM 
├── process-start         // ResfultAPI
├────── process-admin-web-start         // 执行流程adminweb平台 
├────── process-center-server-starter  // 核心系统
├────── process-idm-server-starter  // 用户管理ORM
├────── process-model-server-starter         //流程模型管理
├────── process-modeling-server-starter     //动态表单管理
├────── process-scheduled-server-starter     //定时任务   
├── process-tools         // 系统工具
├────── process-cache         // 缓存模块
├────── process-engine-plugins   // 核心系统-Camunda扩展插件
├────── process-msg-subcriber   //消息订阅
├────── process-mybatis-generator      //生成数据库基础代码 
├────── process-open-api-sdk      //开放API  
~~~
