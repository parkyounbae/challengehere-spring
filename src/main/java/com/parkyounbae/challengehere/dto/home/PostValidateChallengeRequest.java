package com.parkyounbae.challengehere.dto.home;

import lombok.Data;

@Data
public class PostValidateChallengeRequest {
    private String user_id;
    private String challenge_id;
}
