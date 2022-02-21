package com.Burguer.TresJotas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Burguer.TresJotas.entity.Article;

public interface ArticleService {

	List<Article> findAllArticles();

	Page<Article> findArticlesByCriteria(Pageable pageable, Integer priceLow, Integer priceHigh,
			List<String> categorias,List<String> ingredientes, String search);

	List<Article> findFirstArticles();

	Article findArticleById(Long id);

	Article saveArticle(Article article);

	void deleteArticleById(Long id);

	List<String> getAllCategorias();
	
	List<String> getAllIngredientes();

}
