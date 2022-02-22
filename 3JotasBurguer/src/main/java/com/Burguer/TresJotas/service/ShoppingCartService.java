package com.Burguer.TresJotas.service;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.CarritoCompra;
import com.Burguer.TresJotas.entity.User;


public interface ShoppingCartService {

	CarritoCompra getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	ProductoCarrito findCartItemById(Long cartItemId);
	
	ProductoCarrito addArticleToShoppingCart(Producto article, User user, int cantidad);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(ProductoCarrito productoCarrito, Integer cantidad);

	void removeCartItem(ProductoCarrito productoCarrito);
	
}
