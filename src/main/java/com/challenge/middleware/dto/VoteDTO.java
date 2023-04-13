package com.challenge.middleware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteDTO {

    @NonNull
    @JsonProperty(namespace = "idMember")
    private String idMember;

    @NonNull
    @JsonProperty(namespace = "idUserStory")
    private String idUserStory;

    @JsonProperty(namespace = "value")
    private String value;

}
