package com.parkyounbae.challengehere.domain.post;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class Like {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long postId;
}
