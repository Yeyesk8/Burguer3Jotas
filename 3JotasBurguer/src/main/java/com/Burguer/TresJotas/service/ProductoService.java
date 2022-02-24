package com.Burguer.TresJotas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Burguer.TresJotas.entity.Producto;

public interface ProductoService {

	List<Producto> findAllProductos();

	Page<Producto> findProductosPorCriterios(Pageable pageable, Double precioBajo, Double precioAlto,
			List<String> categorias,List<String> ingredientes, String busqueda);

	List<Producto> findPrimerosProductos();

	Producto findProductoById(Long id);

	Producto guardarProducto(Producto producto);

	void borrarProductoById(Long id);

	List<String> getAllCategorias();
	
	List<String> getAllIngredientes();

}
