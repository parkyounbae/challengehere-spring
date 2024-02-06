package com.parkyounbae.challengehere.repository.memory.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeSuccess;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeSuccessRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChallengeSuccessMemoryRepository implements ChallengeSuccessRepository {
    private final Map<Long, ChallengeSuccess> challengeSuccesses = new HashMap<>();
    private long nextId = 1;

    @Override
    public ChallengeSuccess save(ChallengeSuccess challengeSuccess) {
        if (challengeSuccess.getId() == null) {
            challengeSuccess.setId(nextId++);
        }
        challengeSuccesses.put(challengeSuccess.getId(), challengeSuccess);
        return challengeSuccess;
    }

    @Override
    public List<ChallengeSuccess> findByUserId(Long userId) {
        List<ChallengeSuccess> userSuccesses = new ArrayList<>();
        for (ChallengeSuccess success : challengeSuccesses.values()) {
            if (success.getUserId().equals(userId)) {
                userSuccesses.add(success);
            }
        }
        return userSuccesses;
    }

    @Override
    public List<ChallengeSuccess> findByChallengeId(Long challengeId) {
        List<ChallengeSuccess> challengeSuccessesForChallenge = new ArrayList<>();
        for (ChallengeSuccess success : challengeSuccesses.values()) {
            if (success.getChallengeId().equals(challengeId)) {
                challengeSuccessesForChallenge.add(success);
            }
        }
        return challengeSuccessesForChallenge;
    }

    @Override
    public List<ChallengeSuccess> findByUserIdAndChallengeId(Long userId, Long challengeId) {
        List<ChallengeSuccess> successes = new ArrayList<>();
        for (ChallengeSuccess success : challengeSuccesses.values()) {
            if (success.getUserId().equals(userId) && success.getChallengeId().equals(challengeId)) {
                successes.add(success);
            }
        }
        return successes;
    }

    @Override
    public Boolean findByChallengeIdAndUserIdAndDate(Long challengeId, Long userId, String date){
        for(ChallengeSuccess success : challengeSuccesses.values()) {
            if(success.getDate().equals(date) && success.getChallengeId().equals(challengeId) && success.getUserId().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Long> findByChallengeIdAndDate(Long challengeId, String date) {
        List<Long> successParticipantUserId = new ArrayList<>();
        for(ChallengeSuccess success : challengeSuccesses.values()) {
            if(success.getDate().equals(date) && success.getChallengeId().equals(challengeId)) {
                successParticipantUserId.add(success.getUserId());
            }
        }
        return successParticipantUserId;
    }

    @Override
    public void deleteByUserId(Long userId) {
        List<Long> successesToRemove = new ArrayList<>();
        for (Map.Entry<Long, ChallengeSuccess> entry : challengeSuccesses.entrySet()) {
            Long successId = entry.getKey();
            ChallengeSuccess success = entry.getValue();
            if (success.getUserId().equals(userId)) {
                successesToRemove.add(successId);
            }
        }

        for (Long successId : successesToRemove) {
            challengeSuccesses.remove(successId);
        }
    }
}
