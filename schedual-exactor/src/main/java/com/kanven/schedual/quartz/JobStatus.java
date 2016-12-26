package com.kanven.schedual.quartz;

import java.util.Date;

/**
 * 
 * 任务执行状态信息
 * 
 * @author kanven
 *
 */
public class JobStatus {

	private Long id;

	private String group;

	private String name;

	private String url;

	private Date startTime;

	private Date endTime;

	private Status status = Status.FAILURE;

	private Throwable throwable;

	private String result;

	public static enum Status {
		SUCCESS(200), FAILURE(400);

		private int status;

		private Status(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}
	}

	public JobStatus() {
		this.startTime = new Date(System.currentTimeMillis());
	}

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

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void finish() {
		this.endTime = new Date(System.currentTimeMillis());
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
