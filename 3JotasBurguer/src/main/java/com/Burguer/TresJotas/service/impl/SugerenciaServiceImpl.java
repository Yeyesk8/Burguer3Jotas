package com.Burguer.TresJotas.service.impl;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Burguer.TresJotas.entity.Sugerencia;
import com.Burguer.TresJotas.repository.SugerenciaRepository;
import com.Burguer.TresJotas.service.SugerenciaService;



@Service
public class SugerenciaServiceImpl implements SugerenciaService {
	
	@Autowired
	private SugerenciaRepository sugerenciaRepository;
	


	@Override
	public Sugerencia findByAsunto(String asunto) {
		return sugerenciaRepository.findByAsunto(asunto);
	}
	
	@Override
	public void save(Sugerencia sugerencia) {
		sugerenciaRepository.save(sugerencia);
	}


	@Override
	public Sugerencia createSugerencia(String nombre, String apellido, String email, String asunto, String mensaje) {
		Sugerencia sugerencia = findByAsunto(asunto);
		if (sugerencia != null) {
			return sugerencia;
		} else {
			sugerencia = new Sugerencia();
			sugerencia.setNombre(nombre);
			sugerencia.setApellido(apellido);
			sugerencia.setEmail(email);
			sugerencia.setAsunto(asunto);
			sugerencia.setMensaje(mensaje);		
			
			return sugerenciaRepository.save(sugerencia);
		}
	}

	@Override
	public Iterable<Sugerencia> getAllSugerencias() {
		return sugerenciaRepository.findAll();
	}

	@Override
	public Sugerencia findSugerenciaById(Long id) {
		Optional<Sugerencia> opt = sugerenciaRepository.findById(id);
		return opt.get();
	}

	@Override
	public void deleteSugerenciaById(Long id) {
		sugerenciaRepository.deleteById(id);
		
	}

	}
	

