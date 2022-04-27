package com.Burguer.TresJotas.entity;


public class ConstructorUser {
		
	private String nombre;
	private String apellido;
	private String email;	
	private String username;
	private String password;

	
	public ConstructorUser() {
	}
	
	public ConstructorUser conNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public ConstructorUser conApellido(String apellido) {
		this.apellido = apellido;
		return this;
	}
	
	public ConstructorUser conEmail(String email) {
		this.email = email;
		return this;
	}
	
	public ConstructorUser conUsername(String username) {
		this.username = username;
		return this;
	}
	
	public ConstructorUser conPassword(String password) {
		this.password = password;
		return this;
	}
	
	
	public User build() {
		User user = new User();
		user.setNombre(this.nombre);
		user.setApellido(this.apellido);
		user.setEmail(this.email);
		user.setUsername(this.username);
		user.setPassword(this.password);		
									
		return user;
	}
	
}
