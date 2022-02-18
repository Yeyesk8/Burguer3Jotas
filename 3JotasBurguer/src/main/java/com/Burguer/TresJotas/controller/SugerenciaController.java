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
	
	@GetMapping("/sugerencia-list")
	public String sugerenciaList(Model model) {
		model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
		return "sugerenciaList";
	}
	
	@GetMapping("/delete")
	public String deleteSugerencia(@RequestParam("id") Long id) {
		sugerenciaService.deleteSugerenciaById(id);
		
		return "redirect:sugerencia-list";
	}

}
