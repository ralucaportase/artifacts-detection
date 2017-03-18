package eegApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

	@RequestMapping( value = "/", method = RequestMethod.GET)
	public String home()
	{
		return "home.html" ;
	}
}
