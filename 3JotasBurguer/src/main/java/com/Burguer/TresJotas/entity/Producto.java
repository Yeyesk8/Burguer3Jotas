package com.Burguer.TresJotas.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	private int stock;	
	private double precio;
	private String imagen;
	

	@OneToMany(mappedBy="producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Categoria> categorias;
	
	@OneToMany(mappedBy="producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ingrediente> ingredientes;

	public Producto() {
	}
	
	public boolean hasStock(int amount) {
		return (this.getStock() > 0) && (amount <= this.getStock());
	}
	
	public void decreaseStock(int amount) {
		this.stock -= amount;
	}
	
    
	public void addCategory(Categoria categoria) {
        categorias.add(categoria);
        categoria.setProducto(this);
    }
 
    public void removeCategory(Categoria categoria) {
    	categorias.remove(categoria);   	
    	categoria.setProducto(null);
    }
    
    public void addIngrediente(Ingrediente ingrediente) {
    	ingredientes.add(ingrediente);   	
    	ingrediente.setProducto(this);
    }
 
    public void removeIngrediente(Ingrediente ingrediente) {
    	ingredientes.remove(ingrediente);
    	ingrediente.setProducto(null);
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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
    
    
    
	
	

}
