package com.parkyounbae.challengehere.dto.home;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetHomeResponse {
    private List<CircleData> circleData = new ArrayList<>();
    private List<HomeChallengeData> challengeData = new ArrayList<>();
}

