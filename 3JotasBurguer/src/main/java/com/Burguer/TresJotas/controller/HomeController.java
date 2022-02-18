package com.Burguer.TresJotas.controller;


import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.Sugerencia;
import com.Burguer.TresJotas.service.ArticleService;
import com.Burguer.TresJotas.service.SugerenciaService;

@Controller
public class HomeController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SugerenciaService sugerenciaService;

	@GetMapping("/")
	public String index(Model model) {
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		return "index";
	}

	@GetMapping("/sugerencia")
	public String sugerencia(Model model) {
		model.addAttribute("asuntoExists", model.asMap().get("asuntoExists"));

		return "sugerencia";
	}
	
	@GetMapping("/historia")
	public String historia() {
		
		return "historia";
	}
	@GetMapping("/metodopago")
	public String metodopago() {
		
		return "pagos";
	}
	
	@GetMapping("/ubicacion")
	public String ubicacion() {
		
		return "ubicacion";
	}

	@GetMapping("/sugerencia/cancelar")
	public String cancelarSugerencia(Model model) {

		return "redirect:/";
	}

	@PostMapping("/new-sugerencia")
	public String newSugerenciaPost(@Valid @ModelAttribute("sugerencia") Sugerencia sugerencia,
			BindingResult bindingResults,RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("nombre", sugerencia.getNombre());
		model.addAttribute("apellido", sugerencia.getApellido());
		model.addAttribute("email", sugerencia.getEmail());
		model.addAttribute("asunto", sugerencia.getAsunto());
		model.addAttribute("mensaje", sugerencia.getMensaje());
		boolean invalidFields = false;
		if (bindingResults.hasErrors()) {
			return "redirect:/sugerencia";
		}
		if (sugerenciaService.findByAsunto(sugerencia.getAsunto()) != null) {
			redirectAttributes.addFlashAttribute("asuntoExists", true);
			invalidFields = true;
		}
		
		if (invalidFields) {
			return "redirect:/sugerencia";
		}
		sugerencia = sugerenciaService.createSugerencia(sugerencia.getNombre(),sugerencia.getApellido(),sugerencia.getEmail(),sugerencia.getAsunto(),sugerencia.getMensaje());
		return "redirect:/";
	}

}
