/**
 * @filename:UserController 2019年4月9日
 * @project wallet-manage  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.mdg;

import java.sql.SQLException;
import java.util.Date;

import com.github.flying.cattle.mdg.entity.BasisInfo;
import com.github.flying.cattle.mdg.util.EntityInfoUtil;
import com.github.flying.cattle.mdg.util.Generator;
import com.github.flying.cattle.mdg.util.MySqlToJavaUtil;
/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 自动生成工具</P>
 * <p>源码地址：https://gitee.com/flying-cattle/mybatis-dsc-generator</P>
 */
public class MyGenerator {

		// 基础信息：项目名、作者、版本
		public static final String PROJECT = "workflow";
		public static final String AUTHOR = "王鹏飞";
		public static final String VERSION = "V1.0.0";

		// 数据库连接信息：连接URL、用户名、秘密、数据库名
		public static final String URL = "jdbc:mysql://192.168.10.110:33080/pansome_flow_form?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=UTC";
		public static final String NAME = "pansome";
		public static final String PASS = "pansome2022+";
		public static final String DATABASE = "pansome_flow_form";

		// 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间 ===
		// TODO update before run test
		public static final String MODULE_PACKAGE ="model";
		public static final String TABLE = "work_flow_model_deployment";
		public static final String CLASSCOMMENT = "部署流程";
		public static final String TIME = "2024年11月29日";
		public static final String AGILE = new Date().getTime() + "";


	    // 路径信息，分开路径方便聚合工程项目，微服务项目
		public static final String BAST_PROJECT_URL ="com.pansome.workflow."+MODULE_PACKAGE+".";
		public static final String ENTITY_URL = BAST_PROJECT_URL + "entity";
		public static final String DAO_URL = BAST_PROJECT_URL + "mapper";
		public static final String XML_URL = BAST_PROJECT_URL + "mapper.impl";
		public static final String SERVICE_URL = BAST_PROJECT_URL + "service";
		public static final String SERVICE_IMPL_URL = BAST_PROJECT_URL + "service.impl";
		public static final String CONTROLLER_URL = BAST_PROJECT_URL + "controller";
		//是否是Swagger配置
		public static final String IS_SWAGGER = "true";




	public static void main(String[] args) {
		BasisInfo bi = new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASS, DATABASE, TIME, AGILE, ENTITY_URL,
				DAO_URL, XML_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL,IS_SWAGGER);
		bi.setTable(TABLE);
		bi.setEntityName(MySqlToJavaUtil.getClassName(TABLE));
		bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(TABLE));
		bi.setEntityComment(CLASSCOMMENT);
		String module = "process-"+MODULE_PACKAGE+"-server-starter";
		String moduleORM = "process-"+MODULE_PACKAGE+"-orm";
		bi.setEndPoint("process-"+MODULE_PACKAGE+"-server");
		try {
			bi = EntityInfoUtil.getInfo(bi);
			// 生成文件存放位置
			String codeUrl = moduleORM +"/src/main/java/";
			String codeUrl2 = module +"/src/main/java/";
			String xmlUrl = moduleORM +"/src/main/resources/mapper/";
			//开始生成文件
			String aa1 = Generator.createEntity(codeUrl, bi).toString();
			String aa2 = Generator.createMapper(codeUrl, bi).toString();
			String aa3 = Generator.createMapperImpl(xmlUrl, bi).toString();
			String aa4 = Generator.createService(codeUrl, bi).toString();
			String aa5 = Generator.createServiceImpl(codeUrl, bi).toString();
			String aa6 = Generator.createController(codeUrl2, bi).toString();
			// 是否创建swagger配置文件
			//String aa7 = Generator.createSwaggerConfig(fileUrl, bi).toString();
			System.out.println(aa1);
			System.out.println(aa2);
			System.out.println(aa3);
			System.out.println(aa4);
			System.out.println(aa5);
			//System.out.println(aa6);
			//System.out.println(aa7);
			//System.out.println(aa7);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
