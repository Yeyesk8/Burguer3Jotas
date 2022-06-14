package com.Burguer.TresJotas.controller;






import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.Burguer.TresJotas.entity.ConstructorPedido;
import com.Burguer.TresJotas.entity.Pedido;
import com.Burguer.TresJotas.service.PedidoService;



@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	
	@GetMapping("/lista-pedidos")
	public String pedidoList(Model model) {
		model.addAttribute("pedidos", pedidoService.getAllPedidos());
		return "listaPedido";
	}
	
	@GetMapping("/editar")
	public String editarPedido(@RequestParam("id") Long id, Model model) {
		Pedido pedido = pedidoService.findPedidoById(id);
		
		model.addAttribute("user_order", pedido);
		return "editarPedido";
	}
	
	@PostMapping("/editar")
	public String editarPedidoPost(@ModelAttribute("user_order") Pedido pedido, HttpServletRequest request) {		
		Pedido nuevo = new ConstructorPedido()						
				.conFechapedido(pedido.getFechaPedido())
				.conEstadoPedido(pedido.getEstadoPedido())
				.conTotalPedido(pedido.getTotalPedido())
				.build();
		nuevo.setId(pedido.getId());
		pedidoService.guardar(nuevo);	
		return "redirect:lista-pedidos";
	}
	
	@GetMapping("/eliminar")
	public String BorrarPedido(@RequestParam("id") Long id) {
		pedidoService.borrarPedidoById(id);
		
		return "redirect:lista-pedidos";
	}
	
	@GetMapping("/eliminar/cancel")
	public String cancelBorrarPedido(Model model) {

		return "redirect:lista-pedidos";
	}
	
}
