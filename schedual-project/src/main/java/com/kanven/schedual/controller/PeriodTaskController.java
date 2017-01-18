package com.kanven.schedual.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kanven.schedual.entity.PeriodTask;
import com.kanven.schedual.web.common.WebConstants;

@RestController
@RequestMapping(value = "/period")
public class PeriodTaskController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(WebConstants.FORWARD + "/task-add.jsp");
		return mav;
	}

	@RequestMapping(value="/get/{id}")
	public ResponseEntity<PeriodTask> getTask(@PathVariable Long id) {
		PeriodTask task = new PeriodTask();
		ResponseEntity<PeriodTask> response = new ResponseEntity<PeriodTask>(task, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Integer> addTask(@RequestBody PeriodTask task) {
		ResponseEntity<Integer> respone = new ResponseEntity<Integer>(1, HttpStatus.OK);
		return respone;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<PeriodTask> updateTask(@RequestBody PeriodTask task) {
		ResponseEntity<PeriodTask> response = new ResponseEntity<PeriodTask>(task, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
		Boolean result = false;
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(result, HttpStatus.OK);
		return response;
	}

}
