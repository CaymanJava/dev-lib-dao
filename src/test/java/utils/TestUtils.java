package utils;


import org.cayman.model.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.cayman.model.Rating.defaultRating;

public class TestUtils {
    public static List<Author> createAuthorsForTest() {
        Author martin = Author.builder().name("Martin Fowler").build();
        Author ken = Author.builder().name("Ken Horstmann").build();
        Author ivan = Author.builder().name("Ivan Golovach").build();
        return Arrays.asList(martin, ken, ivan);
    }

    public static List<Book> createBooksForTest() {
        Book book1 = Book
                .builder()
                .name("java for professionals")
                .language(Language.EN)
                .year(1999)
                .image("111111")
                .fileId("http://mylink.com")
                .description("super book")
                .pageCount(650)
                .rating(defaultRating())
                .addDate(LocalDateTime.now())
                .build();
        Book book2 = Book
                .builder()
                .name("java for kids by martin")
                .language(Language.EN)
                .year(2000)
                .image("111webwewr111")
                .fileId("http://mylink.com")
                .description("super book for kids")
                .pageCount(100)
                .rating(defaultRating())
                .addDate(LocalDateTime.now())
                .build();
        Book book3 = Book
                .builder()
                .name("Spring IN action 4")
                .language(Language.RU)
                .year(2016)
                .image("ewvwbbw111")
                .fileId("http://mylink.com")
                .description("Spring")
                .pageCount(100)
                .rating(defaultRating())
                .addDate(LocalDateTime.now())
                .build();

        return Arrays.asList(book1, book2, book3);
    }

    public static List<Publisher> createPublishersForTest() {
        Publisher headFirst = Publisher.builder().name("HeadFirst").build();
        Publisher manning = Publisher.builder().name("Manning").build();
        return Arrays.asList(headFirst, manning);
    }

    public static List<Category> createCategoriesForTest() {
        Category java = Category.builder().name("Java").image("link_to_image").build();
        Category javaScript = Category.builder().name("JavaScript").image("link_to_image").build();
        return Arrays.asList(java, javaScript);
    }
}
