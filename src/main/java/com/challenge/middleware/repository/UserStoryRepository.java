package com.challenge.middleware.repository;

import com.challenge.middleware.entity.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

    @Query(value = "SELECT * FROM user_story as us WHERE EXISTS " +
            "(SELECT * FROM poker_planning_session as pps WHERE pps.session_id = :sessionId AND pps.id = us.poker_planning_session_user_story_id)",
            nativeQuery = true)
    List<UserStory> findAllBySessionId(String sessionId);

    @Query(value = "SELECT us.* FROM user_story as us " +
            "INNER JOIN poker_planning_session as pps ON us.poker_planning_session_user_story_id=pps.id AND us.user_story_id = :userStoryId AND pps.session_id = :sessionId",
            nativeQuery = true)
    UserStory findBySessionId(String sessionId, String userStoryId);

}
