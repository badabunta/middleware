package com.challenge.middleware.repository;

import com.challenge.middleware.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberId(String memberId);

    @Query(value = "SELECT * FROM member as m WHERE EXISTS " +
            "(SELECT * FROM poker_planning_session as pps WHERE pps.session_id = :sessionId AND pps.id = m.poker_planning_session_member_id)",
        nativeQuery = true)
    List<Member> findAllBySessionId(String sessionId);

    @Query(value = "SELECT m.* FROM member as m " +
            "INNER JOIN poker_planning_session as pps ON m.poker_planning_session_member_id=pps.id AND m.member_id = :memberId AND pps.session_id = :sessionId",
        nativeQuery = true)
    Member findBySessionIdAndMemberId(String sessionId, String memberId);


}
