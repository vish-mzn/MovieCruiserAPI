package com.cts.authenticationserver.services;

import java.util.Map;

import com.cts.authenticationserver.model.User;

public interface SecurityTokenGenerator {
	
	Map<String, String> generateToken(User user);
}
