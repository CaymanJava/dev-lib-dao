package org.cayman.service;

import org.cayman.model.Author;
import org.cayman.model.Book;
import org.cayman.model.Category;
import org.cayman.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.*;

public class AbstractTest {
    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PublisherService publisherService;

    List<Book> booksForTest;
    List<Publisher> publishersForTest;
    List<Category> categoriesForTest;
    List<Author> authorsForTest;

    public void setUp() throws Exception {
        setUpAuthors();
        setUpPublisher();
        setUpCategories();
        setUpBooks();
    }

    private void setUpAuthors() {
        createAuthorsForTest().forEach(a -> authorService.save(a.getName()));
        authorsForTest = authorService.getAll();
    }

    private void setUpPublisher() {
        createPublishersForTest().forEach(p -> publisherService.save(p.getName()));
        publishersForTest = publisherService.getAll();
    }

    private void setUpCategories () {
        createCategoriesForTest().forEach(categoryService::save);
        categoriesForTest = categoryService.getAll();
    }

    private void  setUpBooks () {
        booksForTest = createBooksForTest();
        Book book1 = booksForTest.get(0);
        Book book2 = booksForTest.get(1);
        Book book3 = booksForTest.get(2);

        book1.setAuthor(authorsForTest);
        book1.setCategory(categoriesForTest.get(0));
        book1.setPublisher(publishersForTest.get(0));

        book2.setAuthor(Arrays.asList(authorsForTest.get(0), authorsForTest.get(1)));
        book2.setCategory(categoriesForTest.get(1));
        book2.setPublisher(publishersForTest.get(1));

        book3.setAuthor(Arrays.asList(authorsForTest.get(2)));
        book3.setCategory(categoriesForTest.get(0));
        book3.setPublisher(publishersForTest.get(0));

        booksForTest.forEach(bookService::save);
        booksForTest = bookService.getAll();
    }

    public void tearDown() throws Exception {
        booksForTest.forEach(bookService::delete);
        publishersForTest.forEach(publisherService::delete);
        categoriesForTest.forEach(categoryService::delete);
        authorsForTest.forEach(authorService::delete);
    }
}
