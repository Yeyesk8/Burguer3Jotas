package com.Burguer.TresJotas.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Burguer.TresJotas.entity.Direccion;
import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.service.PedidoService;
import com.Burguer.TresJotas.service.UserService;
import com.Burguer.TresJotas.service.impl.UserSecurityService;

import utility.SecurityUtility;

@Controller
public class CuentaController {

	@Autowired
	private UserService userService;
	

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/login")
	public String log(Model model) {
		model.addAttribute("usernameExiste", model.asMap().get("usernameExiste"));
		model.addAttribute("emailExiste", model.asMap().get("emailExiste"));
		return "miCuenta";
	}
	
	
	@GetMapping("/mi-perfil")
	public String miPerfil(Model model, Authentication authentication) {				
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "miPerfil";
	}
	
	@GetMapping("/mis-pedidos")
	public String misPedidos(Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		List<Pedido> orders = pedidoService.findByUser(user);
		model.addAttribute("orders", orders);
		return "misPedidos";
	}
	
	@GetMapping("/mi-direccion")
	public String miDireccion(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "miDireccion";
	}
	
	@PostMapping("/actualizar-direccion")
	public String actualizardireccion(@ModelAttribute("direccion") Direccion direccion, 
			Model model, Principal principal) throws Exception {
		User UserActual = userService.findByUsername(principal.getName());
		if(UserActual == null) {
			throw new Exception ("Usuario no encontrado");
		}
		UserActual.setDireccion(direccion);
		userService.guardar(UserActual);
		return "redirect:/mi-direccion";
	}
	
	@PostMapping("/nuevo-usuario")
	public String nuevoUsuarioPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResults,
							  @ModelAttribute("new-password") String password, 
							  RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("email", user.getEmail());
		model.addAttribute("username", user.getUsername());	
		boolean invalidFields = false;
		if (bindingResults.hasErrors()) {
			return "redirect:/login";
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
			return "redirect:/login";
		}		
		user = userService.crearUsuario(user.getUsername(), password,  user.getEmail(), Arrays.asList("ROLE_USER"));	
		userSecurityService.authenticateUser(user.getUsername());
		return "redirect:/mi-perfil";  
	}
		
	@PostMapping("/editar-info-usuario")
	public String editarInfoUsuario( @ModelAttribute("user") User user,
								  @RequestParam("newPassword") String newPassword,
								  Model model, Principal principal) throws Exception {
		User UserActual = userService.findByUsername(principal.getName());
		if(UserActual == null) {
			throw new Exception ("Usuario no encontrado");
		}
		/*verifica si el username ya existe*/
		User existingUser = userService.findByUsername(user.getUsername());
		if (existingUser != null && !existingUser.getId().equals(UserActual.getId()))  {
			model.addAttribute("usernameExiste", true);
			return "miPerfil";
		}	
		/*verifica si el email ya existe*/
		existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null && !existingUser.getId().equals(UserActual.getId()))  {
			model.addAttribute("emailExiste", true);
			return "miPerfil";
		}			
		/*actualizar contraseña*/
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = UserActual.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				UserActual.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				return "miPerfil";
			}
		}		
		UserActual.setNombre(user.getNombre());
		UserActual.setApellido(user.getApellido());
		UserActual.setUsername(user.getUsername());
		UserActual.setEmail(user.getEmail());		
		userService.guardar(UserActual);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", UserActual);				
		userSecurityService.authenticateUser(UserActual.getUsername());		
		return "miPerfil";
	}
	
	@GetMapping("/detalle-pedido")
	public String detallePedido(@RequestParam("order") Long id, Model model) {
		Pedido order = pedidoService.findPedidoDetalles(id);
		model.addAttribute("order", order);
		return "detallePedido";
	}
	
}
