package com.challenge.middleware.convertor;

import com.challenge.middleware.dto.UserStoryDTO;
import com.challenge.middleware.entity.UserStory;
import org.springframework.stereotype.Component;

@Component
public class UserStoryConverter {

    public UserStory convertToEntity(UserStoryDTO storyDto) {
        UserStory userStoryEntity = new UserStory();
        userStoryEntity.setUserStoryId(storyDto.getIdUserStory());
        userStoryEntity.setDescription(storyDto.getDescription());
        userStoryEntity.setStatusEnum(storyDto.getStatusEnum());

        return userStoryEntity;
    }

    public UserStoryDTO convertToDto(UserStory entity) {
        UserStoryDTO userStoryDto = new UserStoryDTO();
        userStoryDto.setIdUserStory(entity.getUserStoryId());
        userStoryDto.setDescription(entity.getDescription());
        userStoryDto.setStatusEnum(entity.getStatusEnum());

        return userStoryDto;
    }

}
