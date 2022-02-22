package com.Burguer.TresJotas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.carritoCompra;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.repository.CartItemRepository;
import com.Burguer.TresJotas.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Override
	public carritoCompra getShoppingCart(User user) {
		return new carritoCompra(cartItemRepository.findAllByUserAndOrderIsNull(user));
	}
	
	@Override
	@Cacheable("itemcount")
	public int getItemsNumber(User user) {
		return cartItemRepository.countDistinctByUserAndOrderIsNull(user);
	}

	@Override
	public ProductoCarrito findCartItemById(Long cartItemId) {
		Optional<ProductoCarrito> opt = cartItemRepository.findById(cartItemId);
		return opt.get();
	}

	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public ProductoCarrito addArticleToShoppingCart(Article article, User user, int cantidad) {
		carritoCompra carritoCompra = this.getShoppingCart(user);
		ProductoCarrito productoCarrito = carritoCompra.findCartItemByArticle(article.getId());
		if (productoCarrito != null) {
			productoCarrito.addQuantity(cantidad);
			productoCarrito = cartItemRepository.save(productoCarrito);
		} else {
			productoCarrito = new ProductoCarrito();
			productoCarrito.setUser(user);
			productoCarrito.setArticle(article);
			productoCarrito.setCantidad(cantidad);
			productoCarrito = cartItemRepository.save(productoCarrito);
		}		
		return productoCarrito;	
	}

	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public void removeCartItem(ProductoCarrito productoCarrito) {
		cartItemRepository.deleteById(productoCarrito.getId());
	}
	
	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public void updateCartItem(ProductoCarrito productoCarrito, Integer cantidad) {
		if (cantidad == null || cantidad <= 0) {
			this.removeCartItem(productoCarrito);
		} else if (productoCarrito.getArticle().hasStock(cantidad)) {
			productoCarrito.setCantidad(cantidad);
			cartItemRepository.save(productoCarrito);
		}
	}

	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public void clearShoppingCart(User user) {
		cartItemRepository.deleteAllByUserAndOrderIsNull(user);	
	}	
}
