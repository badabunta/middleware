package com.challenge.middleware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PokerPlanningSessionDTO {

    @JsonProperty(namespace = "idSession")
    @Setter(AccessLevel.NONE)
    private String idSession;

    @JsonProperty(namespace = "title")
    private String title;
}
