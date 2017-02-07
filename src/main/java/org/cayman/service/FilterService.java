package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cayman.dto.FilterDto;
import org.cayman.filter.BookFilter;
import org.cayman.filter.Operation;
import org.cayman.filter.SearchCriteria;
import org.cayman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Slf4j
@Component
public class FilterService {
    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;

    private final static String LANG = "language";
    private final static String YEAR = "year";
    private final static String CATEGORY = "category";
    private final static Comparator<Book> BOOK_COMPARATOR = (o1, o2) -> o1.getAddDate().isBefore(o2.getAddDate()) ? 1
                    : o1.getAddDate().isAfter(o2.getAddDate()) ? -1 : 0;

    @Autowired
    public FilterService(BookService bookService, AuthorService authorService,
                         PublisherService publisherService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.categoryService = categoryService;
    }

    public List<Book> filter(FilterDto filterDto) {
        Category category = extractCategory(filterDto);
        Language lang = extractLanguage(filterDto);
        String from = filterDto.getFrom();
        String to = filterDto.getTo();

        log.info("Filter books. From = " + filterDto.getFrom() + "; to = " + filterDto.getTo() +
                "; lang = " + filterDto.getLang() + "; category id = " + filterDto.getCategoryId());

        List<BookFilter> filters = new ArrayList<>();
        if (from != null && !from.isEmpty() && isNumeric(from)) {
            filters.add(new BookFilter(new SearchCriteria(YEAR, Operation.GT, from)));
        }

        if (to != null && !to.isEmpty() && isNumeric(to)) {
            filters.add(new BookFilter(new SearchCriteria(YEAR, Operation.LT, to)));
        }

        if (lang != null) {
            filters.add(new BookFilter(new SearchCriteria(LANG, Operation.EQ, lang)));
        }
        if (category != null) {
            filters.add(new BookFilter(new SearchCriteria(CATEGORY, Operation.EQ, category)));
        }

        List<Book> books = bookService.getByCriteria(filters);
        books.sort(BOOK_COMPARATOR);
        return books;
    }

    public List<Book> search(String keyword) {
        log.info("Search books by keyword: '" + keyword + "");
        if (keyword == null || keyword.isEmpty()) return bookService.getAll();

        Map<Integer, Book> result = bookService.searchByNameAndDescription(keyword);
        result.putAll(searchAuthorName(keyword));
        result.putAll(searchPublisherName(keyword));
        ArrayList<Book> books = new ArrayList<>(result.values());
        books.sort(BOOK_COMPARATOR);
        return books;
    }

    private Language extractLanguage(FilterDto filterDto) {
        try {
            return Language.valueOf(filterDto.getLang());
        } catch (Exception e) {
            return null;
        }
    }

    private Category extractCategory(FilterDto filterDto) {
        String categoryId = filterDto.getCategoryId();
        if (categoryId != null && StringUtils.isNumeric(categoryId)) {
            return categoryService.getById(Integer.parseInt(categoryId));
        }
        return null;
    }

    private Map<Integer, Book> searchAuthorName(String name) {
        Map<Integer, Book> result = new HashMap<>();
        List<Author> authors = authorService.searchByName(name);
        authors.forEach(a -> bookService.getByAuthorId(a.getId()).forEach(b -> result.put(b.getId(), b)));
        return result;
    }

    private Map<Integer, Book> searchPublisherName(String name) {
        Map<Integer, Book> result = new HashMap<>();
        List<Publisher> publishers = publisherService.searchByName(name);
        publishers.forEach(p -> bookService.getByPublisherId(p.getId()).forEach(b -> result.put(b.getId(), b)));
        return result;
    }
}
