package com.Burguer.TresJotas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.User;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

	List<Pedido> findByUser(User user); 
	
	@EntityGraph(attributePaths = { "productosCarrito", "pago", "envio" })
	Pedido findEagerById(Long id);
	
	Optional<Pedido> findById(Long id);

}
