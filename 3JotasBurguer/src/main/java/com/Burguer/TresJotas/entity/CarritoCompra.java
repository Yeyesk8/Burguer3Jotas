package com.Burguer.TresJotas.entity;

import java.math.BigDecimal;
import java.util.List;

public class CarritoCompra {
	
	private List<ProductoCarrito> productosCarrito;

	public CarritoCompra(List<ProductoCarrito> productosCarrito) {
		this.productosCarrito = productosCarrito;
	}

	public BigDecimal getGrandTotal() {
		BigDecimal cartTotal = new BigDecimal(0);
		for (ProductoCarrito item : this.productosCarrito) {
			cartTotal = cartTotal.add(item.getSubtotal());
		}
		return cartTotal;
	}
	
	public boolean isEmpty() {
		return productosCarrito.isEmpty();
	}
	
	public void removeCartItem(ProductoCarrito cartItem) {
		productosCarrito.removeIf(item -> item.getId() == cartItem.getId());
	}
	
	public void clearItems() {
		productosCarrito.clear();
	}
	
	public ProductoCarrito findCartItemByArticle(Long id) {
		for (ProductoCarrito item : this.productosCarrito) {
			if (item.getProducto().getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	public int getItemCount() {
		return this.productosCarrito.size();
	}

	public List<ProductoCarrito> getProductosCarrito() {
		return productosCarrito;
	}

	public void setProductosCarrito(List<ProductoCarrito> productosCarrito) {
		this.productosCarrito = productosCarrito;
	}	
	
	

	
}
