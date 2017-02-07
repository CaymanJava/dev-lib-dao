package org.cayman.repository;


import org.cayman.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>, JpaSpecificationExecutor<Publisher> {
    Publisher findFirstByName(String name);
    List<Publisher> findByNameContainingIgnoreCase(String name);
}
