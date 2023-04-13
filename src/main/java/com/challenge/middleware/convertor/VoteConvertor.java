package com.challenge.middleware.convertor;

import com.challenge.middleware.dto.VoteDTO;
import com.challenge.middleware.entity.Vote;
import org.springframework.stereotype.Component;

@Component
public class VoteConvertor {

    public Vote convertToEntity(VoteDTO voteDTO) {
        Vote voteEntity = new Vote();
        voteEntity.setUserStoryId(voteDTO.getIdUserStory());
        voteEntity.setMemberId(voteDTO.getIdMember());
        voteEntity.setVoteValue(voteDTO.getValue());

        return voteEntity;
    }

    public VoteDTO convertToDto(Vote entity) {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setIdMember(entity.getMemberId());
        voteDTO.setIdUserStory(entity.getUserStoryId());
        voteDTO.setValue(entity.getVoteValue());

        return voteDTO;
    }
}
