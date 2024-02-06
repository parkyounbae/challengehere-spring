package com.parkyounbae.challengehere.repository.interfaces.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeParticipant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeParticipantRepository {
    ChallengeParticipant save(ChallengeParticipant challengeParticipant);

    List<ChallengeParticipant> findBuUserId(Long id);

    List<ChallengeParticipant> findByChallengeId(Long id);

    void deleteById(Long id);

    void deleteByUserId(Long id);
}
