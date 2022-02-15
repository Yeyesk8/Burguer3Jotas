package com.Burguer.TresJotas.service;


import com.Burguer.TresJotas.domain.Sugerencia;

public interface SugerenciaService {

	Sugerencia findByAsunto(String asunto);

	void save(Sugerencia sugerencia);

	Sugerencia createSugerencia(String nombre, String apellido, String email,String asunto,String mensaje);

}
