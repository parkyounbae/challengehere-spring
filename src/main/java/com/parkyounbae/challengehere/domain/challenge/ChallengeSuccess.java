package com.parkyounbae.challengehere.domain.challenge;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// @Entity
public class ChallengeSuccess {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long challengeId;

    private String date;
}
