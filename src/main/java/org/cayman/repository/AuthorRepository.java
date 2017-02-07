package org.cayman.repository;


import org.cayman.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer >/*, JpaSpecificationExecutor<Author>*/ {
    Author findFirstByName(String name);
    List<Author> findByNameContainingIgnoreCase(String name);
}
