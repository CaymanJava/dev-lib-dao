package org.cayman.web.controller;


import org.cayman.dto.CategoryDto;
import org.cayman.model.Category;
import org.cayman.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Category> getAll(){
        return categoryService.getAll();
    }

    @RequestMapping(value = "one", method = RequestMethod.GET)
    public @ResponseBody Category getById(@RequestParam(value = "id") int id) {
        return categoryService.getById(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Category save(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(
                Category.builder()
                        .name(categoryDto.getName())
                        .image(categoryDto.getImage())
                        .build()
        );
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Category update(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.getById(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setImage(categoryDto.getImage());
        return categoryService.update(category);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public @ResponseBody Boolean delete(@RequestParam(value = "id") int id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            return false;
        }
        categoryService.delete(category);
        return true;
    }
}
