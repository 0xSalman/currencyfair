package com.lucidspring.currencyfair.controller;

import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to display login page
 */

@Controller
public class LoginController {

	@RequestMapping(value = "/login")
	public String loginPage() {
        LoggerUtil.logDebug(IndexController.class, "Requested login page");
		return "login";
	}
}
