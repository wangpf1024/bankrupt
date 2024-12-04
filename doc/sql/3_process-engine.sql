

DROP TABLE IF EXISTS `open_api`;

CREATE TABLE `open_api` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `access_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标示',
  `access_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密钥',
  `status` int DEFAULT '0' COMMENT '状态值(0-上线 1-下线)',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='OpenApi管理';



DROP TABLE IF EXISTS `work_flow_form_model`;

CREATE TABLE `work_flow_form_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `form_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表单唯一标识Key：用于关联流程',
  `form_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '表单配置json',
  `rule_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '表单规则配置json',
  `option_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '表单选项配置json',
  `form_type` tinyint(1) DEFAULT '0' COMMENT '状态值-0：静态表单， 1：动态表单',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态1-未发布，2-已发布',
  `main_version` tinyint(1) DEFAULT '0' COMMENT '是否为主版本',
  `config_json` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '表单表头字段配置',
  `release_note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '版本说明',
  `version` int DEFAULT '1' COMMENT '版本号',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_unique_form_key` (`form_key`,`version`) USING BTREE
) ENGINE=InnoDB COMMENT='表单模型';

DROP TABLE IF EXISTS `work_flow_form_model_info`;

CREATE TABLE `work_flow_form_model_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表单模型信息主键',
  `model_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模型id',
  `category_id` bigint DEFAULT NULL COMMENT '表单分类ID',
  `category_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表单分类名称',
  `form_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表单名称',
  `form_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主版本formKey=》用于关联工作流',
  `version` int DEFAULT '1' COMMENT '主版本版本号',
  `form_model_type` tinyint(1) DEFAULT '0' COMMENT '默认：0-外部表单 1-自定义表单',
  `tenant_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统标识-租户ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='表单信息';

DROP TABLE IF EXISTS `work_flow_job_log`;

CREATE TABLE `work_flow_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='任务执行器日志';


DROP TABLE IF EXISTS `work_flow_model`;

CREATE TABLE `work_flow_model` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `process_def_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程定义id',
  `model_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型Key',
  `model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `model_json` json DEFAULT NULL COMMENT '流程json数据',
  `model_xml` longtext COLLATE utf8mb4_general_ci COMMENT '流程xml数据',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名',
  `version` int DEFAULT '1' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `un_model_key_version` (`model_key`,`version`) USING BTREE COMMENT '唯一索引，防止并发新增出现相同版本号'
) ENGINE=InnoDB COMMENT='流程模型';


DROP TABLE IF EXISTS `work_flow_model_category`;

CREATE TABLE `work_flow_model_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '分类名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '分类编码',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `parent_id` bigint DEFAULT '0' COMMENT '上级分类id',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `open_api_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'open_api.access_name',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='流程分类';


DROP TABLE IF EXISTS `work_flow_model_deployment`;

CREATE TABLE `work_flow_model_deployment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `model_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模型IDid',
  `model_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型Key',
  `model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `model_json` json DEFAULT NULL COMMENT '流程json数据',
  `model_xml` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '流程xml数据',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名',
  `version` int DEFAULT '1' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='部署流程';


DROP TABLE IF EXISTS `work_flow_model_info`;

CREATE TABLE `work_flow_model_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `model_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模型id',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模型名称',
  `model_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模型key',
  `model_type` int DEFAULT '0' COMMENT '模型类型: 0 自定义流程 1 是业务流程',
  `category_id` bigint DEFAULT NULL COMMENT '分类id',
  `status` int DEFAULT '0' COMMENT '流程图Model状态',
  `apply_company_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '适用租户',
  `apply_project_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '适用项目',
  `function_range` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '功能范围(1 跳过已通过)',
  `order_num` int DEFAULT '0' COMMENT '排序',
  `tenant_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统标识-租户ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `definition_id` varchar(128) DEFAULT NULL COMMENT '流程定义ID',
  `definition_key` varchar(128) DEFAULT NULL COMMENT '流程KEY',
  `deployment_id` varchar(128) DEFAULT NULL COMMENT '流程部署ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='流程信息';



DROP TABLE IF EXISTS `work_flow_service`;

CREATE TABLE `work_flow_service` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '服务id',
  `service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务名称',
  `service_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务编码',
  `icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标图片',
  `type` tinyint DEFAULT NULL COMMENT '服务类型，1：内部表单服务；2，外部调用服务',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '是否发布，0：不发布，1：发布',
  `open_way` tinyint DEFAULT '0' COMMENT '打开方式,0-当前页面,1-新窗口打开',
  `order_num` int DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `tenant_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统标识-租户ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `definition_id` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程定义ID',
  `definition_key` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程KEY',
  `deployment_id` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程部署ID',
  `apply_company_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '适用租户',
  `apply_project_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '适用项目',
  `model_info_id` bigint DEFAULT NULL COMMENT '流程信息ID',
  `definition_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程定义名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='流程执行服务';


DROP TABLE IF EXISTS `work_flow_tenant`;

CREATE TABLE `work_flow_tenant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标示',
  `tenant_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `status` int DEFAULT '0' COMMENT '租户状态值(0-上线 1-下线)',
  `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户密钥',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='租户';
