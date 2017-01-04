package com.kanven.schedual.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("report")
public class TaskReport implements Serializable {

	private static final long serialVersionUID = -5106756958412762892L;

	private Long id;

	/**
	 * 任务编号
	 */
	private Long taskId;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "TaskReport [id=" + id + ", taskId=" + taskId + ", ip=" + ip + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

}
