package com.Burguer.TresJotas.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContructorProducto {
		
	private String nombre;
	private String descripcion;
	private int stock;	
	private double precio;
	private String imagen;
	private List<String> categorias;
	private List<String> ingredientes;
	
	public ContructorProducto() {
	}
	
	public ContructorProducto conTitulo(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public ContructorProducto conDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public ContructorProducto stockDisponible(int stock) {
		this.stock = stock;
		return this;
	}
	
	public ContructorProducto conPrecio(double precio) {
		this.precio = precio;
		return this;
	}
	
	public ContructorProducto LinkImagen(String imagen) {
		this.imagen = imagen;
		return this;
	}
	
	public ContructorProducto conCategorias(List<String> categorias) {
		this.categorias = categorias;
		return this;
	}

	public ContructorProducto conIngrediente(List<String> ingredientes) {
		this.ingredientes = ingredientes;
		return this;
	}
	
	public Producto build() {
		Producto producto = new Producto();
		producto.setNombre(this.nombre);
		producto.setDescripcion(this.descripcion);
		producto.setPrecio(this.precio);
		producto.setStock(this.stock);
		producto.setImagen(this.imagen);		
		
		
		if (this.categorias != null && !this.categorias.isEmpty() ) {
			Set<Categoria> catElements = new HashSet<>();
			for (String val : this.categorias) {
				catElements.add(new Categoria(val,producto));
			}
			producto.setCategorias(catElements);
		}
		
		if (this.ingredientes != null && !this.ingredientes.isEmpty() ) {
			Set<Ingrediente> ingelements = new HashSet<>();
			for (String val : this.ingredientes) {
				ingelements.add(new Ingrediente(val,producto));
			}
			producto.setIngredientes(ingelements);
		}	
							
		return producto;
	}
	
}