package com.sdrc.websocket.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Message_Details")
public class MessageDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", columnDefinition = "serial")
	private Integer messageId;
	
	@Column(name="message_text")
	private String messageText;
	
	@ManyToOne
	@JoinColumn(name="sender")
	private UserDetails sender;
	
	@ManyToOne
	@JoinColumn(name="reciever")
	private UserDetails reciever;
	
	@Column(name="sent_time")
	private Timestamp sentTime;
	
	@ManyToOne
	@JoinColumn(name="session_id")
	private PrivateSession sessionId;
	
	@Column(name="attachment_link")
	private String attachmentLink;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public UserDetails getSender() {
		return sender;
	}

	public void setSender(UserDetails sender) {
		this.sender = sender;
	}

	public UserDetails getReciever() {
		return reciever;
	}

	public void setReciever(UserDetails reciever) {
		this.reciever = reciever;
	}

	public Timestamp getSentTime() {
		return sentTime;
	}

	public void setSentTime(Timestamp sentTime) {
		this.sentTime = sentTime;
	}

	public String getAttachmentLink() {
		return attachmentLink;
	}

	public void setAttachmentLink(String attachmentLink) {
		this.attachmentLink = attachmentLink;
	}

	public PrivateSession getSessionId() {
		return sessionId;
	}

	public void setSessionId(PrivateSession sessionId) {
		this.sessionId = sessionId;
	}
	
	
}
