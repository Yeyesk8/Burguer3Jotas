package com.Burguer.TresJotas.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.ContructorProducto;
import com.Burguer.TresJotas.entity.Categoria;
import com.Burguer.TresJotas.entity.Ingrediente;
import com.Burguer.TresJotas.service.ArticleService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/añadir")
	public String aniadirProducto(Model model) {
		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		model.addAttribute("allCategorias", articleService.getAllCategorias());
		model.addAttribute("allIngredientes", articleService.getAllIngredientes());
		return "aniadirProducto";
	}
	
	@PostMapping("/añadir")
	public String aniadirProductoPost(@ModelAttribute("producto") Producto producto, HttpServletRequest request) {
		Producto nuevo = new ContructorProducto()							
				.conTitulo(producto.getNombre())
				.conDescripcion(producto.getDescripcion())
				.stockDisponible(producto.getStock())
				.conPrecio(producto.getPrecio())
				.LinkImagen(producto.getImagen())
				.conCategorias(Arrays.asList(request.getParameter("categoria").split("\\s*,\\s*")))
				.conIngrediente(Arrays.asList(request.getParameter("ingrediente").split("\\s*,\\s*")))
				.build();		
		articleService.saveArticle(nuevo);	
		return "redirect:lista-productos";
	}
	
	@GetMapping("/lista-productos")
	public String listaProducto(Model model) {
		List<Producto> productos = articleService.findAllArticles();
		model.addAttribute("productos", productos);
		return "listaProducto";
	}
	
	@GetMapping("/editar")
	public String editarProducto(@RequestParam("id") Long id, Model model) {
		Producto producto = articleService.findArticleById(id);
		
		String preselectedCategorias = "";
		for (Categoria categoria : producto.getCategorias()) {
			preselectedCategorias += (categoria.getNombre() + ",");
		}		
		String preselectedIngredientes = "";
		for (Ingrediente ingrediente : producto.getIngredientes()) {
			preselectedIngredientes += (ingrediente.getNombre() + ",");
		}
		model.addAttribute("producto", producto);
		model.addAttribute("preselectedCategorias", preselectedCategorias);
		model.addAttribute("preselectedIngredientes", preselectedIngredientes);
		model.addAttribute("allCategorias", articleService.getAllCategorias());
		model.addAttribute("allIngredientes", articleService.getAllIngredientes());
		return "editarProducto";
	}
	
	@PostMapping("/editar")
	public String editarProductoPost(@ModelAttribute("producto") Producto producto, HttpServletRequest request) {		
		Producto nuevo = new ContructorProducto()						
				.conTitulo(producto.getNombre())
				.conDescripcion(producto.getDescripcion())
				.stockDisponible(producto.getStock())
				.conPrecio(producto.getPrecio())
				.LinkImagen(producto.getImagen())
				.conCategorias(Arrays.asList(request.getParameter("categoria").split("\\s*,\\s*")))
				.conIngrediente(Arrays.asList(request.getParameter("ingrediente").split("\\s*,\\s*")))
				.build();
		nuevo.setId(producto.getId());
		articleService.saveArticle(nuevo);	
		return "redirect:lista-productos";
	}
	
	@GetMapping("/eliminar")
	public String borrarProducto(@RequestParam("id") Long id) {
		articleService.deleteArticleById(id);
		return "redirect:lista-productos";
	}
	
}
