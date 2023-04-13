package com.challenge.middleware.repository;

import com.challenge.middleware.entity.PokerPlanningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokerPlanningSessionRepository extends JpaRepository<PokerPlanningSession, Long> {

    PokerPlanningSession findBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);
}
