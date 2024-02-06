package com.parkyounbae.challengehere.controller;

import com.parkyounbae.challengehere.dto.board.GetBoardResponse;
import com.parkyounbae.challengehere.dto.board.PostBoardRequest;
import com.parkyounbae.challengehere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    public final PostService postService;

    @Autowired
    public PostController(PostService postService) {this.postService = postService;}

    @GetMapping("/post")
    public List<GetBoardResponse> getBoardResponses(@RequestHeader("challenge_id") String challenge_id, @RequestHeader("user_id") String user_id) {
        return postService.getBoardResponses(Long.parseLong(challenge_id), Long.parseLong(user_id));
    }

    @PostMapping("/post")
    public ResponseEntity<Void> postBoardResponse(@RequestBody PostBoardRequest postBoardRequest) {
        postService.postBoard(postBoardRequest);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/post/like")
    public ResponseEntity<Void> postLikeResponse(@RequestHeader("post_id") String post_id, @RequestHeader("user_id") String user_id) {
        postService.likeBoard(Long.parseLong(post_id), Long.parseLong(user_id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/post")
    public ResponseEntity<Void> deleteResponse(@RequestHeader("post_id") String post_id, @RequestHeader("user_id") String user_id) {
        postService.deleteBoard(Long.parseLong(post_id), Long.parseLong(user_id));
        return new ResponseEntity(HttpStatus.OK);
    }



}
