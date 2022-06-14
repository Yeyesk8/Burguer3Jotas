package com.Burguer.TresJotas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.CarritoCompra;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.service.CarritoService;
import com.Burguer.TresJotas.service.ProductoService;


@Controller
@RequestMapping("/carrito")
public class CarritoController {
		
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private CarritoService carritoCompraService;
	
	@GetMapping("/carrito")
	public String carrito(Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		CarritoCompra carritoCompra = carritoCompraService.getCarrito(user);		
		model.addAttribute("ListaProductosCarrito", carritoCompra.getProductosCarrito());
		model.addAttribute("carritoCompra", carritoCompra);		
		return "carrito";
	}

	@PostMapping("/añadir-carrito")
	public String aniadirCarrito(@ModelAttribute("producto") Producto producto, @RequestParam("cantidad") String cantidad,
						  RedirectAttributes attributes, Model model, Authentication authentication) {
		producto = productoService.findProductoById(producto.getId());				
		if (!producto.hasStock(Integer.parseInt(cantidad))) {
			attributes.addFlashAttribute("notEnoughStock", true);
			return "redirect:/detalle-producto?id="+producto.getId();
		}		
		User user = (User) authentication.getPrincipal();		
		carritoCompraService.aniadirProductoCarrito(producto, user, Integer.parseInt(cantidad));
		attributes.addFlashAttribute("addArticleSuccess", true);
		return "redirect:/detalle-producto?id="+producto.getId();
	}
	
	@PostMapping("/editar-carrito")
	public String editarCarrito(@RequestParam("id") Long productoCarritoId,
									 @RequestParam("cantidad") Integer cantidad, Model model) {		
		ProductoCarrito productoCarrito = carritoCompraService.findProductoCarritoById(productoCarritoId);
		if (productoCarrito.canUpdateQty(cantidad)) {
			carritoCompraService.actualizarProductoCarrito(productoCarrito, cantidad);
		}
		return "redirect:/carrito/carrito";
	}
	
	@GetMapping("/eliminar-producto-carrito")
	public String eliminarProductoCarrito(@RequestParam("id") Long id) {		
		carritoCompraService.retirarProductoCarrito(carritoCompraService.findProductoCarritoById(id));		
		return "redirect:/carrito/carrito";
	} 
}
