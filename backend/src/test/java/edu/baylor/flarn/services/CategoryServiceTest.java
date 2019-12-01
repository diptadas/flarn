package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.DefaultCategoryModificationException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Contains the unit  & Integration test for category service
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void createCategory() throws DefaultCategoryModificationException {
        Category category = new Category();
        category.setName("Test Category");

        Category saved = categoryService.createCategory(category);
        assertEquals(saved, category);

    }

    @Test
    void getCategoryById() throws RecordNotFoundException {
        Category category = categoryService.getCategoryById(8L);
        assertEquals(category.getName(), "Other");
    }

    @Test
    void getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        assertEquals(categories.size(), 4);
    }


    @Test
    void updateCategory() {

    }


    @Test
    void getCategoryByName() throws RecordNotFoundException {
        String categoryName = Category.DEFAULT_CATEGORY_NAME;
        Category category = categoryService.getCategoryByName(categoryName);
        assertEquals(category.getName(), categoryName);
    }

    @Test
    void deleteCategory() throws RecordNotFoundException, DefaultCategoryModificationException {
        categoryService.deleteCategory(10L);
        assertThatThrownBy(() -> categoryService.getCategoryById(10L)).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("Category not found with id "+10);
    }

    @Test
    void getDefaultCategory() throws RecordNotFoundException {
        Category category = categoryService.getDefaultCategory();
        assertEquals(category.getName(), Category.DEFAULT_CATEGORY_NAME);
    }

    // create,read,update,delete test for Category
    @Test
    public void CRUDCategory() throws RecordNotFoundException, DefaultCategoryModificationException {

        Category category = new Category();
        category.setName("New Category");


        assertThatThrownBy(() -> categoryService.getCategoryByName(category.getName())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining(String.format("Category with name %s not  found: ", category.getName()));

        //save category
        Category savedCategory = categoryService.createCategory(category);

        //Test record
        assertEquals(savedCategory, category);
        assertNotNull(categoryService.getCategoryByName(category.getName()));


        //delete category
        categoryService.deleteCategory(savedCategory.getId());
        assertThatThrownBy(() -> categoryService.getCategoryByName(category.getName())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining(String.format("Category with name %s not  found: ", category.getName()));

    }

}