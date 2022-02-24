package com.Burguer.TresJotas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.ProductoCarrito;
import com.Burguer.TresJotas.entity.CarritoCompra;
import com.Burguer.TresJotas.entity.User;
import com.Burguer.TresJotas.repository.ProductoCarritoRepository;
import com.Burguer.TresJotas.service.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

	@Autowired
	private ProductoCarritoRepository productoCarritoRepository;
	
	@Override
	public CarritoCompra getCarrito(User user) {
		return new CarritoCompra(productoCarritoRepository.findAllByUserAndOrderIsNull(user));
	}
	
	@Override
	@Cacheable("itemcount")
	public int getnumeroItems(User user) {
		return productoCarritoRepository.countDistinctByUserAndOrderIsNull(user);
	}

	@Override
	public ProductoCarrito findProductoCarritoById(Long productoCarritoId) {
		Optional<ProductoCarrito> opt = productoCarritoRepository.findById(productoCarritoId);
		return opt.get();
	}

	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public ProductoCarrito addProductoCarrito(Producto producto, User user, int cantidad) {
		CarritoCompra carritoCompra = this.getCarrito(user);
		ProductoCarrito productoCarrito = carritoCompra.findCartItemByArticle(producto.getId());
		if (productoCarrito != null) {
			productoCarrito.addQuantity(cantidad);
			productoCarrito = productoCarritoRepository.save(productoCarrito);
		} else {
			productoCarrito = new ProductoCarrito();
			productoCarrito.setUser(user);
			productoCarrito.setProducto(producto);
			productoCarrito.setCantidad(cantidad);
			productoCarrito = productoCarritoRepository.save(productoCarrito);
		}		
		return productoCarrito;	
	}

	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public void retirarProductoCarrito(ProductoCarrito productoCarrito) {
		productoCarritoRepository.deleteById(productoCarrito.getId());
	}
	
	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public void actualizarProductoCarrito(ProductoCarrito productoCarrito, Integer cantidad) {
		if (cantidad == null || cantidad <= 0) {
			this.retirarProductoCarrito(productoCarrito);
		} else if (productoCarrito.getProducto().hasStock(cantidad)) {
			productoCarrito.setCantidad(cantidad);
			productoCarritoRepository.save(productoCarrito);
		}
	}

	@Override
	@CacheEvict(value = "itemcount", allEntries = true)
	public void vaciarCarrito(User user) {
		productoCarritoRepository.deleteAllByUserAndOrderIsNull(user);	
	}	
}
