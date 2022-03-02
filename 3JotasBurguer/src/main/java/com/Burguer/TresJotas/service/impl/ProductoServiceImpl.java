package com.Burguer.TresJotas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.repository.ProductoRepository;
import com.Burguer.TresJotas.repository.EspecificacionProducto;
import com.Burguer.TresJotas.service.ProductoService;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepositoy;

	@Value("${productoservice.featured-items-number}")
	private int featuredArticlesNumber;

	@Override
	public List<Producto> findAllProductos() {
		return (List<Producto>) productoRepositoy.findAllEagerBy();
	}

	@Override
	public Page<Producto> findProductosPorCriterios(Pageable pageable, Double precioBajo, Double precioAlto,
			List<String> categorias,List<String> ingredientes, String busqueda) {
		Page<Producto> page = productoRepositoy
				.findAll(EspecificacionProducto.filtrarPor(precioBajo, precioAlto, categorias,ingredientes, busqueda), pageable);
		return page;
	}

	@Override
	public List<Producto> findPrimerosProductos() {
		return productoRepositoy.findAll(PageRequest.of(0, featuredArticlesNumber)).getContent();
	}

	@Override
	public Producto findProductoById(Long id) {
		Optional<Producto> opt = productoRepositoy.findById(id);
		return opt.get();
	}

	@Override
	@CacheEvict(value = {"categorias","ingredientes" }, allEntries = true)
	public Producto guardarProducto(Producto producto) {
		return productoRepositoy.save(producto);
	}

	@Override
	@CacheEvict(value = {"categorias","ingredientes" }, allEntries = true)
	public void borrarProductoById(Long id) {
		productoRepositoy.deleteById(id);
	}

	
	@Override
	@Cacheable("categorias")
	public List<String> getAllCategorias() {
		return productoRepositoy.findAllCategorias();
	}
	
	@Override
	@Cacheable("ingredientes")
	public List<String> getAllIngredientes() {
		return productoRepositoy.findAllIngredientes();
	}

}
