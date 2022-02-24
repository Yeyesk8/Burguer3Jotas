package com.Burguer.TresJotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.User;

public interface ProductoCarritoRepository extends CrudRepository<ProductoCarrito, Long> {

	@EntityGraph(attributePaths = { "producto" })
	List<ProductoCarrito> findAllByUserAndOrderIsNull(User user);
	
	void deleteAllByUserAndOrderIsNull(User user);

	int countDistinctByUserAndOrderIsNull(User user);
}
