package com.kanven.schedual.quartz;

import java.util.Date;

/**
 * 任务配置信息
 * 
 * @author kanven
 *
 */
public class JobConfig {
	/**
	 * 任务编号
	 */
	private Long id;
	/**
	 * 分组名称
	 */
	private String group;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 调用地址
	 */
	private String url;
	/**
	 * 表达式
	 */
	private String cron;

	/**
	 * 开始执行时间
	 */
	private Date startTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
