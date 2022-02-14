package com.Burguer.TresJotas.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int stock;	
	private double price;
	private String picture;
	

	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Category> categories;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ingredientes> ingredientes;

	public Article() {
	}
	
	public boolean hasStock(int amount) {
		return (this.getStock() > 0) && (amount <= this.getStock());
	}
	
	public void decreaseStock(int amount) {
		this.stock -= amount;
	}
	
    
	public void addCategory(Category category) {
        categories.add(category);
        category.setArticle(this);
    }
 
    public void removeCategory(Category category) {
    	categories.remove(category);
        category.setArticle(null);
    }
    
    public void addIngrediente(Ingredientes ingrediente) {
    	ingredientes.add(ingrediente);
    	ingrediente.setArticle(this);
    }
 
    public void removeIngrediente(Ingredientes ingrediente) {
    	ingredientes.remove(ingrediente);
    	ingrediente.setArticle(null);
    }
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public Set<Ingredientes> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<Ingredientes> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	

}
