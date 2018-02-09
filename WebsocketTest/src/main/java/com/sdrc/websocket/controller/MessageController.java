package com.sdrc.websocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdrc.websocket.model.Message;
import com.sdrc.websocket.model.OutputMessage;
import com.sdrc.websocket.model.SessionModel;
import com.sdrc.websocket.services.MessageService;
import com.sdrc.websocket.services.UserService;

@Controller
public class MessageController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public OutputMessage send(Message message) throws Exception {
	    String time = new SimpleDateFormat("HH:mm").format(new Date());
	    return new OutputMessage(message.getFrom(), message.getText(), time);
	}
	
	@MessageMapping("/chat.private.{username}")
	@SendTo("/topic/privateMessage/{username}")
	public OutputMessage filterPrivateMessage(@Payload Message message, @DestinationVariable("username") String username) {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		message.setTo(username);
		messageService.saveMessage(message);
		return new OutputMessage(message.getFrom(), username, message.getText(), time);
	}
	
	@RequestMapping("getSession")
	@ResponseBody
	public SessionModel getSession(@RequestParam("from")String client1,@RequestParam("to")String client2) {
		SessionModel sessionModel = userService.getSession(client1, client2);
		return sessionModel;
	}
	
	@RequestMapping("getMessages")
	@ResponseBody
	public List<Message> getMessages(Integer sessionId){
		return messageService.getMessages(sessionId);
	}
}
