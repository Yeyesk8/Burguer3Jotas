package com.Burguer.TresJotas.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.Burguer.TresJotas.entity.Producto;
import com.Burguer.TresJotas.entity.Categoria;
import com.Burguer.TresJotas.entity.Ingrediente;

public class ArticleSpecification {

	private ArticleSpecification() {
	}

	@SuppressWarnings("serial")
	public static Specification<Producto> filterBy(Double precioBajo, Double precioAlto, List<String> categorias, List<String> ingredientes,
			String busqueda) {
		return new Specification<Producto>() {
			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				query.distinct(true);

				if (categorias != null && !categorias.isEmpty()) {
					Join<Producto, Categoria> joinSize = root.join("categorias");
					predicates.add(criteriaBuilder.and(joinSize.get("nombre").in(categorias)));
				}
												
				 if (ingredientes!=null && !ingredientes.isEmpty()) {
	                	Join<Producto, Ingrediente> joinSize = root.join("ingredientes");
	                	predicates.add(criteriaBuilder.and(joinSize.get("nombre").in(ingredientes)));
	                }  

				if (busqueda != null && !busqueda.isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("nombre"), "%" + busqueda + "%")));
				}
				if (precioBajo != null && precioBajo >= 0) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), precioBajo)));
				}
				if (precioAlto != null && precioAlto >= 0) {
					predicates
							.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioAlto)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
