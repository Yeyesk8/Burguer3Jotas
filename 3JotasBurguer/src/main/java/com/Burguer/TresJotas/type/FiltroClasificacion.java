package com.Burguer.TresJotas.type;

import org.springframework.data.domain.Sort;

public class FiltroClasificacion {
	
	private String tipoclasificación;
	
	public FiltroClasificacion(String tipo) {
		this.tipoclasificación = tipo;
	}
	
	public Sort getTipoClasificación() {
		if (this.tipoclasificación == null) {
			return Sort.by("id").descending();
		}		
		switch(this.tipoclasificación) {
			case "priceasc":
				return Sort.by("precio").ascending();
			case "pricedesc":
				return Sort.by("precio").descending();
			case "alphasc":
				return Sort.by("nombre").ascending();
			case "alphdesc":
				return Sort.by("nombre").descending();
			default: 
				return Sort.by("id").descending();
				
		}
	}
	
	
}
