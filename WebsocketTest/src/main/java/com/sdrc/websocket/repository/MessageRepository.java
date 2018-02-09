package com.sdrc.websocket.repository;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.sdrc.websocket.domain.MessageDetails;

@RepositoryDefinition(idClass=Integer.class,domainClass=MessageDetails.class)
public interface MessageRepository {
	List<MessageDetails> findBySessionIdSessionId(Integer sessionId);
	
	@Transactional
	public void save(MessageDetails messageDetails);
}
