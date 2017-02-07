package org.cayman.service;

import org.cayman.config.SpringApplicationConfig;
import org.cayman.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.cayman.model.Rating.defaultRating;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationConfig.class, loader = SpringApplicationContextLoader.class)
public class BookServiceTest extends AbstractTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void getAll() throws Exception {
        assertTrue(bookService.getAll().size() >= booksForTest.size());
    }

    @Test
    public void getBooksByAuthorId() throws Exception {
        Author ken = authorService.getByName("Ken Horstmann");
        List<Book> booksByAuthorId = bookService.getByAuthorId(ken.getId());
        assertTrue(booksByAuthorId.size() > 0);
        booksByAuthorId.forEach(System.out::println);
    }

    @Test
    public void getBooksByCategoryId() {
        Category java = categoryService.getByName("Java");
        List<Book> booksByCategoryId = bookService.getByCategoryId(java.getId());
        assertTrue(booksByCategoryId.size() > 0);
        booksByCategoryId.forEach(System.out::println);
    }

    @Test
    public void getByPublisherId() {
        Publisher headFirst = publisherService.getByName("HeadFirst");
        List<Book> booksByPublisherId = bookService.getByPublisherId(headFirst.getId());
        assertTrue(booksByPublisherId.size() > 0);
        booksByPublisherId.forEach(System.out::println);
    }

    @Test
    public void update() throws Exception {
        Category java = categoryService.getByName("Java");
        Publisher headFirst = publisherService.getByName("HeadFirst");
        Book book = Book
                .builder()
                .name("java for professionals")
                .language(Language.EN)
                .year(2015)
                .author(authorsForTest)
                .publisher(headFirst)
                .image("111111")
                .fileId("http://mylink.com")
                .description("super book")
                .category(java)
                .pageCount(650)
                .rating(defaultRating())
                .addDate(LocalDateTime.now())
                .build();
        Book saveBook = bookService.save(book);
        Book byId = bookService.getById(saveBook.getId());
        byId.setName("new name");
        bookService.update(byId);
        byId = bookService.getById(saveBook.getId());
        assertTrue(byId.getName().equals("new name"));
        bookService.delete(byId);
    }
}