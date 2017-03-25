package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.cayman.filter.BookFilter;
import org.cayman.model.Book;
import org.cayman.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "bookService")
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll(){
        log.info("Get all books");
        return bookRepository.findAllByOrderByAddDateDesc();
    }

    public List<Book> getLastNBooks(int count) {
        log.info("Get last " + count + " added books");
        return bookRepository.findWithPageable(new PageRequest(0, count, Sort.Direction.DESC, "addDate"));
    }

    public List<Book> getLastBooksInCategory(int count, int categoryId) {
        log.info("Get last " + count + " added books from category (id = " + categoryId + ")");
        return bookRepository.findByCategoryId(categoryId, new PageRequest(0, count, Sort.Direction.DESC, "addDate"));
    }

    List<Book> getByCriteria(List<BookFilter> filters) {
        if (filters.size() == 0) {
            return getAll();
        }

        Specifications<Book> conditions = Specifications.where(filters.get(0));

        if (filters.size() > 1) {
            for (int i = 1; i < filters.size() ; i++) {
                conditions = conditions.and(filters.get(i));
            }
        }

        return bookRepository.findAll(conditions);
    }

    Map<Integer, Book> searchByNameAndDescription(String searchPhrase) {
        Map <Integer, Book> result = new HashMap<>();
        bookRepository.findByNameContainingIgnoreCaseOrderByAddDateDesc(searchPhrase).forEach(b -> result.put(b.getId(), b));
        bookRepository.findByDescriptionContainingIgnoreCaseOrderByAddDateDesc(searchPhrase).forEach(b -> result.put(b.getId(), b));
        return result;
    }

    public Book getById(int id) {
       log.debug("Get book with id " + id);
       return bookRepository.findOne(id);
    }

    public List<Book> getByAuthorId(int id) {
        log.info("Get books by author id " + id);
        return bookRepository.findByAuthorIdOrderByAddDateDesc(id);
    }

    public List<Book> getByCategoryId(int id) {
        log.info("Get books by category id " + id);
        return bookRepository.findByCategoryIdOrderByAddDateDesc(id);
    }

    public List<Book> getByPublisherId(int id) {
        log.info("Get books by publisher id " + id);
        return bookRepository.findByPublisherIdOrderByAddDateDesc(id);
    }

    public Book save(Book book) {
        log.info("Save book with name " + book.getName());
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        log.info("Update book with name " + book.getName());
        return bookRepository.save(book);
    }

    public void delete(Book book) {
        log.info("Delete book with name " + book.getName());
        bookRepository.delete(book);
    }

    public void incrementBookViews(int id) {
        log.info("Increment book (id = " + id + ") views.");
        Book book = getById(id);
        if (book == null) return;
        book.setViews(book.getViews() + 1);
        save(book);
    }
}
