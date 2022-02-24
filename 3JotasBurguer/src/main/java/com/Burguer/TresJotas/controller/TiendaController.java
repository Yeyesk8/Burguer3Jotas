package com.Burguer.TresJotas.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.form.FiltroProductos;
import com.Burguer.TresJotas.service.ProductoService;
import com.Burguer.TresJotas.type.FiltroClasificacion;

@Controller
public class TiendaController {

	@Autowired
	private ProductoService productoService;

	@GetMapping("/tienda")
	public String tienda(@ModelAttribute("filtros") FiltroProductos filtros, Model model) {
		Integer pagina = filtros.getPagina();
		int numeropagina = (pagina == null || pagina <= 0) ? 0 : pagina - 1;
		FiltroClasificacion filtroClasificacion = new FiltroClasificacion(filtros.getClasificacion());
		Page<Producto> pageresult = productoService.findProductosPorCriterios(
				PageRequest.of(numeropagina, 9, filtroClasificacion.getSortType()), filtros.getPrecioBajo(), filtros.getPrecioAlto(),
				filtros.getCategoria(),filtros.getIngrediente(), filtros.getBusqueda());
		
		model.addAttribute("allCategorias", productoService.getAllCategorias());
		model.addAttribute("allIngredientes", productoService.getAllIngredientes());
		model.addAttribute("productos", pageresult.getContent());
		model.addAttribute("totalitems", pageresult.getTotalElements());
		model.addAttribute("itemsperpage", 9);
		return "tienda";
	}

	@GetMapping("/detalle-producto")
	public String detalleProducto(@PathParam("id") Long id, Model model) {
		Producto producto = productoService.findProductoById(id);
		model.addAttribute("producto", producto);
		model.addAttribute("notEnoughStock", model.asMap().get("notEnoughStock"));
		model.addAttribute("addArticleSuccess", model.asMap().get("addArticleSuccess"));
		return "detalleProducto";
	}

}
