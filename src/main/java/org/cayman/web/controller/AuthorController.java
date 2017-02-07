package org.cayman.web.controller;


import org.cayman.dto.AuthorDto;
import org.cayman.model.Author;
import org.cayman.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Author> getAll() {
        return authorService.getAll();
    }

    @RequestMapping(value = "one", method = RequestMethod.GET)
    public @ResponseBody Author getById(@RequestParam(value = "id") int id) {
        return authorService.getById(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public @ResponseBody Author save(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "lastname") String lastname) {
        return authorService.save(name + " " + lastname);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Author saveRest(@RequestBody AuthorDto author) {
        return authorService.save(author.getName());
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public @ResponseBody Author update(@RequestParam(value = "id") int id,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam(value = "lastname") String lastname){
        return updateAuthor(id, name + " " + lastname);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Author updateRest(@RequestBody AuthorDto author) {
        return updateAuthor(author.getId(), author.getName());
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public @ResponseBody Boolean delete (@RequestParam(value = "id") int id) {
        Author author = authorService.getById(id);
        if (author == null) {
            return false;
        }
        authorService.delete(author);
        return true;
    }

    private Author updateAuthor(int id, String name) {
        Author author = authorService.getById(id);
        author.setName(name);
        return authorService.update(author);
    }
}
