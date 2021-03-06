package com.hildebrandt.uni.controllers;

import com.hildebrandt.uni.domain.Book;
import com.hildebrandt.uni.domain.Category;
import com.hildebrandt.uni.helperclasses.BookCategory;
import com.hildebrandt.uni.services.BookService;
import com.hildebrandt.uni.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    /*---Site for new book---*/
    @RequestMapping( path = "/book/show/{id}")
    public String showSingleBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/show";
    }

    /*---Site for book details---*/
    @RequestMapping( path = "/book/create")
    public String loadForm(Model model) {
        model.addAttribute("book", new Book());
        Set<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        //TODO dateformat on html template should show year only
        return "books/create";
    }

    /*---Add new book---*/
    @RequestMapping(path = "/book", method = RequestMethod.POST)
    public String save(Book book) {
        long id = bookService.create(book);
        return "redirect:/books";
    }

    /*---Get a book by id---*/
    @GetMapping("/book/{id}")
    public String get(@PathVariable("id") long id, Model model) {
        Book book = bookService.findById(id);
        Set<Category> categories = categoryService.getCategories();
        model.addAttribute("allCategories", categories);
        model.addAttribute("book", book);
        //TODO can create category but not edit
        //TODO must allow multiple categories
        return "books/edit";
    }

    /*---get all books---*/
    @GetMapping({"/books", "/"})
    public String list(Model model) {
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("categories", categoryService.getCategories());
        return "books/index";
    }

    /*---Update a book by id---*/
    @RequestMapping(path = "/book/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") long id, Book book) {
        bookService.update(id, book);
        return "redirect:/books";    }

    /*---Delete a book by id---*/
    @RequestMapping(path = "/book/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
