package com.parkyounbae.challengehere.repository.interfaces.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeSuccess;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeSuccessRepository {
    ChallengeSuccess save(ChallengeSuccess challengeSuccess);

    List<ChallengeSuccess> findByUserId(Long userId);

    List<ChallengeSuccess> findByChallengeId(Long challengeId);

    List<ChallengeSuccess> findByUserIdAndChallengeId(Long userId, Long challengeId);

    Optional<ChallengeSuccess> findByChallengeIdAndUserIdAndDate(Long challengeId, Long UserId, String date);

    List<Long> findUserIdsByChallengeIdAndDate(Long challengeId, String date);

    void deleteByUserId(Long userId);
}
