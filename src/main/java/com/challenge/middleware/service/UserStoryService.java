package com.challenge.middleware.service;

import com.challenge.middleware.convertor.UserStoryConverter;
import com.challenge.middleware.dto.MemberDTO;
import com.challenge.middleware.dto.UserStoryDTO;
import com.challenge.middleware.entity.PokerPlanningSession;
import com.challenge.middleware.entity.UserStory;
import com.challenge.middleware.enums.StatusEnum;
import com.challenge.middleware.repository.UserStoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserStoryService {

    private UserStoryRepository userStoryRepository;

    private UserStoryConverter userStoryConverter;

    private PokerPlanningSessionService planingSessionService;


    public UserStoryService(UserStoryRepository userStoryRepository, UserStoryConverter userStoryConverter, PokerPlanningSessionService planingSessionService) {
        this.userStoryRepository = userStoryRepository;
        this.userStoryConverter = userStoryConverter;
        this.planingSessionService = planingSessionService;
    }

    public List<UserStoryDTO> getUserStoriesFromSession(String idSession) {
        Optional<List<UserStory>> optUserStories = Optional.ofNullable(userStoryRepository.findAllBySessionId(idSession));
        if (optUserStories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        return optUserStories.get().stream().map(s -> userStoryConverter.convertToDto(s)).toList();
    }

    @Transactional
    public UserStoryDTO createUserStory(String idSession, UserStoryDTO userStoryDto) {
        PokerPlanningSession session = getSession(idSession);

        UserStory userStoryEntity = userStoryConverter.convertToEntity(userStoryDto);
        userStoryEntity.setPokerPlanningSession(session);

        userStoryRepository.save(userStoryEntity);

        return userStoryDto;
    }

    @Transactional
    public UserStoryDTO updateUserStory(String idSession, String idUserStory, UserStoryDTO userStoryDto) {
        UserStory userStory = findUserStoryEntity(idSession, idUserStory);

        userStory.setDescription(userStoryDto.getDescription());
        userStory.setStatusEnum(userStoryDto.getStatusEnum());

        userStoryRepository.save(userStory);

        return userStoryDto;
    }

    @Transactional
    public UserStoryDTO deleteUserStory(String idSession, String idUserStory, MemberDTO memberDto) {
        PokerPlanningSession session = getSession(idSession);

        session.getMembers().stream().filter(m -> m.getMemberId().equals(memberDto.getIdMember()))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Member not in session"));

        UserStory userStory = findUserStoryEntity(idSession, idUserStory);

        if (!userStory.getStatusEnum().equals(StatusEnum.PENDING)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User story votation in progress");
        }

        UserStoryDTO userStoryDto = userStoryConverter.convertToDto(userStory);
        userStoryRepository.delete(userStory);

        return userStoryDto;
    }

    private PokerPlanningSession getSession(String idSession) {
        Optional<PokerPlanningSession> optPokerPlanningSession =
                Optional.ofNullable(planingSessionService.findSessionEntity(idSession));
        if (optPokerPlanningSession.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found");
        }

        return optPokerPlanningSession.get();
    }

    protected UserStory findUserStoryEntity(String idSession, String idUserStory) {
        Optional<UserStory> optUserStory = Optional.ofNullable(userStoryRepository.findBySessionId(idSession, idUserStory));
        if (optUserStory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        return optUserStory.get();
    }
}
