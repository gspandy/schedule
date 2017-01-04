package com.kanven.schedual.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 周期任务实体类
 * 
 * @author kanven
 *
 */
@Alias("period")
public class PeriodTask implements Serializable {

	private static final long serialVersionUID = -6994016113690721456L;

	/**
	 * 任务编号
	 */
	private Long id;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 组别名称
	 */
	private String groupName;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 调用地址
	 */
	private String url;

	/**
	 * 表达式
	 */
	private String cron;

	/**
	 * 任务开始执行时间
	 */
	private Date startTime;

	/**
	 * 任务状态
	 */
	private Integer taskStatus;

	/**
	 * 任务创建时间
	 */
	private Date createTime;

	/**
	 * 任务创建人
	 */
	private String createUser;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 任务更新人
	 */
	private String updateUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String toString() {
		return "PeriodTask [id=" + id + ", projectName=" + projectName + ", groupName=" + groupName + ", taskName="
				+ taskName + ", url=" + url + ", cron=" + cron + ", startTime=" + startTime + ", taskStatus="
				+ taskStatus + ", createTime=" + createTime + ", createUser=" + createUser + ", updateTime="
				+ updateTime + ", updateUser=" + updateUser + "]";
	}

}
