package com.kanven.schedual.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kanven.schedual.entity.PeriodTask;

public interface PeriodTaskDao extends BaseDao<PeriodTask> {

	/**
	 * 根据项目名／分组／任务名获取定时任务
	 * 
	 * @param task
	 * @return
	 */
	PeriodTask findTask(PeriodTask task);

	/**
	 * 分页获取定时任务
	 * 
	 * @param task
	 * @param start
	 *            开始行
	 * @param size
	 *            数量
	 * @return
	 */
	List<PeriodTask> findTasksByPage(PeriodTask task, @Param("start") int start, @Param("size") int size);

}
