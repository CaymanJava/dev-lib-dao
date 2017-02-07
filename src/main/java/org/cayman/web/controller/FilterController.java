package org.cayman.web.controller;

import org.cayman.dto.FilterDto;
import org.cayman.dto.SearchDto;
import org.cayman.model.Book;
import org.cayman.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilterController {
    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Book> filter(@RequestBody FilterDto filterDto) {
        return filterService.filter(filterDto);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Book> search(@RequestBody SearchDto searchDto) {
        return filterService.search(searchDto.getKeyword());
    }
}
