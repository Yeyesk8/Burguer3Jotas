package com.Burguer.TresJotas.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Burguer.TresJotas.entity.ConstructorSugerencia;
import com.Burguer.TresJotas.entity.Sugerencia;
import com.Burguer.TresJotas.service.SugerenciaService;

@Controller
@RequestMapping("/sugerencia")
public class SugerenciaController {
	
	@Autowired
	private SugerenciaService sugerenciaService;
	
	@GetMapping("/lista-sugerencias")
	public String sugerenciaList(Model model) {
		model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
		return "listaSugerencia";
	}
	
	@GetMapping("/editar")
	public String editarSugerencia(@RequestParam("id") Long id, Model model) {
		Sugerencia sugerencia = sugerenciaService.findSugerenciaById(id);
		
		model.addAttribute("sugerencia", sugerencia);
		return "editarSugerencia";
	}
	
	@PostMapping("/editar")
	public String editarSugerenciaPost(@ModelAttribute("sugerencia") Sugerencia sugerencia, HttpServletRequest request) {		
		Sugerencia nuevo = new ConstructorSugerencia()						
				.conNombre(sugerencia.getNombre())
				.conApellido(sugerencia.getApellido())
				.conEmail(sugerencia.getEmail())
				.conAsunto(sugerencia.getAsunto())
				.conMensaje(sugerencia.getMensaje())
				.build();
		nuevo.setId(sugerencia.getId());
		sugerenciaService.guardar(nuevo);	
		return "redirect:lista-sugerencias";
	}
	
	@GetMapping("/eliminar")
	public String BorrarSugerencia(@RequestParam("id") Long id) {
		sugerenciaService.borrarSugerenciaById(id);
		
		return "redirect:lista-sugerencias";
	}
	
	@GetMapping("/eliminar/cancel")
	public String cancelBorrarSugerencia(Model model) {

		return "redirect:lista-sugerencias";
	}

}
