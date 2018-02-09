package com.sdrc.websocket.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sdrc.websocket.domain.PrivateSession;

@RepositoryDefinition(idClass=Integer.class,domainClass=PrivateSession.class)
public interface PrivateSessionRepository {

	@Transactional
	void save(PrivateSession session);
	
	@Query("select ps from PrivateSession ps where (ps.client1.userName=:client1 and ps.client2.userName=:client2) or (ps.client1.userName=:client2 and ps.client2.userName=:client1)")
	PrivateSession getSession(@Param("client1")String client1, @Param("client2")String client2);
}
