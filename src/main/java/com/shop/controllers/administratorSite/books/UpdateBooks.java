package com.shop.controllers.administratorSite.books;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.data.tables.Books;
import com.shop.data.tables.Categories;
import com.shop.data.tables.Pictures;
import com.shop.others.RepositoriesAccess;

@Controller
@RequestMapping("administratorSite/books")
public class UpdateBooks {

	@RequestMapping("/update")
	public String updateSite(Model model) {
		Iterable<Books> books = RepositoriesAccess.booksRepository.findAll();
		Iterable<Categories> allCategories = RepositoriesAccess.categoriesRepository.findAll();

		model.addAttribute("books", books);
		model.addAttribute("categories", allCategories);
		return "administratorSite/booksCRUD/update";
	}
	
	@RequestMapping(value = "updateBook/{bookId}")
	public String updateOneSite(@PathVariable Long bookId, Model model) {
		Books foundBook = RepositoriesAccess.booksRepository.findById(bookId);

		if (foundBook == null)
			model.addAttribute("msg", "not found");

		Iterable<Categories> categories = RepositoriesAccess.categoriesRepository.findAll();

		model.addAttribute("book", foundBook);
		model.addAttribute("categories", categories);
		return "/administratorSite/booksCRUD/updateOneBook";
	}

	@RequestMapping("updateBook/update")
	public String updateBook(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("author") String author, @RequestParam("language") String language,
			@RequestParam("price") String price, @RequestParam("description") String description, Model model,
			HttpServletRequest request) {

		Iterable<Categories> allCategories = RepositoriesAccess.categoriesRepository.findAll();
		model.addAttribute("categories", allCategories);

		Books foundBook = RepositoriesAccess.booksRepository.findById(Long.parseLong(id));
		if (foundBook == null) {
			model.addAttribute("book", foundBook);
			model.addAttribute("msg", "not found book to update");
			return "administratorSite/booksCRUD/updateOneBook";
		}
		Iterable<Categories> categories = RepositoriesAccess.categoriesRepository.findAll();

		String categoryName = null;
		boolean moreThanOne = false;

		for (Categories x : categories) {
			if (request.getParameter(x.getName()) != null) {
				categoryName = x.getName();

				if (moreThanOne) {
					model.addAttribute("msgError", "Check only 1 category");
					return "administratorSite/booksCRUD/create";
				}
				moreThanOne = true;
			}
		}
		if (moreThanOne == false) {
			model.addAttribute("msgError", "You have to check 1 category");
			return "administratorSite/booksCRUD/create";
		}

		Categories foundCategory = RepositoriesAccess.categoriesRepository.findByName(categoryName);

		foundBook.setName(name);
		foundBook.setAuthor(author);
		foundBook.setPrice(new BigDecimal(price));
		foundBook.setDescription(description);
		foundBook.setLanguage(language);
		RepositoriesAccess.booksRepository.save(foundBook);

		foundCategory.getBooks().add(foundBook);
		RepositoriesAccess.categoriesRepository.save(foundCategory);

		model.addAttribute("book", foundBook);
		model.addAttribute("msg", "Success");
		return "administratorSite/booksCRUD/updateOneBook";
	}
	
	@RequestMapping(value = "updateBook/deletePicture")
	public ModelAndView deletePicture(@RequestParam("bookId") Long bookId, @RequestParam("pictureId") Long pictureId,
			RedirectAttributes red) {
		Books foundBook = RepositoriesAccess.booksRepository.findById(bookId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:http://localhost:8080/CRUD/administratorSite/books/updateBook/" + bookId);

		for (Pictures x : foundBook.getPictures()) {
			if (x.getId() == pictureId) {
				Pictures foundPicture = RepositoriesAccess.picturesRepository.findById(pictureId);
				foundBook.getPictures().remove(foundPicture);
				RepositoriesAccess.booksRepository.save(foundBook);
				RepositoriesAccess.picturesRepository.delete(foundPicture);
				red.addFlashAttribute("msg", "Success");
				return modelAndView;
			}
		}
		red.addFlashAttribute("msg", "Error with delete picture");
		return modelAndView;
	}
}
