package com.challenge.middleware.service;

import com.challenge.middleware.convertor.VoteConvertor;
import com.challenge.middleware.dto.VoteDTO;
import com.challenge.middleware.entity.Member;
import com.challenge.middleware.entity.PokerPlanningSession;
import com.challenge.middleware.entity.UserStory;
import com.challenge.middleware.entity.Vote;
import com.challenge.middleware.enums.StatusEnum;
import com.challenge.middleware.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private PokerPlanningSessionService sessionService;
    private UserStoryService userStoryService;
    private VoteConvertor voteConvertor;
    private MemberService memberService;

    public VoteService(VoteRepository voteRepository, PokerPlanningSessionService sessionService, UserStoryService userStoryService, VoteConvertor voteConvertor, MemberService memberService) {
        this.voteRepository = voteRepository;
        this.sessionService = sessionService;
        this.userStoryService = userStoryService;
        this.voteConvertor = voteConvertor;
        this.memberService = memberService;
    }

    public List<VoteDTO> getAllVotes(String idSession) {
        PokerPlanningSession sessionEntity = sessionService.findSessionEntity(idSession);
        List<UserStory> userStories = sessionEntity.getUserStories();
        List<String> userStoryIds = userStories.stream().map(UserStory::getUserStoryId).toList();

        return voteRepository.findAllByUserStoryIdIn(userStoryIds).stream().map(e -> voteConvertor.convertToDto(e)).toList();
    }

    @Transactional
    public VoteDTO emitVote(String idSession, VoteDTO emitRequest) {
        UserStory userStory = userStoryService.findUserStoryEntity(idSession, emitRequest.getIdUserStory());
        Member member = memberService.findMember(idSession, emitRequest.getIdMember());
        Optional<Vote> optVote = Optional.ofNullable(
                voteRepository.findByMemberIdAndUserStoryId(emitRequest.getIdMember(), emitRequest.getIdUserStory())
        );

        if (StatusEnum.VOTED.equals(userStory.getStatusEnum()) || StatusEnum.PENDING.equals(userStory.getStatusEnum())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User story stopped for voting");
        }

        Vote vote = new Vote();
        if (optVote.isPresent()) {
            vote = optVote.get();
            vote.setVoteValue(emitRequest.getValue());
        } else {
            vote.setUserStory(userStory);
            vote.setMember(member);
            vote.setUserStoryId(emitRequest.getIdUserStory());
            vote.setVoteValue(emitRequest.getValue());
            vote.setMemberId(emitRequest.getIdMember());
        }

        voteRepository.save(vote);

        return emitRequest;
    }

}
