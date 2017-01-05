package com.kanven.schedual.test.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kanven.schedual.dao.PeriodTaskDao;
import com.kanven.schedual.entity.PeriodTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-mvc.xml",
		"classpath:spring/applicationContext-*.xml" })
public class PeriodTaskDaoTest {

	@Autowired
	private PeriodTaskDao periodTaskDao;

	@Test
	public void testSave() {
		PeriodTask task = new PeriodTask();
		task.setProjectName("schedual");
		task.setTaskName("task");
		task.setGroupName("schedual");
		task.setUrl("https://www.baidu.com");
		task.setCron("ttt");
		task.setStartTime(new Date());
		task.setTaskStatus(1);
		task.setCreateTime(new Date());
		task.setCreateUser("kanven");
		task.setUpdateTime(new Date());
		task.setUpdateUser("kanven");
		System.out.println(periodTaskDao.save(task));
	}

	@Test
	public void testGet() {
		PeriodTask task = periodTaskDao.get(1L);
		Assert.assertNotNull(task);
	}

	@Test
	public void testUpdate() {
		PeriodTask task = new PeriodTask();
		task.setId(1L);
		task.setUpdateTime(new Date());
		task.setUpdateUser("tom");
		periodTaskDao.update(task);
	}

	@Test
	public void testDelete() {
		periodTaskDao.delete(1L);
	}

}
