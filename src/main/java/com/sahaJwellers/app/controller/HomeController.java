<<<<<<< HEAD
package com.sahaJwellers.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(path= {"/","/index"})
	public String index() {
		return "index.html";
	}
}
=======
package com.sahaJwellers.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(path= {"/","/index"})
	public String index() {
		return "index.html";
	}
}
>>>>>>> e5a3f8cc8bbf944746bb398b37e879e387199c5d
