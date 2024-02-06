package com.parkyounbae.challengehere.dto.board;

import lombok.Data;

import java.util.List;

@Data
public class GetBoardResponse {
    private Long id;
    private Long userId;
    private String name;
    private String content;
    private int like;
    private Boolean isLiked;
    private List<String> imagePath;
}
