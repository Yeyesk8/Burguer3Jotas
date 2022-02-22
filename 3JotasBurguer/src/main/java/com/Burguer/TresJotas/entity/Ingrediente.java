package com.Burguer.TresJotas.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ingrediente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	private String nombre;
	
	public Ingrediente() {
	}
	
	public Ingrediente(String nombre, Producto producto) {
		this.nombre = nombre;
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	
}
