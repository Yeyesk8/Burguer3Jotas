package com.Burguer.TresJotas.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Envio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String destinatario;
	@OneToOne(cascade= CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;	
	@OneToOne
	private Pedido order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Pedido getOrder() {
		return order;
	}

	public void setOrder(Pedido order) {
		this.order = order;
	}	

}
