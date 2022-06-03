package com.Burguer.TresJotas.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Burguer.TresJotas.entity.ConstructorUser;
import com.Burguer.TresJotas.entity.User;
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

	@GetMapping("/usuario-nuevo")
	public String aniadirUsuario(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "aniadirUsuarioAdmin";
	}

	@PostMapping("/usuario-nuevo")
	public String aniadirUsuarioAdminPost(@ModelAttribute("user") User user,
			BindingResult bindingResults, @ModelAttribute("password") String password,
			RedirectAttributes redirectAttributes, Model model) {
		
		model.addAttribute("email", user.getEmail());
		model.addAttribute("username", user.getUsername());
		boolean invalidFields = false;
		if (bindingResults.hasErrors()) {
			return "redirect:/usuario/usuario-nuevo";
		}
		if (userService.findByUsername(user.getUsername()) != null) {
			redirectAttributes.addFlashAttribute("usernameExiste", true);
			invalidFields = true;
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			redirectAttributes.addFlashAttribute("emailExiste", true);
			invalidFields = true;
		}
		if (invalidFields) {
			return "redirect:/usuario/usuario-nuevo";
		}
		user = userService.crearUsuario(user.getUsername(), password, user.getEmail(), Arrays.asList("ROLE_USER"));		
		return "redirect:/usuario/lista-usuarios";
	}



	@GetMapping("/editar")
	public String editarUsuario(@RequestParam("id") Long id, Model model) {
		User user = userService.findById(id);

		model.addAttribute("user", user);
		return "editarUsuario";
	}

	@PostMapping("/editar")
	public String editarUsuarioPost(@ModelAttribute("user") User user, HttpServletRequest request) {
		User nuevo = new ConstructorUser().conNombre(user.getNombre()).conApellido(user.getApellido())
				.conEmail(user.getEmail()).conUsername(user.getUsername()).conPassword(user.getPassword()).build();
		nuevo.setId(user.getId());
		userService.guardar(nuevo);
		return "redirect:lista-usuarios";
	}

	@GetMapping("/eliminar")
	public String BorrarUsuario(@RequestParam("id") Long id) {
		userService.borrarUsuarioById(id);

		return "redirect:lista-usuarios";
	}

	@GetMapping("/eliminar/cancel")
	public String cancelBorrarUsuario(Model model) {

		return "redirect:lista-usuarios";
	}

}
