package com.Burguer.TresJotas.controller;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Burguer.TresJotas.entity.ConstructorUser;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.service.UserService;

import utility.SecurityUtility;

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
	
	@GetMapping("/editar")
	public String editarUsuario(@RequestParam("id") Long id, Model model) {
		User user = userService.findById(id);
		
		model.addAttribute("user", user);
		return "editarUsuario";
	}
	
	@PostMapping("/editar")
	public String editarUsuarioPost(@ModelAttribute("user") User user, HttpServletRequest request) {		
		User nuevo = new ConstructorUser()						
				.conNombre(user.getNombre())
				.conApellido(user.getApellido())
				.conEmail(user.getEmail())
				.conUsername(user.getUsername())
				.conPassword(user.getPassword())
				.build();
		nuevo.setId(user.getId());
		userService.guardar(nuevo);	
		return "redirect:lista-usuarios";
	}
	
	@GetMapping("/eliminar")
	public String BorrarUsuario(@RequestParam("id") Long id) {
		userService.borrarUsuarioById(id);
		
		return "redirect:lista-usuarios";
	}
	
}
