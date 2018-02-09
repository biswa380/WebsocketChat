package com.sdrc.websocket.model;

import com.sdrc.websocket.domain.UserDetails;

public class SessionModel {

	private Integer sessionId;
	
	private String sessionName;
	
	private String client1;
	
	private String client2;

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getClient1() {
		return client1;
	}

	public void setClient1(String client1) {
		this.client1 = client1;
	}

	public String getClient2() {
		return client2;
	}

	public void setClient2(String client2) {
		this.client2 = client2;
	}
	
	
}
