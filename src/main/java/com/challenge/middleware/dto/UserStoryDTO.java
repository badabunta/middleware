package com.challenge.middleware.dto;

import com.challenge.middleware.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserStoryDTO {

    @JsonProperty(namespace = "idUserStory")
    private String idUserStory;

    @JsonProperty(namespace = "description")
    private String description;

    @JsonProperty(namespace = "statusEnum")
    private StatusEnum statusEnum = StatusEnum.PENDING;
}
