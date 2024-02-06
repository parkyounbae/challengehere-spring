package com.parkyounbae.challengehere.dto.home;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HomeChallengeData {
    private Long id;
    private String challengeName;
    private List<String> challengePosition = new ArrayList<>();
    private List<Double> challengePositionX = new ArrayList<>();
    private List<Double> challengePositionY = new ArrayList<>();
    private Boolean isSuccess;
}
