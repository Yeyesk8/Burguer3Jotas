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

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.carritoCompra;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.service.ArticleService;
import com.Burguer.TresJotas.service.ShoppingCartService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
		
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ShoppingCartService carritoCompraService;
	
	@GetMapping("/carrito")
	public String carrito(Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		carritoCompra carritoCompra = carritoCompraService.getShoppingCart(user);		
		model.addAttribute("ListaProductosCarrito", carritoCompra.getProductosCarrito());
		model.addAttribute("carritoCompra", carritoCompra);		
		return "carrito";
	}

	@PostMapping("/añadir-carrito")
	public String aniadirCarrito(@ModelAttribute("article") Article article, @RequestParam("cantidad") String cantidad,
						  RedirectAttributes attributes, Model model, Authentication authentication) {
		article = articleService.findArticleById(article.getId());				
		if (!article.hasStock(Integer.parseInt(cantidad))) {
			attributes.addFlashAttribute("notEnoughStock", true);
			return "redirect:/detalle-producto?id="+article.getId();
		}		
		User user = (User) authentication.getPrincipal();		
		carritoCompraService.addArticleToShoppingCart(article, user, Integer.parseInt(cantidad));
		attributes.addFlashAttribute("addArticleSuccess", true);
		return "redirect:/detalle-producto?id="+article.getId();
	}
	
	@PostMapping("/editar-carrito")
	public String editarCarrito(@RequestParam("id") Long cartItemId,
									 @RequestParam("cantidad") Integer cantidad, Model model) {		
		ProductoCarrito productoCarrito = carritoCompraService.findCartItemById(cartItemId);
		if (productoCarrito.canUpdateQty(cantidad)) {
			carritoCompraService.updateCartItem(productoCarrito, cantidad);
		}
		return "redirect:/carrito/carrito";
	}
	
	@GetMapping("/eliminar-producto-carrito")
	public String eliminarProductoCarrito(@RequestParam("id") Long id) {		
		carritoCompraService.removeCartItem(carritoCompraService.findCartItemById(id));		
		return "redirect:/carrito/carrito";
	} 
}
