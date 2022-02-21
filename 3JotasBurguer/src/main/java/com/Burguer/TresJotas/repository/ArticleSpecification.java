package com.Burguer.TresJotas.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.Categoria;
import com.Burguer.TresJotas.entity.Ingrediente;

public class ArticleSpecification {

	private ArticleSpecification() {
	}

	@SuppressWarnings("serial")
	public static Specification<Article> filterBy(Integer priceLow, Integer priceHigh, List<String> categorias, List<String> ingredientes,
			String search) {
		return new Specification<Article>() {
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				query.distinct(true);

				if (categorias != null && !categorias.isEmpty()) {
					Join<Article, Categoria> joinSize = root.join("categorias");
					predicates.add(criteriaBuilder.and(joinSize.get("nombre").in(categorias)));
				}
												
				 if (ingredientes!=null && !ingredientes.isEmpty()) {
	                	Join<Article, Ingrediente> joinSize = root.join("ingredientes");
	                	predicates.add(criteriaBuilder.and(joinSize.get("nombre").in(ingredientes)));
	                }  

				if (search != null && !search.isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + search + "%")));
				}
				if (priceLow != null && priceLow >= 0) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceLow)));
				}
				if (priceHigh != null && priceHigh >= 0) {
					predicates
							.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceHigh)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
