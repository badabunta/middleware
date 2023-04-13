package com.challenge.middleware.entity;

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
@Table(name = "member")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", unique = true)
    private String memberId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poker_planning_session_member_id", foreignKey = @ForeignKey(
            name = "fk_poker_planning_session_member",
            foreignKeyDefinition = "FOREIGN KEY (poker_planning_session_member_id) REFERENCES poker_planning_session ON UPDATE CASCADE"
    ))
    private PokerPlanningSession pokerPlanningSession;

    @OneToOne(mappedBy = "member")
    private Vote vote;

}
