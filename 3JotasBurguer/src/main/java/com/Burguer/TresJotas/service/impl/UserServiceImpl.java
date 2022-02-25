package com.Burguer.TresJotas.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.entity.security.Role;
import com.Burguer.TresJotas.entity.security.UserRole;
import com.Burguer.TresJotas.repository.RoleRepository;
import com.Burguer.TresJotas.repository.UserRepository;
import com.Burguer.TresJotas.service.UserService;

import utility.SecurityUtility;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User findById(Long id) {
		Optional<User> opt = userRepository.findById(id);
		return opt.get();
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void guardar(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional
	public User crearUsuario(String username, String password, String email, List<String> roles) {
		User user = findByUsername(username);
		if (user != null) {
			return user;
		} else {
			user = new User();
			user.setUsername(username);
			user.setPassword(SecurityUtility.passwordEncoder().encode(password));
			user.setEmail(email);			
			Set<UserRole> userRoles = new HashSet<>();
			for (String rolename : roles) {
				Role role = roleRepository.findByName(rolename);
				if (role == null) {
					role = new Role();
					role.setName(rolename);
					roleRepository.save(role);
				}
				userRoles.add(new UserRole(user, role));
			}			
			user.setUserRoles(userRoles);
			return userRepository.save(user);
		}
	}

	@Override
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void borrarUsuarioById(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public User findUserById(Long id) {
		Optional<User> opt = userRepository.findById(id);
		return opt.get();
	}
	
}
