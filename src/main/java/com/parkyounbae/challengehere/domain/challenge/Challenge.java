package com.parkyounbae.challengehere.domain.challenge;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Entity
public class Challenge {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double power = 0.0;

    private String coverPath;

    private String notification = "공지사항";

}