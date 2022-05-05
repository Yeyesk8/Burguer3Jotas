package com.Burguer.TresJotas.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ConstructorPedido {
	
	private Date fechaPedido;
	private String estadoPedido;
	private BigDecimal totalPedido;
	
	public ConstructorPedido() {
	}
	
	public ConstructorPedido conFechapedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
		return this;
	}
	
	public ConstructorPedido conEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
		return this;
	}
	
	public ConstructorPedido conTotalPedido(BigDecimal totalPedido) {
		this.totalPedido = totalPedido;
		return this;
	}
	
	public Pedido build() {
		Pedido pedido = new Pedido();
		pedido.setFechaPedido(this.fechaPedido);
		pedido.setEstadoPedido(this.estadoPedido);
		pedido.setTotalPedido(this.totalPedido);
		
									
		return pedido;
	}
	

}
