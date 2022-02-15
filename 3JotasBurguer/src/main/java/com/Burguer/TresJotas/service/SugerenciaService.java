package com.Burguer.TresJotas.service;



import com.Burguer.TresJotas.domain.Sugerencia;

public interface SugerenciaService {
	
	 Iterable<Sugerencia> getAllSugerencias();
	 
	 Sugerencia findSugerenciaById(Long id);

	Sugerencia findByAsunto(String asunto);

	void save(Sugerencia sugerencia);

	Sugerencia createSugerencia(String nombre, String apellido, String email,String asunto,String mensaje);
	
	void deleteSugerenciaById(Long id);

}
