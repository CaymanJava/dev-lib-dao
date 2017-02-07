package org.cayman.service;

import org.cayman.config.SpringApplicationConfig;
import org.cayman.model.Author;
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
import static utils.TestUtils.createAuthorsForTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationConfig.class, loader = SpringApplicationContextLoader.class)
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    private List<Author> authorsForTest;


    @Before
    public void setUp() {
        authorsForTest = createAuthorsForTest();
        authorsForTest.forEach(a -> authorService.save(a.getName()));
    }

    @Test
    public void getAllTest() {
        List<Author> all = authorService.getAll();
        assertTrue(all.size() >= authorsForTest.size());
    }

    @Test
    public void getByNameTest() throws Exception {
        Author ivan = authorService.getByName("Ivan Golovach");
        assertTrue(ivan.getName().equals("Ivan Golovach"));
    }

    @Test
    public void getByFakeNameTest(){
        Author dima = authorService.getByName("Dima");
        assertTrue(dima == null);
    }


    @Test
    public void updateTest() throws Exception {
        Author ken = authorService.getByName("Ken Horstmann");
        ken.setName("John Zoiberg");
        authorService.update(ken);
        assertTrue(authorService.getByName("Ken Horstmann") == null);
        assertTrue(authorService.getByName("John Zoiberg") != null);
    }

    @Test
    public void deleteTest() throws Exception {
        authorService.save("Petr");
        Author petr = authorService.getByName("Petr");
        assertTrue(petr != null);
        authorService.delete(petr);
        petr = authorService.getByName("Petr");
        assertTrue(petr == null);
    }

    @After
    public void tearDown() {
        authorsForTest.forEach(a -> authorService.delete(a));
    }

}