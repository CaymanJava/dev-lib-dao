package org.cayman.web.controller;


import org.cayman.dto.PublisherDto;
import org.cayman.model.Publisher;
import org.cayman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "publisher")
public class PublisherController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Publisher> getAll(){
        return publisherService.getAll();
    }

    @RequestMapping(value = "one", method = RequestMethod.GET)
    public @ResponseBody Publisher getById(@RequestParam(value = "id") int id){
        return publisherService.getById(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Publisher save(@RequestBody PublisherDto publisherDto) {
        return publisherService.save(publisherDto.getName());
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Publisher update(@RequestBody PublisherDto publisherDto) {
        Publisher publisher = publisherService.getById(publisherDto.getId());
        publisher.setName(publisherDto.getName());
        return publisherService.update(publisher);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public @ResponseBody Boolean delete(@RequestParam(value = "id") int id) {
        Publisher publisher = publisherService.getById(id);
        if (publisher == null) {
            return false;
        }
        publisherService.delete(publisher);
        return true;
    }
}
