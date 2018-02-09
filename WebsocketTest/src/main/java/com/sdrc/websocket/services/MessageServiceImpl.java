package com.sdrc.websocket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdrc.websocket.domain.MessageDetails;
import com.sdrc.websocket.model.Message;
import com.sdrc.websocket.repository.MessageRepository;
import com.sdrc.websocket.repository.PrivateSessionRepository;
import com.sdrc.websocket.repository.UserDetailsRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	public UserDetailsRepository userDetailsRepository;
	
	@Autowired
	public PrivateSessionRepository privateSessionRepository;
	
	@Override
	public List<Message> getMessages(Integer sessionId) {
		// TODO Auto-generated method stub
		List<Message> messages=new ArrayList<Message>();
		List<MessageDetails> messageDetails = messageRepository.findBySessionIdSessionId(sessionId);
		for (MessageDetails messageDetail : messageDetails) {
			Message message=new Message();
			
			message.setFrom(messageDetail.getSender().getUserName());
			message.setTo(messageDetail.getReciever().getUserName());
			message.setText(messageDetail.getMessageText());
			message.setTime(String.valueOf(messageDetail.getSentTime()));
			
			messages.add(message);
		}
		return messages;
	}
	
	@Transactional
	@Override
	public void saveMessage(Message message) {
		// TODO Auto-generated method stub
		
		java.util.Date today = new java.util.Date();
		
		MessageDetails messageDetails = new MessageDetails();
		messageDetails.setMessageText(message.getText());
		messageDetails.setReciever(userDetailsRepository.findByUserName(message.getTo()));
		messageDetails.setSender(userDetailsRepository.findByUserName(message.getFrom()));
		messageDetails.setSessionId(privateSessionRepository.getSession(message.getTo(), message.getFrom()));
		messageDetails.setSentTime(new java.sql.Timestamp(today.getTime()));
		
		messageRepository.save(messageDetails);
	}

}
