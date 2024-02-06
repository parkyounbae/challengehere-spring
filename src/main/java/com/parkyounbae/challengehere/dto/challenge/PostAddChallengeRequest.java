package com.parkyounbae.challengehere.dto.challenge;

import lombok.Data;

import java.util.List;

@Data
public class PostAddChallengeRequest {
    private String name;
    private String cover;
    private Long userId;
    private List<Position> position;
    private List<Participant> participant;
}
