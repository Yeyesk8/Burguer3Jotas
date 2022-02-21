package com.Burguer.TresJotas.service;

import java.util.List;

import com.Burguer.TresJotas.entity.Order;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.ShoppingCart;
import com.Burguer.TresJotas.entity.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, Envio envio, Pago pago, User user);
	
	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);
	
	 Iterable<Order> getAllPedidos();
	 
	 void deletePedidoById(Long id);
}
