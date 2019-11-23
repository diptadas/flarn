package edu.baylor.flarn.controllers;


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


    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }


    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }


    @GetMapping("/{id}")
    public Category getCategory(@PathVariable long id) {
        return categoryService.getCategory(id);
    }


    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    //Todo: Check moderator also
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseBody deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }


}
