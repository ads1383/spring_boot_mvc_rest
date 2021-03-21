package com.denisakulov.springboot.spring_boot_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {


	@GetMapping(value = "/")
	public String showIndex() {
		return "redirect:/user";
	}

	@GetMapping(value = "/user")
	public String showUser() {
		return "hello";
	}


	@GetMapping(value = "/admin")
	public String showAdmins() {
		return "index";
	}

	@GetMapping(value = "/login")
	public String getLoginPage() {
		return "login";
	}
}
