package cn.etcom.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain=true)
public class SystemLog {

	private int id;

	private String requestip; // 操作IP
	private String url ;  //请求路径
	private String httpmethod ; //请求方式

	private String type;// 操作类型 1 操作记录 2异常记录

	private String userid;// 操作人ID

	private String description;// 操作描述

	private Date actiondate;// 操作时间

	private String exceptioncode;// 异常code

	private String exceptiondetail;// 异常详情

	private String actionmethod;// 请求方法

	private String params;// 请求参数
	
}
