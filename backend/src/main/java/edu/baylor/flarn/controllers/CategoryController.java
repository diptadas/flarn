package edu.baylor.flarn.controllers;


import edu.baylor.flarn.exceptions.DefaultCategoryModificationException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // don't allow creating "other" category, it should be created using DataInitializer
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(@RequestBody Category category) throws DefaultCategoryModificationException {
        return categoryService.createCategory(category);
    }

    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable long id) throws RecordNotFoundException {
        return categoryService.getCategory(id);
    }

    // don't allow updating "other" category
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(@RequestBody Category category) throws DefaultCategoryModificationException {
        return categoryService.updateCategory(category);
    }

    // deletes the category and associate all problems of this category with "other" category
    // don't allow deleting "other"category
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseBody deleteCategory(@PathVariable("id") Long id) throws DefaultCategoryModificationException, RecordNotFoundException {
        return categoryService.deleteCategory(id);
    }

}
