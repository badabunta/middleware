package com.challenge.middleware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberDTO {
    @NonNull
    @Setter(AccessLevel.NONE)
    @JsonProperty(namespace = "idMember")
    private String idMember;

    @JsonProperty(namespace = "name")
    @NonNull
    private String name;
}
