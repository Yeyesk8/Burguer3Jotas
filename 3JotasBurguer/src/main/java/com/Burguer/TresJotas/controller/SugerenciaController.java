package com.Burguer.TresJotas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/eliminar")
	public String BorrarSugerencia(@RequestParam("id") Long id) {
		sugerenciaService.deleteSugerenciaById(id);
		
		return "redirect:lista-sugerencias";
	}

}
