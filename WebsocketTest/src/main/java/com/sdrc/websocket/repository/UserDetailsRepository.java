package com.sdrc.websocket.repository;


import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.sdrc.websocket.domain.UserDetails;

@RepositoryDefinition(idClass=Integer.class,domainClass=UserDetails.class)
public interface UserDetailsRepository {

	UserDetails save(UserDetails user);
	
	List<UserDetails> findAll();
	
	UserDetails findByUserName(String username);
	
}
