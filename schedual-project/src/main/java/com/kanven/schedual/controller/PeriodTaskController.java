package com.kanven.schedual.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kanven.schedual.entity.PeriodTask;
import com.kanven.schedual.exception.SchedualException;
import com.kanven.schedual.service.PeriodTaskService;
import com.kanven.schedual.web.common.WebConstants;

@RestController
@RequestMapping(value = "/period")
public class PeriodTaskController {

	private static final Logger log = LoggerFactory.getLogger(PeriodTaskController.class);

	@Autowired
	private PeriodTaskService periodTaskService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(WebConstants.FORWARD + "/task-add.jsp");
		return mav;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<PeriodTask> getTask(@PathVariable Long id) {
		ResponseEntity<PeriodTask> response = null;
		try {
			PeriodTask task = periodTaskService.get(id);
			if (task == null) {
				response = new ResponseEntity<PeriodTask>(HttpStatus.NO_CONTENT);
			} else {
				response = new ResponseEntity<PeriodTask>(task, HttpStatus.OK);
			}
		} catch (SchedualException e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<PeriodTask>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("周期任务(" + id + ")获取失败！", e);
			response = new ResponseEntity<PeriodTask>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Boolean> addTask(@RequestBody PeriodTask task) {
		HttpStatus status;
		boolean result = false;
		try {
			Long id = periodTaskService.addTask(task);
			if (id == null || id <= 0) {
				status = HttpStatus.NOT_MODIFIED;
			} else {
				status = HttpStatus.OK;
				result = true;
			}
		} catch (SchedualException e) {
			status = HttpStatus.BAD_REQUEST;
			log.error("周期任务新增出现异常！", e);
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			log.error("周期任务新增出现异常", e);
		}
		ResponseEntity<Boolean> respone = new ResponseEntity<Boolean>(result, status);
		return respone;
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
		ResponseEntity<Boolean> response = null;
		try {
			PeriodTask task = new PeriodTask();
			task.setId(id);
			periodTaskService.delTask(task);
			response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (SchedualException e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("周期任务(" + id + ")信息删除失败！", e);
			response = new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
	public ResponseEntity<List<PeriodTask>> findTasks(@RequestBody PeriodTask task, @PathVariable int page,
			@PathVariable int size) {
		ResponseEntity<List<PeriodTask>> response = null;
		List<PeriodTask> tasks = null;
		try {
			tasks = periodTaskService.findTasksByPage(task, page, size);
			if (task == null || tasks.size() <= 0) {
				response = new ResponseEntity<List<PeriodTask>>(HttpStatus.NO_CONTENT);
			} else {
				response = new ResponseEntity<List<PeriodTask>>(tasks, HttpStatus.OK);
			}
		} catch (SchedualException e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<List<PeriodTask>>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("周期任务分页获取出现异常！", e);
			response = new ResponseEntity<List<PeriodTask>>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "/pause/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> pauseTask(@PathVariable Long id) {
		ResponseEntity<Boolean> response = null;
		try {
			PeriodTask task = new PeriodTask();
			task.setId(id);
			periodTaskService.pauseTask(task);
			response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (SchedualException e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("周期任务（" + id + "）暂停失败！", e);
			response = new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "/recove/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> recoveTask(@PathVariable Long id) {
		ResponseEntity<Boolean> response = null;
		try {
			PeriodTask task = new PeriodTask();
			task.setId(id);
			periodTaskService.recoveTask(task);
			response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (SchedualException e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("周期任务(" + id + ")恢复失败！", e);
			response = new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

}
