package com.Burguer.TresJotas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Burguer.TresJotas.service.OrderService;



@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/lista-pedidos")
	public String pedidoList(Model model) {
		model.addAttribute("pedidos", orderService.getAllPedidos());
		return "listaPedido";
	}
	
	@GetMapping("/eliminar")
	public String BorrarPedido(@RequestParam("id") Long id) {
		orderService.deletePedidoById(id);
		
		return "redirect:lista-pedidos";
	}

}
