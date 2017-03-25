package org.cayman.service;

import lombok.extern.slf4j.Slf4j;
import org.cayman.config.SpringApplicationConfig;
import org.cayman.dto.FilterDto;
import org.cayman.model.Book;
import org.cayman.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationConfig.class, loader = SpringApplicationContextLoader.class)
@Slf4j
public class FilterServiceTest extends AbstractTest{

    private final static String EMPTY = "";

    @Autowired
    private FilterService filterService;

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception{
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void filter() throws Exception {
        List<Book> ruFilter = filterService.filter(new FilterDto(0, 0, "RU", 0));
        assertTrue(ruFilter.size() > 0);
        ruFilter.forEach(b -> log.info(b.toString()));
        Category java = categoryService.getByName("Java");
        List<Book> categoryFilter = filterService.filter(new FilterDto(0, 0, "EN", java.getId()));
        assertTrue(categoryFilter.size() > 0);
        List<Book> categoryAndYearFilter = filterService.filter(new FilterDto(2005, 0, null, java.getId()));
        assertTrue(categoryAndYearFilter.size() > 0);
    }

    @Test
    public void search() throws Exception {
        List<Book> ivanSearch = filterService.search("mart");
        assertTrue(ivanSearch.size() > 0);
        ivanSearch.forEach(b -> log.info(b.toString()));
    }

}