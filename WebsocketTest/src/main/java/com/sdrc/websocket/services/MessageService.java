package com.sdrc.websocket.services;

import java.util.List;

import com.sdrc.websocket.model.Message;

public interface MessageService {
public List<Message> getMessages(Integer sessionId); 

public void saveMessage(Message message);
}
