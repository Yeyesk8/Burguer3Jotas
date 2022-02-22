package com.Burguer.TresJotas.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ProductoCarrito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int cantidad;
	
	@OneToOne
	@JoinColumn(name="article_id")
	private Article article;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Pedido order;
	
	public ProductoCarrito() {		
	}
	
	public boolean canUpdateQty(Integer cantidad) {
		return cantidad == null || cantidad <= 0 || this.getArticle().hasStock(cantidad);
	}
	
	public BigDecimal getSubtotal() {
		return new BigDecimal(article.getPrice()).multiply(new BigDecimal(cantidad));
	}

	public void addQuantity(int cantidad) {
		if (cantidad > 0) {
			this.cantidad = this.cantidad + cantidad;
		}
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Pedido getOrder() {
		return order;
	}
	public void setOrder(Pedido order) {
		this.order = order;
	}
	
	
}