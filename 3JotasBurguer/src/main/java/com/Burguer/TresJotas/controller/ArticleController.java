package com.Burguer.TresJotas.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Burguer.TresJotas.entity.Article;
import com.Burguer.TresJotas.entity.ArticleBuilder;
import com.Burguer.TresJotas.entity.Category;
import com.Burguer.TresJotas.entity.Ingrediente;
import com.Burguer.TresJotas.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/add")
	public String addArticle(Model model) {
		Article article = new Article();
		model.addAttribute("article", article);
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("allIngredientes", articleService.getAllIngredientes());
		return "addArticle";
	}
	
	@PostMapping(value="/add")
	public String addArticlePost(@ModelAttribute("article") Article article, HttpServletRequest request) {
		Article newArticle = new ArticleBuilder()
				.withTitle(article.getTitle())
				.withDescripcion(article.getDescripcion())
				.stockAvailable(article.getStock())
				.withPrice(article.getPrice())
				.imageLink(article.getPicture())
				.ofCategories(Arrays.asList(request.getParameter("category").split("\\s*,\\s*")))
				.ofIngrediente(Arrays.asList(request.getParameter("ingrediente").split("\\s*,\\s*")))
				.build();		
		articleService.saveArticle(newArticle);	
		return "redirect:article-list";
	}
	
	@GetMapping("/article-list")
	public String articleList(Model model) {
		List<Article> articles = articleService.findAllArticles();
		model.addAttribute("articles", articles);
		return "articleList";
	}
	
	@GetMapping("/edit")
	public String editArticle(@RequestParam("id") Long id, Model model) {
		Article article = articleService.findArticleById(id);
		
		String preselectedCategories = "";
		for (Category category : article.getCategories()) {
			preselectedCategories += (category.getName() + ",");
		}		
		String preselectedIngredientes = "";
		for (Ingrediente ingrediente : article.getIngredientes()) {
			preselectedIngredientes += (ingrediente.getName() + ",");
		}
		model.addAttribute("article", article);
		model.addAttribute("preselectedCategories", preselectedCategories);
		model.addAttribute("preselectedIngredientes", preselectedIngredientes);
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("allIngredientes", articleService.getAllIngredientes());
		return "editArticle";
	}
	
	@PostMapping("/edit")
	public String editArticlePost(@ModelAttribute("article") Article article, HttpServletRequest request) {		
		Article newArticle = new ArticleBuilder()
				.withTitle(article.getTitle())
				.withDescripcion(article.getDescripcion())
				.stockAvailable(article.getStock())
				.withPrice(article.getPrice())
				.imageLink(article.getPicture())				
				.ofCategories(Arrays.asList(request.getParameter("category").split("\\s*,\\s*")))
				.ofIngrediente(Arrays.asList(request.getParameter("ingrediente").split("\\s*,\\s*")))
				.build();
		newArticle.setId(article.getId());
		articleService.saveArticle(newArticle);	
		return "redirect:article-list";
	}
	
	@GetMapping("/delete")
	public String deleteArticle(@RequestParam("id") Long id) {
		articleService.deleteArticleById(id);
		return "redirect:article-list";
	}
	
}
