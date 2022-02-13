package com.Burguer.TresJotas.service;

import java.util.List;

import com.Burguer.TresJotas.domain.Order;
import com.Burguer.TresJotas.domain.Payment;
import com.Burguer.TresJotas.domain.Shipping;
import com.Burguer.TresJotas.domain.ShoppingCart;
import com.Burguer.TresJotas.domain.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, Payment payment, User user);
	
	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);
}
