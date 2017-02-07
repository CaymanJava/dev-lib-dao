package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.cayman.model.Category;
import org.cayman.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "categoryService")
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll(){
        log.info("Get all categories");
        return categoryRepository.findAll();
    }

    public Category getById(int id) {
        log.debug("Get category with ID " + id);
        return categoryRepository.findOne(id);
    }

    Category getByName(String name) {
        log.debug("Get category with name " + name);
        return categoryRepository.findFirstByName(name);
    }

    public Category save(Category category) {
        log.info("Save category with name " + category.getName());
        Category oldCategory = getByName(category.getName());
        if (oldCategory == null) {
            return categoryRepository.save(category);
        }
        return oldCategory;
    }

    public Category update(Category category) {
        log.info("Update category with name " + category.getName());
        return categoryRepository.save(category);
    }

    public void delete(Category category) {
        log.info("Delete category with name " + category.getName());
        categoryRepository.delete(category);
    }
}
