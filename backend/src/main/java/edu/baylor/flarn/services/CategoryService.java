package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.repositories.CategoryRepository;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.resources.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ResponseBody deleteProblem(Long id) {
        try {
            categoryRepository.deleteById(id);
            return  new ResponseBody(200,"Successful");
        }
        catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }
    }

    public Category updateProblem(UpdateRequest<Category> updateRequest) throws RecordNotFoundException {
        Category category = categoryRepository.findById(updateRequest.getId()).orElse(null);

        if (category == null) {
            throw new RecordNotFoundException("user not found with id " + updateRequest.getId());
        }
        category.setName(updateRequest.getObj().getName());

        return categoryRepository.save(category);
    }

    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
