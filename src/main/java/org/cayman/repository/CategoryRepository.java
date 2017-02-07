package org.cayman.repository;


import org.cayman.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findFirstByName(String name);
}
