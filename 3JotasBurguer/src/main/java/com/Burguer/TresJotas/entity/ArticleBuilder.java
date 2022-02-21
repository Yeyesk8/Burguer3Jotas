package com.Burguer.TresJotas.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleBuilder {
		
	private String title;
	private String descripcion;
	private int stock;	
	private double price;
	private String picture;
	private List<String> categorias;
	private List<String> ingredientes;
	
	public ArticleBuilder() {
	}
	
	public ArticleBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	
	public ArticleBuilder withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public ArticleBuilder stockAvailable(int stock) {
		this.stock = stock;
		return this;
	}
	
	public ArticleBuilder withPrice(double price) {
		this.price = price;
		return this;
	}
	
	public ArticleBuilder imageLink(String picture) {
		this.picture = picture;
		return this;
	}
	
	public ArticleBuilder ofCategories(List<String> categories) {
		this.categorias = categories;
		return this;
	}

	public ArticleBuilder ofIngrediente(List<String> ingredientes) {
		this.ingredientes = ingredientes;
		return this;
	}
	
	public Article build() {
		Article article = new Article();
		article.setTitle(this.title);
		article.setDescripcion(this.descripcion);
		article.setPrice(this.price);
		article.setStock(this.stock);
		article.setPicture(this.picture);		
		
		
		if (this.categorias != null && !this.categorias.isEmpty() ) {
			Set<Categoria> catElements = new HashSet<>();
			for (String val : this.categorias) {
				catElements.add(new Categoria(val,article));
			}
			article.setCategories(catElements);
		}
		
		if (this.ingredientes != null && !this.ingredientes.isEmpty() ) {
			Set<Ingrediente> ingelements = new HashSet<>();
			for (String val : this.ingredientes) {
				ingelements.add(new Ingrediente(val,article));
			}
			article.setIngredientes(ingelements);
		}	
							
		return article;
	}
	
}