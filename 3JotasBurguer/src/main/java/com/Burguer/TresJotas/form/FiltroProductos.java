package com.Burguer.TresJotas.form;

import java.util.List;

public class FiltroProductos {
	
	private List<String> categoria;
	private List<String> ingrediente;
	private Double precioBajo;
	private Double precioAlto;
	private String clasificacion;
	private Integer pagina;
	private String busqueda;
	
	public FiltroProductos() {
	}

	public List<String> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<String> categoria) {
		this.categoria = categoria;
	}

	public List<String> getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(List<String> ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Double getPrecioBajo() {
		return precioBajo;
	}

	public void setPrecioBajo(Double precioBajo) {
		this.precioBajo = precioBajo;
	}

	public Double getPrecioAlto() {
		return precioAlto;
	}

	public void setPrecioAlto(Double precioAlto) {
		this.precioAlto = precioAlto;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	

	
	
	
}
