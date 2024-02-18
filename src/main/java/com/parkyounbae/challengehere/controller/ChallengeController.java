package com.parkyounbae.challengehere.controller;

import com.parkyounbae.challengehere.domain.challenge.Challenge;
import com.parkyounbae.challengehere.dto.challenge.GetChallengeListResponse;
import com.parkyounbae.challengehere.dto.challenge.GetChallengeResponse;
import com.parkyounbae.challengehere.dto.challenge.PostAddChallengeRequest;
import com.parkyounbae.challengehere.dto.challenge.PostModifyNotification;
import com.parkyounbae.challengehere.dto.home.GetHomeResponse;
import com.parkyounbae.challengehere.dto.home.PostValidateChallengeRequest;
import com.parkyounbae.challengehere.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class ChallengeController {
    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {this.challengeService = challengeService;}

    @PostMapping("/challenge")
    public ResponseEntity<Void> addChallenge(@RequestBody PostAddChallengeRequest postAddChallengeRequest) {
        challengeService.addChallenge(postAddChallengeRequest);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/challenge/notification")
    public ResponseEntity<String> modifyNotification(@RequestBody PostModifyNotification postModifyNotification) {
        challengeService.modifyChallengeNotification(Long.parseLong(postModifyNotification.getChallenge_id()), postModifyNotification.getContent());
        return new ResponseEntity(postModifyNotification.getContent(), HttpStatus.OK);
    }

    @GetMapping("/challenge")
    public List<GetChallengeListResponse> getChallengeList(@RequestHeader("user_id") String user_id) {
        return challengeService.getChallengeListResponse(Long.parseLong(user_id));
    }

    @GetMapping("/challenge/{challengeId}")
    public GetChallengeResponse getChallengeDetail(@PathVariable("challengeId") String challengeId) {
        return challengeService.getChallengeResponse(Long.parseLong(challengeId));
    }

    @PostMapping("/challenge/success")
    public GetHomeResponse postChallengeSuccess(@RequestBody PostValidateChallengeRequest postValidateChallengeRequest) {
        return challengeService.validateChallenge(postValidateChallengeRequest);
    }

    // 챌린지 업데이트 하기
    @PostMapping("/challenge/update")
    public ResponseEntity<Void> postUpdateChallenge(@RequestBody PostAddChallengeRequest postAddChallengeRequest, @RequestHeader("challenge_id") String challenge_id) {
        challengeService.updateChallenge(postAddChallengeRequest, Long.parseLong(challenge_id));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 챌린지 삭제하기
    @DeleteMapping("/challenge/{challengeId}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable("challengeId") String challengeId) {
        challengeService.deleteChallenge(Long.parseLong(challengeId));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 챌린지 떠나기
    @PostMapping("/challenge/leave")
    public ResponseEntity<Void> leaveChallenge(@RequestHeader("challenge_id") String challenge_id, @RequestHeader("user_id") String user_id) {
        challengeService.leaveChallenge(Long.parseLong(challenge_id), Long.parseLong(user_id));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 챌린지 수정화면 데이터 불러오기
    @GetMapping("/challenge/update")
    public PostAddChallengeRequest getChallengeUpdate(@RequestHeader("challenge_id") String challenge_id) {
        return challengeService.getChallengeModifyResponse(Long.parseLong(challenge_id));
    }
}
