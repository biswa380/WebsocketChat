package com.sdrc.websocket.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sdrc.websocket.model.UserModel;
import com.sdrc.websocket.services.UserService;

@Controller
public class LoginController implements AuthenticationProvider{

	@Autowired
	private UserService userService;
	
	
	@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST,produces="text/plain")
	public String login(HttpServletRequest request, RedirectAttributes redirectAttributes,@RequestParam("username")String username, @RequestParam("pwd")String pwd) {
		List<UserModel> users=userService.getUserList();
		String status="failed";
		for (UserModel userModel : users) {
			if(userModel.getUserName().equals(username) && userModel.getPassword().equals(pwd)) {
				status =  username;
			}
		}
		
		return status;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}
}
