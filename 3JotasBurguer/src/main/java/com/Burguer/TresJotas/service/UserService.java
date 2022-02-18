package com.Burguer.TresJotas.service;


import java.util.List;

import com.Burguer.TresJotas.entity.User;

public interface UserService {
	
	Iterable<User> getAllUsers();
	
	User findById(Long id);
	
	User findByUsername(String username);
		
	User findByEmail(String email);
	
	User findUserById(Long id);
		
	void save(User user);
	
	User createUser(String username, String email,  String password, List<String> roles);
	
	void deleteUserById(Long id);

}
