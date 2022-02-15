package com.Burguer.TresJotas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.Burguer.TresJotas.domain.Sugerencia;




public interface SugerenciaRepository extends JpaRepository<Sugerencia, Long>, JpaSpecificationExecutor<Sugerencia> {
	
	Sugerencia findByAsunto(String asunto);
	
	Optional<Sugerencia> findByNombre(String nombre);
	
	Optional<Sugerencia> findById(Long id);

}
