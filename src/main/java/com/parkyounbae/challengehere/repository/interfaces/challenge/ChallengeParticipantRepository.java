package com.parkyounbae.challengehere.repository.interfaces.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeParticipant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeParticipantRepository {
    ChallengeParticipant save(ChallengeParticipant challengeParticipant);

    List<ChallengeParticipant> findByUserId(Long userId);

    List<ChallengeParticipant> findByChallengeId(Long id);

    ChallengeParticipant findByUserIdAndChallengeId(Long userId, Long challengeId);

    void deleteById(Long id);

    void deleteByUserId(Long id);

    void deleteByChallengeId(Long id);
}
