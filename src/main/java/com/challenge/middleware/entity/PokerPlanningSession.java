package com.challenge.middleware.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "poker_planning_session")
public class PokerPlanningSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "session_id", unique = true)
    private String sessionId;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "pokerPlanningSession")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Member> members;

    @OneToMany(mappedBy = "pokerPlanningSession", cascade = CascadeType.ALL)
    private List<UserStory> userStories;

}
