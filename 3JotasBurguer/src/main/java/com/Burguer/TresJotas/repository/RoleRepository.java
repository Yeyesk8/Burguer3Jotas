package com.Burguer.TresJotas.repository;

import org.springframework.data.repository.CrudRepository;

import com.Burguer.TresJotas.entity.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByName(String name);

}
