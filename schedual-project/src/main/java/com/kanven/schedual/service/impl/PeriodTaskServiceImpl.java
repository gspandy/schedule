package com.kanven.schedual.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kanven.schedual.dao.PeriodTaskDao;
import com.kanven.schedual.entity.PeriodTask;
import com.kanven.schedual.entity.PeriodTask.TaskStatus;
import com.kanven.schedual.exception.SchedualException;
import com.kanven.schedual.service.Constants;
import com.kanven.schedual.service.PeriodTaskService;

@Component
public class PeriodTaskServiceImpl implements PeriodTaskService {

	@Autowired
	private PeriodTaskDao periodTaskDao;

	public PeriodTask get(Long id) throws SchedualException {
		if (id == null || id <= 0) {
			return null;
		}
		try {
			return periodTaskDao.get(id);
		} catch (Exception e) {
			throw new SchedualException("任务(" + id + ")获取失败！", e);
		}
	}

	public List<PeriodTask> findTasksByPage(PeriodTask task, int page, int size) throws SchedualException {
		if (page <= 0) {
			page = Constants.DEFAULT_PAGE;
		}
		if (size <= 0) {
			size = Constants.DEFAULT_PAGE_SIZE;
		}
		try {
			return periodTaskDao.findTasksByPage(task, (page - 1) * size, size);
		} catch (Exception e) {
			throw new SchedualException("分页获取定时任务列表失败！", e);
		}
	}

	public Long addTask(PeriodTask task) throws SchedualException {
		check(task);
		try {
			if (periodTaskDao.findTask(task) != null) {
				throw new SchedualException("任务已经存在！");
			}
			task.setTaskStatus(TaskStatus.DEFAULT.value());
			task.setCreateTime(new Date());
			task.setUpdateTime(new Date());
			task.setUpdateUser(task.getCreateUser());
			return periodTaskDao.save(task);
		} catch (Exception e) {
			throw new SchedualException("新增定时任务出现异常！", e);
		}
	}

	public void pauseTask(PeriodTask task) throws SchedualException {
		checkModify(task);
		PeriodTask periodTask = new PeriodTask();
		periodTask.setId(task.getId());
		periodTask.setTaskStatus(TaskStatus.PAUSE.value());
		periodTask.setUpdateUser(task.getUpdateUser());
		periodTask.setUpdateTime(new Date());
		try {
			periodTaskDao.update(periodTask);
		} catch (Exception e) {
			throw new SchedualException("任务(" + task.getId() + ")暂停失败！", e);
		}
	}

	public void recoveTask(PeriodTask task) throws SchedualException {
		checkModify(task);
		PeriodTask periodTask = new PeriodTask();
		periodTask.setId(task.getId());
		periodTask.setTaskStatus(TaskStatus.EXACTOR.value());
		periodTask.setUpdateUser(task.getUpdateUser());
		periodTask.setUpdateTime(new Date());
		try {
			periodTaskDao.update(periodTask);
		} catch (Exception e) {
			throw new SchedualException("任务(" + task.getId() + ")恢复失败！", e);
		}
	}

	public void delTask(PeriodTask task) throws SchedualException {
		checkModify(task);
		PeriodTask periodTask = new PeriodTask();
		periodTask.setId(task.getId());
		periodTask.setTaskStatus(TaskStatus.CANCEL.value());
		periodTask.setUpdateUser(task.getUpdateUser());
		periodTask.setUpdateTime(new Date());
		try {
			periodTaskDao.update(periodTask);
		} catch (Exception e) {
			throw new SchedualException("任务(" + task.getId() + ")暂停失败！", e);
		}
	}

	private void check(PeriodTask task) throws SchedualException {
		if (task == null) {
			throw new SchedualException("任务不能为空！");
		}
		if (StringUtils.isEmpty(task.getProjectName())) {
			throw new SchedualException("没有指定项目名称！");
		}
		if (StringUtils.isEmpty(task.getGroupName())) {
			throw new SchedualException("没有指定分组！");
		}
		if (StringUtils.isEmpty(task.getTaskName())) {
			throw new SchedualException("没有指定任务名！");
		}
		if (StringUtils.isEmpty(task.getUrl())) {
			throw new SchedualException("没有指定定时任务调用地址！");
		}
		if (StringUtils.isEmpty(task.getCron())) {
			throw new SchedualException("没有指定cron表达式！");
		}
		if (StringUtils.isEmpty(task.getCreateUser())) {
			throw new SchedualException("没有指定创建人！");
		}
	}

	private void checkModify(PeriodTask task) throws SchedualException {
		if (task == null) {
			throw new SchedualException("定时任务参数为空！");
		}
		Long id = task.getId();
		if (id == null || id <= 0) {
			throw new SchedualException("任务编号不能为空或负数！");
		}
		String updateUser = task.getUpdateUser();
		if (StringUtils.isEmpty(updateUser)) {
			throw new SchedualException("没有指定修改人信息！");
		}
	}

}
