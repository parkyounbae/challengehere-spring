package com.parkyounbae.challengehere.dto.board;

import lombok.Data;

import java.util.List;

@Data
public class PostBoardRequest {
    private Long userId;
    private Long challengeId;
    private String content;
    private List<String> images;
}
