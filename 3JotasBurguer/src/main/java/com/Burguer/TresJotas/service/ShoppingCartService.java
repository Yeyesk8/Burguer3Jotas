package com.Burguer.TresJotas.service;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.CartItem;
import com.Burguer.TresJotas.entity.ShoppingCart;
import com.Burguer.TresJotas.entity.User;


public interface ShoppingCartService {

	ShoppingCart getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	CartItem findCartItemById(Long cartItemId);
	
	CartItem addArticleToShoppingCart(Article article, User user, int qty);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(CartItem cartItem, Integer qty);

	void removeCartItem(CartItem cartItem);
	
}
