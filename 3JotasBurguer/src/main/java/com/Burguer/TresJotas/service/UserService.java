package com.Burguer.TresJotas.service;


import java.util.List;

import com.Burguer.TresJotas.domain.User;

public interface UserService {
	
	User findById(Long id);
	
	User findByUsername(String username);
		
	User findByEmail(String email);
		
	void save(User user);
	
	User createUser(String username, String email,  String password, List<String> roles);

}
