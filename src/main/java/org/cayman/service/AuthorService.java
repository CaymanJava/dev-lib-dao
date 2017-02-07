package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.cayman.model.Author;
import org.cayman.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "authorService")
@Slf4j
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll(){
        log.debug("Get all authors");
        return authorRepository.findAll();
    }

    List<Author> searchByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    public Author getById(int id) {
        log.debug("Get author with ID " + id);
        return authorRepository.findOne(id);
    }

    public Author getByName(String name) {
        log.debug("Get author with name " + name);
        return authorRepository.findFirstByName(name);
    }

    public Author save(String name) {
        log.info("Save author with name " + name);
        Author author = getByName(name);
        if (author == null) {
            return authorRepository.save(new Author(name));
        }
        return author;
    }

    public Author update(Author author) {
        log.info("Update author with name  " + author.getName());
        return authorRepository.save(author);
    }

    public void delete(Author author) {
        log.info("Delete author with name  " + author.getName());
        authorRepository.delete(author);
    }
}
