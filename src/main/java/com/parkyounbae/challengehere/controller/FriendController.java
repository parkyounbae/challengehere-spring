package com.parkyounbae.challengehere.controller;

import com.parkyounbae.challengehere.dto.friend.GetFriendCommitDataResponse;
import com.parkyounbae.challengehere.dto.friend.GetFriendSearchResultResponse;
import com.parkyounbae.challengehere.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {this.friendService = friendService;}

    @GetMapping("/friend")
    public List<GetFriendCommitDataResponse> getFriendCommitData(@RequestHeader("user_id") String user_id) {
        return friendService.getFriendCommitData(Long.parseLong(user_id));
    }

    @GetMapping("/friend/request")
    public ResponseEntity<Void> requestFriend(@RequestHeader("request_id") String request_id, @RequestHeader("receive_id") String receive_id) {
        friendService.requestFriend(Long.parseLong(request_id), Long.parseLong(receive_id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/friend/search")
    public List<GetFriendSearchResultResponse> getFriendSearchResultResponses(@RequestHeader("user_id") String user_id, @RequestHeader("name") String name) {
        return friendService.searchFriend(Long.parseLong(user_id), name);
    }
}
