package com.Burguer.TresJotas.entity;

public class ConstructorSugerencia {
	
	private String nombre;
	private String apellido;
	private String email;	
	private String asunto;
	private String mensaje;
	
	public ConstructorSugerencia() {
	}
	
	public ConstructorSugerencia conNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public ConstructorSugerencia conApellido(String apellido) {
		this.apellido = apellido;
		return this;
	}
	
	public ConstructorSugerencia conEmail(String email) {
		this.email = email;
		return this;
	}
	
	public ConstructorSugerencia conAsunto(String asunto) {
		this.asunto = asunto;
		return this;
	}
	
	public ConstructorSugerencia conMensaje(String mensaje) {
		this.mensaje = mensaje;
		return this;
	}
	
	
	public Sugerencia build() {
		Sugerencia sugerencia = new Sugerencia();
		sugerencia.setNombre(this.nombre);
		sugerencia.setApellido(this.apellido);
		sugerencia.setEmail(this.email);
		sugerencia.setAsunto(this.asunto);
		sugerencia.setMensaje(this.mensaje);		
									
		return sugerencia;
	}

}
