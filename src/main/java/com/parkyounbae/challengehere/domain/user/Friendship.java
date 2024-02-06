package com.parkyounbae.challengehere.domain.user;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class Friendship {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestId; // 요청한 사람의 아이디

    private Long receiveId; // 요청을 받은 사람의 아이디

    private int status; // 0 : waiting, 1 : accepted
}
