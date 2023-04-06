package com.jylove.mpc.mpc.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="companionAnimal")
@Getter
@Setter
@ToString
public class CompanionAnimal {
    @Id
    @Column(name = "CompanionAnimal_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String companionChoice;

}
