package com.shop.controllers.administratorSite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("administratorSite")
public class AdministratorSite {
	
	@RequestMapping
	public String start() {
		return "administratorStartPage";
	}
}
