package org.cayman.service;


import lombok.extern.slf4j.Slf4j;
import org.cayman.dto.VoteUpdateDto;
import org.cayman.model.Vote;
import org.cayman.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    private Vote getById(Vote.VotePK id) {
        log.info("Get vote by id " + id);
        return voteRepository.findOne(id);
    }

    List<Vote> getAll() {
        return voteRepository.findAll();
    }

    VoteUpdateDto saveOrUpdate(int userId, int bookId, int value) {
        Vote.VotePK id = new Vote.VotePK(userId, bookId);
        Vote vote = getById(id);
        if (vote == null) {
            voteRepository.save(new Vote(id, value, LocalDate.now()));
            log.info("Save vote id " + id + ", value " + value);
            return new VoteUpdateDto(false, value);
        } else {
            int oldValue = vote.getValue();
            vote.setValue(value);
            voteRepository.save(vote);
            log.info("Update vote " + vote);
            return new VoteUpdateDto(true, value - oldValue);
        }
    }

    public void delete(Vote vote) {
        log.info("Delete vote " + vote);
        voteRepository.delete(vote);
    }
}
