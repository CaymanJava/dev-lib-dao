package org.cayman.repository;


import org.cayman.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    List<Book> findAllByOrderByAddDateDesc();
    List<Book> findByAuthorIdOrderByAddDateDesc(int id);
    List<Book> findByCategoryIdOrderByAddDateDesc(int id);
    List<Book> findByPublisherIdOrderByAddDateDesc(int id);
    List<Book> findByNameContainingIgnoreCaseOrderByAddDateDesc(String name);
    List<Book> findByDescriptionContainingIgnoreCaseOrderByAddDateDesc(String name);

    @Query(value = "select b from Book b")
    List<Book> findWithPageable(Pageable pageable);
}
