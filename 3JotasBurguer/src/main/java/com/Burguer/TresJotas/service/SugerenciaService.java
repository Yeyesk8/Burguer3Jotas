package com.Burguer.TresJotas.service;



import com.Burguer.TresJotas.entity.Sugerencia;

public interface SugerenciaService {
	
	 Iterable<Sugerencia> getAllSugerencias();
	 
	 Sugerencia findSugerenciaById(Long id);

	Sugerencia findByAsunto(String asunto);

	void guardar(Sugerencia sugerencia);

	Sugerencia crearSugerencia(String nombre, String apellido, String email,String asunto,String mensaje);
	
	void borrarSugerenciaById(Long id);

}
