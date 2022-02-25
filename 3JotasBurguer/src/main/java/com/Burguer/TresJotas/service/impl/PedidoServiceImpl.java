package com.Burguer.TresJotas.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.CarritoCompra;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.repository.ProductoRepository;
import com.Burguer.TresJotas.repository.ProductoCarritoRepository;
import com.Burguer.TresJotas.repository.PedidoRepository;
import com.Burguer.TresJotas.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	PedidoRepository orderRepository;
	
	@Autowired
	ProductoCarritoRepository productoCarritoRepository;
	
	@Autowired
	ProductoRepository productoRepository;
			
	@Override
	@Transactional
	@CacheEvict(value = "itemcount", allEntries = true)
	public synchronized Pedido crearPedido(CarritoCompra carritoCompra, Envio envio, Pago pago, User user) {
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
			Producto producto = item.getProducto();
			producto.decreaseStock(item.getCantidad());
			productoRepository.save(producto);
			item.setOrder(order);
			productoCarritoRepository.save(item);
		}		
		return order;	
	}
	
	@Override
	public Pedido findPedidoDetalles(Long id) {
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
	public void borrarPedidoById(Long id) {
		orderRepository.deleteById(id);
		
	}

}
