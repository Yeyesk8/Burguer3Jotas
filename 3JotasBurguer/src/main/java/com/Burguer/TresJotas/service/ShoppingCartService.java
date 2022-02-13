package com.Burguer.TresJotas.service;

import com.Burguer.TresJotas.domain.Article;
import com.Burguer.TresJotas.domain.CartItem;
import com.Burguer.TresJotas.domain.ShoppingCart;
import com.Burguer.TresJotas.domain.User;


public interface ShoppingCartService {

	ShoppingCart getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	CartItem findCartItemById(Long cartItemId);
	
	CartItem addArticleToShoppingCart(Article article, User user, int qty, String size);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(CartItem cartItem, Integer qty);

	void removeCartItem(CartItem cartItem);
	
}
