package com.kanven.schedual.quartz;

/**
 * 任务配置信息
 * 
 * @author kanven
 *
 */
public class JobConfig {
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
	 * 延期时间
	 */
	private long delay = 0;

	private long interval = 0;

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

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}
