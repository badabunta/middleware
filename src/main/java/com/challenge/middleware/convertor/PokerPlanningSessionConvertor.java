package com.challenge.middleware.convertor;

import com.challenge.middleware.dto.PokerPlanningSessionDTO;
import com.challenge.middleware.entity.PokerPlanningSession;
import org.springframework.stereotype.Component;

@Component
public class PokerPlanningSessionConvertor {

    public PokerPlanningSession convertToEntity(PokerPlanningSessionDTO sessionDTO) {
        PokerPlanningSession sessionEntity = new PokerPlanningSession();
        sessionEntity.setSessionId(sessionDTO.getIdSession());
        sessionEntity.setTitle(sessionDTO.getTitle());

        return sessionEntity;
    }

    public PokerPlanningSessionDTO convertToDto(PokerPlanningSession entity) {
        return new PokerPlanningSessionDTO(entity.getSessionId(), entity.getTitle());
    }
}
