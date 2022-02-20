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
import com.Burguer.TresJotas.entity.CartItem;
import com.Burguer.TresJotas.entity.ShoppingCart;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.service.ArticleService;
import com.Burguer.TresJotas.service.ShoppingCartService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
		
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/carrito")
	public String carrito(Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);		
		model.addAttribute("cartItemList", shoppingCart.getCartItems());
		model.addAttribute("shoppingCart", shoppingCart);		
		return "carrito";
	}

	@PostMapping("/añadir-carrito")
	public String aniadirCarrito(@ModelAttribute("article") Article article, @RequestParam("qty") String qty,
						  RedirectAttributes attributes, Model model, Authentication authentication) {
		article = articleService.findArticleById(article.getId());				
		if (!article.hasStock(Integer.parseInt(qty))) {
			attributes.addFlashAttribute("notEnoughStock", true);
			return "redirect:/detalle-producto?id="+article.getId();
		}		
		User user = (User) authentication.getPrincipal();		
		shoppingCartService.addArticleToShoppingCart(article, user, Integer.parseInt(qty));
		attributes.addFlashAttribute("addArticleSuccess", true);
		return "redirect:/detalle-producto?id="+article.getId();
	}
	
	@PostMapping("/editar-carrito")
	public String editarCarrito(@RequestParam("id") Long cartItemId,
									 @RequestParam("qty") Integer qty, Model model) {		
		CartItem cartItem = shoppingCartService.findCartItemById(cartItemId);
		if (cartItem.canUpdateQty(qty)) {
			shoppingCartService.updateCartItem(cartItem, qty);
		}
		return "redirect:/carrito/carrito";
	}
	
	@GetMapping("/eliminar-producto-carrito")
	public String eliminarProductoCarrito(@RequestParam("id") Long id) {		
		shoppingCartService.removeCartItem(shoppingCartService.findCartItemById(id));		
		return "redirect:/carrito/carrito";
	} 
}
