package com.suncompass.plateform.sys.entity;

import com.suncompass.plateform.kernel.entity.AbstractDataEntity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 日志Entity
 * @author alex
 * @version 2017-11-29
 */
@Entity(name = "sys_log")
public class Log extends AbstractDataEntity<Log> {
	private static final long serialVersionUID = 1L;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 日志类型（0：接入日志；1：错误日志）
	 */
	private String type;
	/**
	 * 日志标题
	 */
	private String title;
	/**
	 * 操作用户的IP地址
	 */
	private String remoteAddr;
	/**
	 * 	操作的URI
	 */
	private String requestUri;
	/**
	 * 操作的方式
	 */
	private String method;
	/**
	 * 操作提交的数据
	 */
	private String params;
	/**
	 * 操作用户代理信息
	 */
	private String userAgent;
	/**
	 * 异常信息
	 */
	private String exception;

	/**
	 * 开始日期
	 */
	private Date beginDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	
	// 日志类型
	public static final String TYPE_ACCESS = "0";
	public static final String TYPE_EXCEPTION = "1";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}