package com.sdrc.websocket.services;

import java.util.List;

import com.sdrc.websocket.domain.UserDetails;
import com.sdrc.websocket.model.SessionModel;
import com.sdrc.websocket.model.UserModel;


public interface UserService {

	public UserDetails register(String name, String email, String pwd);
	
	public List<UserModel> getUserList();
	
	public SessionModel getSession(String client1, String client2);

}
