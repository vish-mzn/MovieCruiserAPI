package com.cts.authenticationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.authenticationserver.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	User findByUserIdAndPassword(String userId, String password);
}
