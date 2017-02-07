package org.cayman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int id;
    private String name;
    private String language;
    private int year;
    private List<String> authors;
    private String publisher;
    private String image;
    private String link;
    private String description;
    private int categoryId;
    private int pageCount;
}
