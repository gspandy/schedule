package com.kanven.schedual.dispatcher.job;

/**
 * 任务客户端
 * 
 * @author kanven
 *
 */
public interface JobClient {

	/**
	 * 添加任务
	 * 
	 * @param job
	 * @return
	 */
	boolean add(Job job);

	/**
	 * 暂停任务
	 * 
	 * @param job
	 * @return
	 */
	boolean pause(Job job);

	/**
	 * 恢复任务
	 * 
	 * @param job
	 * @return
	 */
	boolean recove(Job job);

	/**
	 * 删除任务
	 * 
	 * @param job
	 * @return
	 */
	boolean del(Job job);

}
