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
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.carritoCompra;
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
	public synchronized Pedido createOrder(carritoCompra carritoCompra, Envio envio, Pago pago, User user) {
		Pedido order = new Pedido();
		order.setUser(user);
		order.setPago(pago);
		order.setEnvio(envio);
		order.setTotalPedido(carritoCompra.getGrandTotal());
		envio.setOrder(order);
		pago.setOrder(order);			
		LocalDate today = LocalDate.now();					
		order.setFechaPedido(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setFechaEnvio(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setEstadoPedido("Preparando");
		
		order = orderRepository.save(order);
		
		List<ProductoCarrito> productosCarrito = carritoCompra.getProductosCarrito();
		for (ProductoCarrito item : productosCarrito) {
			Article article = item.getArticle();
			article.decreaseStock(item.getCantidad());
			articleRepository.save(article);
			item.setOrder(order);
			cartItemRepository.save(item);
		}		
		return order;	
	}
	
	@Override
	public Pedido findOrderWithDetails(Long id) {
		return orderRepository.findEagerById(id);
	}	

	public List<Pedido> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public Iterable<Pedido> getAllPedidos() {
		return orderRepository.findAll();
	}

	@Override
	public void deletePedidoById(Long id) {
		orderRepository.deleteById(id);
		
	}

}
