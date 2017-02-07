package org.cayman.web.controller;


import org.cayman.dto.RatingDto;
import org.cayman.model.Rating;
import org.cayman.model.Vote;
import org.cayman.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Rating> getAllRatings() {
        return ratingService.getAll();
    }

    @RequestMapping(value = "votes", method = RequestMethod.GET)
    public @ResponseBody List<Vote> getAllVotes(){
        return ratingService.getAllVotes();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Rating addNewValue(@RequestBody RatingDto ratingDto) {
        return ratingService.addNewValue(ratingDto);
    }

}
