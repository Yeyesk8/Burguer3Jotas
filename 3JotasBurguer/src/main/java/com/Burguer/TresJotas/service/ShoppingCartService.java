package com.Burguer.TresJotas.service;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.carritoCompra;
import com.Burguer.TresJotas.entity.User;


public interface ShoppingCartService {

	carritoCompra getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	ProductoCarrito findCartItemById(Long cartItemId);
	
	ProductoCarrito addArticleToShoppingCart(Article article, User user, int cantidad);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(ProductoCarrito productoCarrito, Integer cantidad);

	void removeCartItem(ProductoCarrito productoCarrito);
	
}
