package com.shop.controllers.administratorSite.categories;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.data.tables.Categories;
import com.shop.others.RepositoriesAccess;

@Controller
@RequestMapping("administratorSite/categories")
public class UpdateCategories {

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateSite(Model model) {
		Iterable<Categories> categories = RepositoriesAccess.categoriesRepository.findAll();

		model.addAttribute("categories", categories);
		return "administratorSite/categoriesManager/update";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateOneSite(@PathVariable Long id, Model model) {
		Categories categoryFound = RepositoriesAccess.categoriesRepository.findById(id);

		if (categoryFound == null)
			model.addAttribute("msg", "not found");

		model.addAttribute("category", categoryFound);
		return "/administratorSite/categoriesManager/updateOneCategory";
	}

	@RequestMapping(value = "update/updateOne", method = RequestMethod.POST)
	
	public String update(@RequestParam("id") String id, @RequestParam("name") String name, Model model) {

		Categories category = RepositoriesAccess.categoriesRepository.findById(Long.parseLong(id));

		if (category == null) {
			model.addAttribute("msg", "not found category to update");
			return "administratorSite/categoriesManager/updateOneCategory";
		}

		category.setName(name);
		RepositoriesAccess.categoriesRepository.save(category);

		model.addAttribute("category", category);
		model.addAttribute("msg", "Success");
		return "administratorSite/categoriesManager/updateOneCategory";
	}
}
