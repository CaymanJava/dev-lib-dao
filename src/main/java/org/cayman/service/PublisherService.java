package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.cayman.model.Publisher;
import org.cayman.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "publisherService")
@Slf4j
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAll() {
        log.info("Get all publishers");
        return publisherRepository.findAll();
    }

    List<Publisher> searchByName(String name) {
        return publisherRepository.findByNameContainingIgnoreCase(name);
    }

    Publisher getByName(String name) {
        return publisherRepository.findFirstByName(name);
    }

    public Publisher getById(int id) {
        log.info("Get publisher by id = " + id);
        return publisherRepository.findOne(id);
    }

    public Publisher save(String name) {
        log.info("Save publisher with name " + name);
        Publisher publisher = getByName(name);
        if (publisher == null) {
            return publisherRepository.save(new Publisher(name));
        }
        return publisher;
    }

    public Publisher update(Publisher publisher) {
        log.info("Update publisher " + publisher);
        return publisherRepository.save(publisher);
    }

    public void delete(Publisher publisher) {
        log.info("Delete publisher " + publisher);
        publisherRepository.delete(publisher);
    }
}
