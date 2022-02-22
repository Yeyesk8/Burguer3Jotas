package com.Burguer.TresJotas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Burguer.TresJotas.entity.Producto;

public interface ArticleService {

	List<Producto> findAllArticles();

	Page<Producto> findArticlesByCriteria(Pageable pageable, Double precioBajo, Double precioAlto,
			List<String> categorias,List<String> ingredientes, String busqueda);

	List<Producto> findFirstArticles();

	Producto findArticleById(Long id);

	Producto saveArticle(Producto producto);

	void deleteArticleById(Long id);

	List<String> getAllCategorias();
	
	List<String> getAllIngredientes();

}
