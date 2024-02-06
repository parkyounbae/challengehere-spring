package com.parkyounbae.challengehere.dto.alert;

import lombok.Data;

@Data
public class GetAlertResponse {
    private String name;

    private Long requestId;

    private int type;
}
