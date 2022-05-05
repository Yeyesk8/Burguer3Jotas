package com.Burguer.TresJotas.service;

import java.util.List;

import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.CarritoCompra;
import com.Burguer.TresJotas.entity.User;

public interface PedidoService {

	Pedido crearPedido(CarritoCompra carritoCompra, Envio envio, Pago pago, User user);
	
	List<Pedido> findByUser(User user);
	
	Pedido findPedidoById(Long id);
	
	Pedido findPedidoDetalles(Long id);
	
	 Iterable<Pedido> getAllPedidos();
	 
	 void guardar(Pedido pedido);
	 
	 void borrarPedidoById(Long id);
	 
}
