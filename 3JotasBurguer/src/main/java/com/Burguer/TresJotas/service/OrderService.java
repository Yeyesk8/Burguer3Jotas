package com.Burguer.TresJotas.service;

import java.util.List;

import com.Burguer.TresJotas.entity.Order;
import com.Burguer.TresJotas.entity.Payment;
import com.Burguer.TresJotas.entity.Shipping;
import com.Burguer.TresJotas.entity.ShoppingCart;
import com.Burguer.TresJotas.entity.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, Payment payment, User user);
	
	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);
	
	 Iterable<Order> getAllPedidos();
	 
	 void deletePedidoById(Long id);
}
