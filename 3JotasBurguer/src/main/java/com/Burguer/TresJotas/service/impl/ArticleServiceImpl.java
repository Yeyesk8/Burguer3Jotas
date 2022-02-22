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
import com.Burguer.TresJotas.repository.ArticleRepository;
import com.Burguer.TresJotas.repository.ArticleSpecification;
import com.Burguer.TresJotas.service.ArticleService;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Value("${articleservice.featured-items-number}")
	private int featuredArticlesNumber;

	@Override
	public List<Producto> findAllArticles() {
		return (List<Producto>) articleRepository.findAllEagerBy();
	}

	@Override
	public Page<Producto> findArticlesByCriteria(Pageable pageable, Double precioBajo, Double precioAlto,
			List<String> categorias,List<String> ingredientes, String busqueda) {
		Page<Producto> page = articleRepository
				.findAll(ArticleSpecification.filterBy(precioBajo, precioAlto, categorias,ingredientes, busqueda), pageable);
		return page;
	}

	@Override
	public List<Producto> findFirstArticles() {
		return articleRepository.findAll(PageRequest.of(0, featuredArticlesNumber)).getContent();
	}

	@Override
	public Producto findArticleById(Long id) {
		Optional<Producto> opt = articleRepository.findById(id);
		return opt.get();
	}

	@Override
	@CacheEvict(value = {"categorias","ingredientes" }, allEntries = true)
	public Producto saveArticle(Producto producto) {
		return articleRepository.save(producto);
	}

	@Override
	@CacheEvict(value = {"categorias","ingredientes" }, allEntries = true)
	public void deleteArticleById(Long id) {
		articleRepository.deleteById(id);
	}

	
	@Override
	@Cacheable("categorias")
	public List<String> getAllCategorias() {
		return articleRepository.findAllCategorias();
	}
	
	@Override
	@Cacheable("ingredientes")
	public List<String> getAllIngredientes() {
		return articleRepository.findAllIngredientes();
	}

}
