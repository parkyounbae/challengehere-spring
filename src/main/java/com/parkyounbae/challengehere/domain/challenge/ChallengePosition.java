package com.parkyounbae.challengehere.domain.challenge;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 @Entity
public class ChallengePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long challengeId;

    private String positionName;

    private Double xPosition;

    private Double yPosition;
}
