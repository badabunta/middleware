package com.challenge.middleware.service;

import com.challenge.middleware.convertor.PokerPlanningSessionConvertor;
import com.challenge.middleware.dto.PokerPlanningSessionDTO;
import com.challenge.middleware.entity.PokerPlanningSession;
import com.challenge.middleware.repository.PokerPlanningSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PokerPlanningSessionService {

    private PokerPlanningSessionRepository sessionRepository;

    private PokerPlanningSessionConvertor sessionConvertor;

    public PokerPlanningSessionService(PokerPlanningSessionRepository sessionRepository, PokerPlanningSessionConvertor sessionConvertor) {
        this.sessionRepository = sessionRepository;
        this.sessionConvertor = sessionConvertor;
    }

    public List<PokerPlanningSessionDTO> getAllSessions() {
        return sessionRepository.findAll().stream().map(s ->
                sessionConvertor.convertToDto(s)).toList();
    }

    public PokerPlanningSessionDTO getSessionBySessionId(String idSession) {
        PokerPlanningSession pokerPlanningSession = findSessionEntity(idSession);
        return sessionConvertor.convertToDto(pokerPlanningSession);
    }

    @Transactional
    public PokerPlanningSessionDTO createSession(PokerPlanningSessionDTO session) {
        PokerPlanningSession sessionEntity = sessionRepository.saveAndFlush(sessionConvertor.convertToEntity(session));
        return sessionConvertor.convertToDto(sessionEntity);
    }

    @Transactional
    public PokerPlanningSessionDTO destroySession(String idSession) {
        PokerPlanningSession session = findSessionEntity(idSession);
        session.getMembers().forEach(m -> m.setPokerPlanningSession(null));

        sessionRepository.delete(session);

        return sessionConvertor.convertToDto(session);
    }

    protected PokerPlanningSession findSessionEntity(String idSession) {
        Optional<PokerPlanningSession> optSession = Optional.ofNullable(sessionRepository.findBySessionId(idSession));
        if (optSession.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found");
        }

        return optSession.get();
    }

}
