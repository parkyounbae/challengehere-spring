package com.parkyounbae.challengehere.dto.alert;

import lombok.Data;

@Data
public class PostAlertResponse {
    private Long receive_id;
    private Long request_id;
    private int type;
    private Boolean is_accept;
}
