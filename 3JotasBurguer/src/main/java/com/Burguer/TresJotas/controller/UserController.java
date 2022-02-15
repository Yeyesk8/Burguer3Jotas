package com.Burguer.TresJotas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Burguer.TresJotas.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/user-list")
	public String userList(Model model) {
		model.addAttribute("usuarios", userService.getAllUsers());
		return "userList";

	}
	
	@RequestMapping("/delete")
	public String deleteUser(@RequestParam("id") Long id) {
		userService.deleteUserById(id);
		
		return "redirect:user-list";
	}
	
}
