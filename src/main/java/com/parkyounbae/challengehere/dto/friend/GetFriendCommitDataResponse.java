package com.parkyounbae.challengehere.dto.friend;

import lombok.Data;

import java.util.List;

@Data
public class GetFriendCommitDataResponse {
    private Long id;
    private String name;
    private List<CommitData> commitData;
}


