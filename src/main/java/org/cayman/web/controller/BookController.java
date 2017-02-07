package org.cayman.web.controller;

import org.cayman.dto.BookDto;
import org.cayman.model.*;
import org.cayman.service.AuthorService;
import org.cayman.service.BookService;
import org.cayman.service.CategoryService;
import org.cayman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("book")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService,
                          CategoryService categoryService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Book> getAll(){
        return bookService.getAll();
    }

    @RequestMapping(value = "one", method = RequestMethod.GET)
    public @ResponseBody Book getBookById(@RequestParam(value = "id") int id) {
        bookService.incrementBookViews(id);
        return bookService.getById(id);
    }

    @RequestMapping(value = "one/update", method = RequestMethod.GET)
    public @ResponseBody Book getBookByIdForUpdate(@RequestParam(value = "id") int id) {
        return bookService.getById(id);
    }

    @RequestMapping(value = "one/author", method = RequestMethod.GET)
    public @ResponseBody List<Book> getByAuthorId(@RequestParam(value = "id") int id) {
        return bookService.getByAuthorId(id);
    }

    @RequestMapping(value = "one/category", method = RequestMethod.GET)
    public @ResponseBody List<Book> getByCategoryId(@RequestParam(value = "id") int id) {
        return bookService.getByCategoryId(id);
    }

    @RequestMapping(value = "one/publisher", method = RequestMethod.GET)
    public @ResponseBody List<Book> getByPublisherId(@RequestParam(value = "id") int id) {
        return bookService.getByPublisherId(id);
    }

    @RequestMapping(value = "some", method = RequestMethod.GET)
    public @ResponseBody List<Book> getLastNBooks(@RequestParam(value = "count") int count) {
        return bookService.getLastNBooks(count);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Book save(@RequestBody BookDto bookDto) {
        List<Author> authors = bookDto.getAuthors().stream().map(authorService::save).collect(Collectors.toList());
        Publisher publisher = publisherService.save(bookDto.getPublisher());
        Category category = categoryService.getById(bookDto.getCategoryId());
        return bookService.
                save(Book.builder()
                        .name(bookDto.getName())
                        .language(Language.valueOf(bookDto.getLanguage()))
                        .year(bookDto.getYear())
                        .author(authors)
                        .publisher(publisher)
                        .image(bookDto.getImage())
                        .fileId(bookDto.getLink())
                        .description(bookDto.getDescription())
                        .category(category)
                        .pageCount(bookDto.getPageCount())
                        .rating(Rating.defaultRating())
                        .addDate(LocalDateTime.now())
                        .build()
                );
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Book update(@RequestBody BookDto bookDto) {
        List<Author> authors = bookDto.getAuthors().stream().map(authorService::save).collect(Collectors.toList());
        Publisher publisher = publisherService.save(bookDto.getPublisher());
        Category category = categoryService.getById(bookDto.getCategoryId());
        Book book = bookService.getById(bookDto.getId());
        book.setName(bookDto.getName());
        book.setLanguage(Language.valueOf(bookDto.getLanguage()));
        book.setYear(bookDto.getYear());
        book.setAuthor(authors);
        book.setPublisher(publisher);
        book.setImage(bookDto.getImage());
        book.setFileId(bookDto.getLink());
        book.setDescription(bookDto.getDescription());
        book.setCategory(category);
        book.setPageCount(bookDto.getPageCount());
        return bookService.save(book);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public @ResponseBody Boolean delete(@RequestParam(value = "id") int id) {
        Book book = bookService.getById(id);
        if (book == null) {
            return false;
        }
        bookService.delete(book);
        return true;
    }



}
