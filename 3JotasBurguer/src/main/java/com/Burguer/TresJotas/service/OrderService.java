package com.Burguer.TresJotas.service;

import java.util.List;

import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.ShoppingCart;
import com.Burguer.TresJotas.entity.User;

public interface OrderService {

	Pedido createOrder(ShoppingCart shoppingCart, Envio envio, Pago pago, User user);
	
	List<Pedido> findByUser(User user);
	
	Pedido findOrderWithDetails(Long id);
	
	 Iterable<Pedido> getAllPedidos();
	 
	 void deletePedidoById(Long id);
}
