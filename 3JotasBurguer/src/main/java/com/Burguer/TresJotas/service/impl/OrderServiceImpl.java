package com.Burguer.TresJotas.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.CartItem;
import com.Burguer.TresJotas.entity.Order;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.ShoppingCart;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.repository.ArticleRepository;
import com.Burguer.TresJotas.repository.CartItemRepository;
import com.Burguer.TresJotas.repository.OrderRepository;
import com.Burguer.TresJotas.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	ArticleRepository articleRepository;
			
	@Override
	@Transactional
	@CacheEvict(value = "itemcount", allEntries = true)
	public synchronized Order createOrder(ShoppingCart shoppingCart, Envio envio, Pago pago, User user) {
		Order order = new Order();
		order.setUser(user);
		order.setPago(pago);
		order.setEnvio(envio);
		order.setOrderTotal(shoppingCart.getGrandTotal());
		envio.setOrder(order);
		pago.setOrder(order);			
		LocalDate today = LocalDate.now();					
		order.setOrderDate(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setFechaEnvio(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setOrderStatus("Preparando");
		
		order = orderRepository.save(order);
		
		List<CartItem> cartItems = shoppingCart.getCartItems();
		for (CartItem item : cartItems) {
			Article article = item.getArticle();
			article.decreaseStock(item.getQty());
			articleRepository.save(article);
			item.setOrder(order);
			cartItemRepository.save(item);
		}		
		return order;	
	}
	
	@Override
	public Order findOrderWithDetails(Long id) {
		return orderRepository.findEagerById(id);
	}	

	public List<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public Iterable<Order> getAllPedidos() {
		return orderRepository.findAll();
	}

	@Override
	public void deletePedidoById(Long id) {
		orderRepository.deleteById(id);
		
	}

}
