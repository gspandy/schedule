package com.kanven.schedual.service;

import java.util.List;

import com.kanven.schedual.entity.PeriodTask;
import com.kanven.schedual.exception.SchedualException;

public interface PeriodTaskService {

	/**
	 * 获取指定的定时任务
	 * 
	 * @param id
	 * @return
	 * @throws SchedualException
	 */
	PeriodTask get(Long id) throws SchedualException;

	/**
	 * 分页获取定时任务
	 * 
	 * @param task
	 * @param page
	 *            页码
	 * @param size
	 *            每页大小
	 * @return
	 * @throws SchedualException
	 */
	List<PeriodTask> findTasksByPage(PeriodTask task, int page, int size) throws SchedualException;

	/**
	 * 新增定时任务
	 * 
	 * @param Task
	 * @return
	 * @throws SchedualException
	 */
	Long addTask(PeriodTask task) throws SchedualException;

	/**
	 * 暂停定时任务
	 * 
	 * @param task
	 * @throws SchedualException
	 */
	void pauseTask(PeriodTask task) throws SchedualException;

	/**
	 * 恢复定时任务
	 * 
	 * @param task
	 * @throws SchedualException
	 */
	void recoveTask(PeriodTask task) throws SchedualException;

	/**
	 * 取消定时任务
	 * 
	 * @param task
	 * @throws SchedualException
	 */
	void delTask(PeriodTask task) throws SchedualException;

}
