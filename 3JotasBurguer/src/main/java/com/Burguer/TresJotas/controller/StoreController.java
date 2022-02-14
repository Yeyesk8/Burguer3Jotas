package com.Burguer.TresJotas.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Burguer.TresJotas.domain.Article;
import com.Burguer.TresJotas.form.ArticleFilterForm;
import com.Burguer.TresJotas.service.ArticleService;
import com.Burguer.TresJotas.type.SortFilter;

@Controller
public class StoreController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/store")
	public String store(@ModelAttribute("filters") ArticleFilterForm filters, Model model) {
		Integer page = filters.getPage();
		int pagenumber = (page == null || page <= 0) ? 0 : page - 1;
		SortFilter sortFilter = new SortFilter(filters.getSort());
		Page<Article> pageresult = articleService.findArticlesByCriteria(
				PageRequest.of(pagenumber, 9, sortFilter.getSortType()), filters.getPricelow(), filters.getPricehigh(),
				filters.getCategory(), filters.getSearch());
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("articles", pageresult.getContent());
		model.addAttribute("totalitems", pageresult.getTotalElements());
		model.addAttribute("itemsperpage", 9);
		return "store";
	}

	@RequestMapping("/article-detail")
	public String articleDetail(@PathParam("id") Long id, Model model) {
		Article article = articleService.findArticleById(id);
		model.addAttribute("article", article);
		model.addAttribute("notEnoughStock", model.asMap().get("notEnoughStock"));
		model.addAttribute("addArticleSuccess", model.asMap().get("addArticleSuccess"));
		return "articleDetail";
	}

}
