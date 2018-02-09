package com.sdrc.websocket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="private_session")
public class PrivateSession {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", columnDefinition = "serial")
	private Integer sessionId;
	
	@Column(name="name")
	private String sessionName;
	
	@ManyToOne
	@JoinColumn(name="client1")
	private UserDetails client1;
	
	@ManyToOne
	@JoinColumn(name="client2")
	private UserDetails client2;

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public UserDetails getClient1() {
		return client1;
	}

	public void setClient1(UserDetails client1) {
		this.client1 = client1;
	}

	public UserDetails getClient2() {
		return client2;
	}

	public void setClient2(UserDetails client2) {
		this.client2 = client2;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	
	
}
