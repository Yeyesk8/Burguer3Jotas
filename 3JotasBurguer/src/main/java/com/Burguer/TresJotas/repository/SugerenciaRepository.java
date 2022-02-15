package com.Burguer.TresJotas.repository;

import org.springframework.data.repository.CrudRepository;
import com.Burguer.TresJotas.domain.Sugerencia;




public interface SugerenciaRepository extends CrudRepository<Sugerencia, Long> {
	
	Sugerencia findByAsunto(String asunto);

}
