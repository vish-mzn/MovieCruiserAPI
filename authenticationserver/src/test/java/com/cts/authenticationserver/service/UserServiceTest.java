package com.cts.authenticationserver.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.authenticationserver.exception.UserAlreadyExistsException;
import com.cts.authenticationserver.exception.UserNotFoundException;
import com.cts.authenticationserver.model.User;
import com.cts.authenticationserver.repository.UserRepository;
import com.cts.authenticationserver.services.UserServiceImpl;

public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	private User user;
	
	@InjectMocks
	private UserServiceImpl service;
	
	Optional<User> options;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User("vishesh1234", "Vishesh", "Pahuja", "vish@123", new Date());
		options = Optional.of(user);
	}

	@Test
	public void testSaveUserSuccess() throws UserAlreadyExistsException {
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = service.saveUser(user);
		assertEquals("Cannot Register User",  true, flag);
		verify(userRepository, times(1)).save(user);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void testSaveUserFailure() throws UserAlreadyExistsException {
		when(userRepository.findById(user.getUserId())).thenReturn(options);
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = service.saveUser(user);
		assertTrue("saving user failed", flag);
		verify(userRepository, times(1)).findById(user.getUserId());
	}
	
	@Test
	public void testValidateSuccess() throws UserNotFoundException {
		when(userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User userResult = service.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(userResult);
		assertEquals(user.getUserId(), userResult.getUserId());
		verify(userRepository, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}

	@Test(expected = UserNotFoundException.class)
	public void testValidateFailure() throws UserNotFoundException {
		when(userRepository.findById("vishe")).thenReturn(null);
		service.findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}

}
