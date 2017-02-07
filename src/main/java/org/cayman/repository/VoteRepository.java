package org.cayman.repository;


import org.cayman.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Vote.VotePK>{
}
