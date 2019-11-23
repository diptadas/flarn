package edu.baylor.flarn.services;


import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.repositories.CategoryRepository;
import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ResponseBody deleteProblem(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseBody(200, "Successful");
        } catch (Exception e) {
            return new ResponseBody(500, e.getMessage());
        }
    }

    public Category updateProblem(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
