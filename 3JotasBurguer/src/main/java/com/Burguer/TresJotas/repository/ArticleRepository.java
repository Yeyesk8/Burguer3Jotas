package com.Burguer.TresJotas.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.Burguer.TresJotas.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
	
	@EntityGraph(attributePaths = {"categorias","ingredientes"})
	List<Article> findAllEagerBy();	
		
	@EntityGraph(attributePaths = {"categorias","ingredientes"})
	Optional<Article> findById(Long id);
	
	@Query("SELECT DISTINCT c.nombre FROM Categoria c")
	List<String> findAllCategorias();
		
	@Query("SELECT DISTINCT i.nombre FROM Ingrediente i")
	List<String> findAllIngredientes();
	
}
