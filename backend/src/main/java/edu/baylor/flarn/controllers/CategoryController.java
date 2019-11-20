package edu.baylor.flarn.controllers;


import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.resources.UpdateRequest;
import edu.baylor.flarn.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @PostMapping
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
    public Category upDateCategory(@RequestBody UpdateRequest<Category> updateRequest) throws RecordNotFoundException {
        return categoryService.updateProblem(updateRequest);
    }

    //Todo: Check moderator also
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseBody deleteProblem(@PathVariable("id") Long id) {
        return categoryService.deleteProblem(id);
    }


}
