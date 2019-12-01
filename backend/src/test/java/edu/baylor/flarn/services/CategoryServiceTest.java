package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.DefaultCategoryModificationException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void createCategory() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void getCategory() {
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