package edu.baylor.flarn.services;


import edu.baylor.flarn.exceptions.DefaultCategoryModificationException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.repositories.CategoryRepository;
import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Activity service includes CRUD operations for the Category model.
 * It prevents any kind of modification of the default category (Other).
 * It throws DefaultCategoryModificationException whenever such modification is attempted.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) throws DefaultCategoryModificationException {
        if (category.getName() != null && category.getName().equals(Category.DEFAULT_CATEGORY_NAME)) {
            throw new DefaultCategoryModificationException();
        }
        return categoryRepository.save(category);
    }

    // only called from data initializer
    public void createDefaultCategory() {
        Category category = new Category();
        category.setName(Category.DEFAULT_CATEGORY_NAME);
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ResponseBody deleteCategory(Long id) throws DefaultCategoryModificationException, RecordNotFoundException {
        Category category = getCategory(id);
        if (category.getName() != null && category.getName().equals(Category.DEFAULT_CATEGORY_NAME)) {
            throw new DefaultCategoryModificationException();
        }

        // assign default category to all problems of deleted category
        Category defaultCategory = getDefaultCategory();
        category.getProblems().forEach(e -> e.setCategory(defaultCategory));
        defaultCategory.getProblems().addAll(category.getProblems());
        categoryRepository.save(defaultCategory);

        try {
            categoryRepository.deleteById(id);
            return new ResponseBody(200, "Successful");
        } catch (Exception e) {
            return new ResponseBody(500, e.getMessage());
        }
    }

    public Category updateCategory(Category category) throws DefaultCategoryModificationException {
        if (category.getName() != null && category.getName().equals(Category.DEFAULT_CATEGORY_NAME)) {
            throw new DefaultCategoryModificationException();
        }
        return categoryRepository.save(category);
    }

    public Category getCategory(long id) throws RecordNotFoundException {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new RecordNotFoundException("Category not found with id: " + id);
        }
        return category;
    }

    public Category getCategoryByName(String name) throws RecordNotFoundException {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new RecordNotFoundException("Category not found with name: " + name);
        }
        return category;
    }

    public Category getDefaultCategory() throws RecordNotFoundException {
        Category category = categoryRepository.findByName(Category.DEFAULT_CATEGORY_NAME);
        if (category == null) {
            throw new RecordNotFoundException("Default category not found");
        }
        return category;
    }
}
