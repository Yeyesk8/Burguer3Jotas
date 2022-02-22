package com.Burguer.TresJotas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Burguer.TresJotas.entity.Direccion;
import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.entity.Pago;
import com.Burguer.TresJotas.entity.Envio;
import com.Burguer.TresJotas.entity.carritoCompra;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.service.OrderService;
import com.Burguer.TresJotas.service.ShoppingCartService;

@Controller
public class VerificarControler {
	
	@Autowired 
	private ShoppingCartService carritoCompraService;
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/verificar")
	public String verificar( @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
							Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();	
		carritoCompra carritoCompra = carritoCompraService.getShoppingCart(user);
		if(carritoCompra.isEmpty()) {
			model.addAttribute("emptyCart", true);
			return "redirect:/carrito/carrito";
		}						
		model.addAttribute("ListaProductosCarrito", carritoCompra.getProductosCarrito());
		model.addAttribute("carritoCompra", carritoCompra);
		if(missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}		
		return "verificarPedido";		
	}
	
	@PostMapping("/verificar")
	public String realizarPedido(@ModelAttribute("envio") Envio envio,
							@ModelAttribute("direccion") Direccion direccion,
							@ModelAttribute("pago") Pago pago,
							RedirectAttributes redirectAttributes, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		carritoCompra carritoCompra = carritoCompraService.getShoppingCart(user);	
		if (!carritoCompra.isEmpty()) {
			envio.setDireccion(direccion);
			Pedido order = orderService.createOrder(carritoCompra, envio, pago, user);		
			redirectAttributes.addFlashAttribute("order", order);
		}
		return "redirect:/pedido-validado";
	}
	
	@GetMapping("/pedido-validado")
	public String pedidoValidado(Model model) {
		Pedido order = (Pedido) model.asMap().get("order");
		if (order == null) {
			return "redirect:/";
		}
		model.addAttribute("order", order);
		return "pedidoValidado";	
	}

}
