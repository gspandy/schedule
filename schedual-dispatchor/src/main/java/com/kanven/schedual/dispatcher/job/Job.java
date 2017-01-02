package com.kanven.schedual.dispatcher.job;

import java.util.Date;

public class Job {

	private Long id;

	private String group;

	private String name;

	private String url;

	private String cron;

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

	@Override
	public String toString() {
		return "Job [id=" + id + ", group=" + group + ", name=" + name + ", url=" + url + ", cron=" + cron
				+ ", startTime=" + startTime + "]";
	}

}
