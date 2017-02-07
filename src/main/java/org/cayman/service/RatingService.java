package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.cayman.dto.RatingDto;
import org.cayman.dto.VoteUpdateDto;
import org.cayman.model.Rating;
import org.cayman.model.Vote;
import org.cayman.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "ratingService")
@Slf4j
public class RatingService {
    private final RatingRepository ratingRepository;
    private final VoteService voteService;

    @Autowired
    public RatingService(RatingRepository ratingRepository, VoteService voteService) {
        this.ratingRepository = ratingRepository;
        this.voteService = voteService;
    }

    public List<Rating> getAll() {
        log.info("Get all rating");
        return ratingRepository.findAll();
    }

    public List<Vote> getAllVotes() {
        log.info("Get all votes");
        return voteService.getAll();
    }

    private Rating getById(int id) {
        log.info("Get rating by id " + id);
        return ratingRepository.findOne(id);
    }

    private Rating update(Rating rating) {
        log.info("Update rating " + rating);
        return ratingRepository.save(rating);
    }

    public Rating addNewValue(RatingDto ratingDto) {
        log.info("Add new vote " + ratingDto);
        VoteUpdateDto voteUpdateDto = voteService.saveOrUpdate(ratingDto.getUserId(), ratingDto.getBookId(), ratingDto.getValue());
        Rating rating = getById(ratingDto.getRatingId());
        log.info("Count new value for rating id = " + rating.getId());
        rating.setPoints(rating.getPoints() + voteUpdateDto.getDifference());
        rating.setVotes(voteUpdateDto.isUpdate() ? rating.getVotes() : rating.getVotes() + 1);
        update(rating);
        return rating;
    }
}
