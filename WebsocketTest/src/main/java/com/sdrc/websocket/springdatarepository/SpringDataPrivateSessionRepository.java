package com.sdrc.websocket.springdatarepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.sdrc.websocket.domain.PrivateSession;
import com.sdrc.websocket.repository.PrivateSessionRepository;

public interface SpringDataPrivateSessionRepository 
//extends PrivateSessionRepository, Repository<PrivateSession, Integer>
{
	
//	@Override
//	@Query("select ps from PrivateSession ps where (ps.client1=:client1 and ps.client2=:client2) or (ps.client1=:client2 and ps.client2=:client1)")
//	PrivateSession getSession(@Param("client1")String client1, @Param("client2")String client2);

}
