package com.kanven.schedual.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kanven.schedual.web.common.WebConstants;

@RestController
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(WebConstants.FORWARD + "/index.jsp");
		return mav;
	}

}
