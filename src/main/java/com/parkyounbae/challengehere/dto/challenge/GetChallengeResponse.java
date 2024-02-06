package com.parkyounbae.challengehere.dto.challenge;

import com.parkyounbae.challengehere.dto.home.CircleData;
import lombok.Data;

import java.util.List;

@Data
public class GetChallengeResponse {
    private Long id;
    private String coverURL;
    private String notification;
    private Double challengePower = 0.0;
    private List<String> todaySuccessParticipant;
    private List<CircleData> colorData;
    private List<RankData> rankData;
    private List<ChallengeBoardThumbnailData> boardThumbnail;
}
