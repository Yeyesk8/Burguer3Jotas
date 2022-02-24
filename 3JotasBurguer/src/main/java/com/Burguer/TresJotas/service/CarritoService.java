package com.Burguer.TresJotas.service;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.CarritoCompra;
import com.Burguer.TresJotas.entity.User;


public interface CarritoService {

	CarritoCompra getCarrito(User user);
	
	int getnumeroItems(User user);
	
	ProductoCarrito findProductoCarritoById(Long productoCarritoId);
	
	ProductoCarrito addProductoCarrito(Producto producto, User user, int cantidad);
		
	void vaciarCarrito(User user);
	
	void actualizarProductoCarrito(ProductoCarrito productoCarrito, Integer cantidad);

	void retirarProductoCarrito(ProductoCarrito productoCarrito);
	
}
