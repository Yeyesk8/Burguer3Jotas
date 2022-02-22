package com.Burguer.TresJotas.type;

import org.springframework.data.domain.Sort;

public class FiltroClasificacion {
	
	private String sortType;
	
	public FiltroClasificacion(String type) {
		this.sortType = type;
	}
	
	public Sort getSortType() {
		if (this.sortType == null) {
			return Sort.by("id").descending();
		}		
		switch(this.sortType) {
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
