package com.sdrc.websocket;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdrc.websocket.model.UserModel;
import com.sdrc.websocket.services.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	public UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/chatPage", method = RequestMethod.GET)
	public String chatHome() {
		return "chatPage";
	}
	
	@ResponseBody
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(@RequestParam("name")String name, @RequestParam("newemail")String email, @RequestParam("newpwd")String pwd) {
		System.out.println(userService.register(name,email,pwd).getUserId());
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="/userlist")
	public List<UserModel> getUserList() {
		List<UserModel> userlist=userService.getUserList();
		return userlist;
	}
}
