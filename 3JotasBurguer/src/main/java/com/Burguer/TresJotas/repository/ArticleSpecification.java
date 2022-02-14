package com.Burguer.TresJotas.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.Burguer.TresJotas.domain.Article;
import com.Burguer.TresJotas.domain.Category;
import com.Burguer.TresJotas.domain.Ingredientes;

public class ArticleSpecification {

	private ArticleSpecification() {
	}

	@SuppressWarnings("serial")
	public static Specification<Article> filterBy(Integer priceLow, Integer priceHigh, List<String> categories, List<String> ingredientes,
			String search) {
		return new Specification<Article>() {
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				query.distinct(true);

				if (categories != null && !categories.isEmpty()) {
					Join<Article, Category> joinSize = root.join("categories");
					predicates.add(criteriaBuilder.and(joinSize.get("name").in(categories)));
				}
				
				if (ingredientes != null && !ingredientes.isEmpty()) {
					Join<Article, Ingredientes> joinSize = root.join("ingredientes");
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
