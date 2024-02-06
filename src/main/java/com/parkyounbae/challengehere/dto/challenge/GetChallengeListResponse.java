package com.parkyounbae.challengehere.dto.challenge;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetChallengeListResponse {
    private Long id;
    private String name;
    private List<String> person = new ArrayList<>();
    private List<String> successDate = new ArrayList<>();
}
