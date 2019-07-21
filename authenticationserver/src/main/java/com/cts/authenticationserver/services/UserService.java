package com.cts.authenticationserver.services;

import com.cts.authenticationserver.exception.UserAlreadyExistsException;
import com.cts.authenticationserver.exception.UserNotFoundException;
import com.cts.authenticationserver.model.User;

public interface UserService {
	
	boolean saveUser(User user) throws UserAlreadyExistsException;
	
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;
}
