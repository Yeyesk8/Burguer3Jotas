package com.Burguer.TresJotas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Burguer.TresJotas.service.UserService;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/lista-usuarios")
	public String listaUsuario(Model model) {
		model.addAttribute("usuarios", userService.getAllUsers());
		return "listaUsuario";

	}
	
	@GetMapping("/eliminar")
	public String BorrarUsuario(@RequestParam("id") Long id) {
		userService.deleteUserById(id);
		
		return "redirect:lista-usuarios";
	}
	
}
