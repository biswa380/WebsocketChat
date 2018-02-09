package com.sdrc.websocket.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdrc.websocket.domain.PrivateSession;
import com.sdrc.websocket.domain.UserDetails;
import com.sdrc.websocket.model.SessionModel;
import com.sdrc.websocket.model.UserModel;
import com.sdrc.websocket.repository.PrivateSessionRepository;
import com.sdrc.websocket.repository.UserDetailsRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDetailsRepository userDetailsRepository;
	
	@Autowired
	public PrivateSessionRepository privateSessionRepository;
	
	@Transactional
	@Override
	public UserDetails register(String name, String email, String pwd) {
		// TODO Auto-generated method stub
		UserDetails user = new UserDetails();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(pwd);
		user.setUserName(email.split("@")[0]);
		
		return userDetailsRepository.save(user);
	}

	@Override
	public List<UserModel> getUserList() {
		// TODO Auto-generated method stub
		List<UserModel> userlist=new ArrayList<UserModel>();
		List<UserDetails>users=userDetailsRepository.findAll();
		System.out.println("User list length : "+users.size());
		for (UserDetails userDetails : users) {
			UserModel userModel = new UserModel();
			userModel.setUserId(userDetails.getUserId());
			userModel.setUserName(userDetails.getUserName());
			userModel.setEmail(userDetails.getEmail());
			userModel.setLive(userDetails.getLive());
			userModel.setPassword(userDetails.getPassword());
			
			userlist.add(userModel);
		}
		
		return userlist;
	}

	@Override
	public SessionModel getSession(String client1, String client2) {
		// TODO Auto-generated method stub
		PrivateSession session=privateSessionRepository.getSession(client1, client2);
		SessionModel sessionModel=new SessionModel();
		if(session==null) {
			session=new PrivateSession();
			session.setClient1(userDetailsRepository.findByUserName(client1));
			session.setClient2(userDetailsRepository.findByUserName(client2));
			session.setSessionName(client1+'_'+client2);
			privateSessionRepository.save(session);
			
			sessionModel.setClient1(session.getClient1().getUserName());
			sessionModel.setClient2(session.getClient2().getUserName());
			sessionModel.setSessionName(session.getSessionName());
			sessionModel.setSessionId(session.getSessionId());
		}
		else {
			sessionModel.setClient1(session.getClient1().getUserName());
			sessionModel.setClient2(session.getClient2().getUserName());
			sessionModel.setSessionName(session.getSessionName());
			sessionModel.setSessionId(session.getSessionId());
		}
		return sessionModel;
	}

}
