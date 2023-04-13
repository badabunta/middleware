package com.challenge.middleware.repository;

import com.challenge.middleware.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAllByUserStoryIdIn(List<String> userStoryIds);

    Vote findByMemberIdAndUserStoryId(String memberId, String userStoryId);
}
