package org.cayman.service;

import lombok.extern.slf4j.Slf4j;
import org.cayman.config.SpringApplicationConfig;
import org.cayman.dto.RatingDto;
import org.cayman.model.Book;
import org.cayman.model.Rating;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationConfig.class, loader = SpringApplicationContextLoader.class)
@Slf4j
public class RatingServiceTest extends AbstractTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private BookService bookService;

    @Autowired
    private VoteService voteService;

    @Before
    public void setUp() throws Exception{
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void getAll() throws Exception {
        List<Rating> all = ratingService.getAll();
        all.forEach(r -> log.info(r.toString()));
        assertTrue(all.size() > 0);
    }

    @Test
    public void addNewValue() throws Exception {
        List<Book> all = bookService.getAll();
        Book book = all.get(0);
        Rating rating = book.getRating();
        Rating refreshRating = ratingService.addNewValue(new RatingDto(1, book.getId(), rating.getId(), 5));
        assertTrue(refreshRating.getPoints() - rating.getPoints() == 5
                && refreshRating.getVotes() - rating.getVotes() == 1);
        refreshRating = ratingService.addNewValue(new RatingDto(1, book.getId(), rating.getId(), 2));
        assertTrue(refreshRating.getPoints() == 2 && refreshRating.getVotes()  == 1);
        log.info(refreshRating.toString());
    }

}