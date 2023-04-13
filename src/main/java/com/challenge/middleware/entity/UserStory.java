package com.challenge.middleware.entity;

import com.challenge.middleware.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_story")
public class UserStory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_story_id", unique = true)
    private String userStoryId;

    @Column(name = "description")
    private String description;

    @Column(name = "status_enum")
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum = StatusEnum.PENDING;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poker_planning_session_user_story_id", foreignKey = @ForeignKey(
            name = "fk_poker_planning_session_user_story",
            foreignKeyDefinition = "FOREIGN KEY (poker_planning_session_user_story_id) REFERENCES poker_planning_session ON UPDATE CASCADE"
    ))
    private PokerPlanningSession pokerPlanningSession;

    @OneToOne(mappedBy = "userStory", cascade = CascadeType.ALL)
    private Vote vote;
}
